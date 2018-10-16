package abc.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import abc.header.*;
import abc.sound.*;
import lib6005.parser.UnableToParseException;

/**
 * Tests for the parser.
 *
 */
public class AbcParserTest {
    
    /*
     * Testing strategy for AbcParser
     *   
     * Partition for parse:
     *   string: contains header fields C, L, M, Q, doesn't contain those fields, key signature minor, major, natural, sharp, flat
     *           body contains notes, octave changes, sharps, flats, naturals, duration changes, bars,
     *           repeat bars, multiple ending labels, rests, chords, tuplets, multiple voices
     *   output: a Song that contains a Header and a Music
     */
    
    private final static Note A = new Note(1, new Pitch('A'));
    private final static Note B = new Note(1, new Pitch('B'));
    private final static Note B_FLAT = new Note(1, new Pitch('B')).transpose(-1);
    private final static Note C = new Note(1, new Pitch('C'));
    private final static Note HIGH_C = new Note(1, new Pitch('C')).transpose(12);
    private final static Note D = new Note(1, new Pitch('D'));
    private final static Note E = new Note(1, new Pitch('E'));
    private final static Note F = new Note(1, new Pitch('F'));
    private final static Note G = new Note(1, new Pitch('G'));
    private final static Note LOW_G = new Note(1, new Pitch('G')).transpose(-12);
    private final static Note HIGH_HIGH_G = new Note(1, new Pitch('G')).transpose(24);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Testing parse
    // Tests string contains default header fields only, body contains notes, bars, key signature major, natural
    // output is a Song that contains a Header and a Music
    @Test
    public void testParseDefaultValues() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C", "", false), 1.0/8.0, new Meter(4, 4), new Tempo(1.0/8.0, 100), "Test Piece 1", 1);
        List<Music> notes = Arrays.asList(G, A, B, HIGH_C);
        
        Measure measure = new Measure(notes, 4, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure);
        Voice voice = new Voice(notes2);
        Voices music = new Voices(voice);
        Song expected = new Song(music, header);
        
        String input = "X: 1\n"
                     + "T: Test Piece 1\n"
                     + "K: C\n"
                     + "G2 A2 B2 c2|\n";
        Song result = AbcParser.parse(input);
        assertEquals(expected, result);
    }
    
 // Testing parse
    // Tests string contains default header fields only, body contains notes, bars, key signature major, natural, and octave indicators
    // output is a Song that contains a Header and a Music
    @Test
    public void testParseOctaves() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C", "", false), 1.0/8.0, new Meter(4, 4), new Tempo(1.0/8.0, 100), "Test Piece 1", 1);
        List<Music> notes = Arrays.asList(LOW_G, HIGH_HIGH_G);
        
        Measure measure = new Measure(notes, 4, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure);
        Voice voice = new Voice(notes2);
        Voices music = new Voices(voice);
        Song expected = new Song(music, header);
        
        String input = "X: 1\n"
                     + "T: Test Piece 1\n"
                     + "K: C\n"
                     + "G,2 g'2";
        Song result = AbcParser.parse(input);
        assertEquals(expected, result);
    }
    
    // Tests string contains default header fields only, body contains notes, bars, key signature major, natural, and accidentals
    // Tests that accidentals carry over to the rest of a measure but not to the next measure
    // output is a Song that contains a Header and a Music
    @Test
    public void testParseAccidentalCarryover() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C", "", false), 1.0/8.0, new Meter(4, 4), new Tempo(1.0/8.0, 100), "Test Piece 1", 1);
        List<Music> notes = Arrays.asList(B, B, B_FLAT, B_FLAT);
        
        Measure measure = new Measure(notes, 4, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure, measure);
        Voice voice = new Voice(notes2);
        Voices music = new Voices(voice);
        Song expected = new Song(music, header);
        
        String input = "X: 1\n"
                     + "T: Test Piece 1\n"
                     + "K: C\n"
                     + "B2 B2 _B2 B2| B2 B2 _B2 B2\n";
        Song result = AbcParser.parse(input);
        assertEquals(expected, result);
    }
    
    // Tests string contains header fields C, M, key signature major, flat
    // body contains notes, rests, bars, durations, octave changes
    // output is a Song that contains a Header and a Music
    @Test
    public void testParseCMRestDurationOctave() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "The Goblin Composer", new KeySignature("C", "b", false), 1.0/8.0, new Meter(2, 2), new Tempo(1.0/8.0, 100), "Test Piece 2", 2);
        Note cFlat = C.transpose(-1);
        Note highCFlat = HIGH_C.transpose(-1);
        List<Music> bar1 = Arrays.asList(cFlat, cFlat, highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), new Rest(4));
        bar1.replaceAll((note)->{return note.rescale(.25);});
        List<Music> bar2 = Arrays.asList(cFlat, cFlat, highCFlat, highCFlat, cFlat, highCFlat, new Rest(2));
        bar2.replaceAll((note)->{return note.rescale(.25);});
        Measure measure1 = new Measure(bar1, 2, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 2, false, false, false, false, false);
        List<Measure> notes = Arrays.asList(measure1, measure2);
        Voice voice = new Voice(notes);
        Voices music = new Voices(voice);
        Song expected = new Song(music, header);
        
        String input = "X: 2\n"
                     + "T: Test Piece 2\n"
                     + "C: The Goblin Composer\n"
                     + "M: 2/2\n"
                     + "K: Cb\n"
                     + "C C c/2 c/2 c/2 c/2 z4 | C C C' C' c, c z2 |\n";
        Song result = AbcParser.parse(input);
        assertEquals(expected, result);
    }
    
    // Tests string contains header fields L, Q, key signature minor, sharp
    // body contains notes, rests, bars, durations, sharps, chords, tuplets
    @Test
    public void testParseLQChordTuplet() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("F", "#", true), 1.0/4.0, new Meter(4, 4), new Tempo(1.0/4.0, 200), "Test Piece 3", 3);
        Music[] tuplet1 = {F.transpose(1), F.transpose(1), new Rest(1)};
        List<Music> bar1 = Arrays.asList(F.transpose(1), new Tuplet(tuplet1), F.transpose(1));
        Music[] tuplet2 = {E.transpose(1).rescale(0.5), E.transpose(1).rescale(0.5), E.transpose(1).rescale(0.5)};
        List<Music> bar2 = Arrays.asList(C.transpose(1).rescale(0.5), C.transpose(1).rescale(0.5), new Tuplet(tuplet2), E.rescale(2).transpose(1));
        Measure measure1 = new Measure(bar1, 4, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 4, false, false, false, false, false);
        List<Measure> notes = Arrays.asList(measure1, measure2);
        Voice voice = new Voice(notes);
        Voices music = new Voices(voice);
        Song expected = new Song(music, header);
        
        String input = "X: 3\n"
                     + "T: Test Piece 3\n"
                     + "L: 1/4\n"
                     + "Q: 1/4=200\n"
                     + "K: F#m\n"
                     + "F (3FFz F | C/2 C/2 (3^E/2^E/2^E/2 E2 |\n";
        Song result = AbcParser.parse(input);
        assertEquals(expected, result);
    }
    
    // Tests string contains header fields all fields, key signature major, natural
    // body contains notes, rests, durations, repeat bars, flats
    @Test
    public void testParseRepeat() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "The Awesome Team", new KeySignature("F", "", false), 1.0/4.0, new Meter(4, 4), new Tempo(1.0/4.0, 150), "Test Piece 4", 4);
        Rest oneBeat = new Rest(1);
        Note cHalf = C.transpose(-1).rescale(0.5);
        Note dHalf = D.rescale(0.5);
        Note eHalf = E.rescale(0.5);
        List<Music> bar1 = Arrays.asList(F, F, F, oneBeat);
        List<Music> bar2 = Arrays.asList(cHalf, cHalf, dHalf, dHalf, eHalf, eHalf, oneBeat);
        Measure measure1 = new Measure(bar1, 4, true, false, false, false, false);
        Measure measure2 = new Measure(bar2, 4, false, true, false, false, false);
        List<Measure> notes = Arrays.asList(measure1, measure2);
        Voice voice = new Voice(notes);
        Voices music = new Voices(voice);
        Song expected = new Song(music, header);
        
        String input = "X: 4\n"
                     + "T: Test Piece 4\n"
                     + "C: The Awesome Team\n"
                     + "L: 1/4\n"
                     + "M: 4/4\n"
                     + "Q: 1/4=150\n"
                     + "K: F\n"
                     + "F F F z | _C/2 _C/2 D/2 D/2 E/2 E/2 z :|\n";
        Song result = AbcParser.parse(input);
        assertEquals(expected, result);
    }
    
    // Tests string ocntains header fields L, key signature major, natural
    // body contains major section bars, repeat bars, notes
    @Test
    public void testParseMajorSectionRepeat() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C"), 1.0/4.0, new Meter(4, 4), new Tempo(1.0/4.0, 100), "Test Piece Some Number", 567);
        List<Music> bar1 = Arrays.asList(C, C, C, C);
        List<Music> bar2 = Arrays.asList(HIGH_C, HIGH_C, HIGH_C, HIGH_C);
        Measure measure1 = new Measure(bar1, 4, false, false, false, false, true);
        Measure measure2 = new Measure(bar2, 4, false, true, false, false, false);
        Voice voice = new Voice(Arrays.asList(measure1, measure2));
        Voices music = new Voices(voice);
        Song expected = new Song(music, header);
        
        String input = "X: 567\n"
                     + "T: Test Piece Some Number\n"
                     + "L: 1/4\n"
                     + "K: C\n"
                     + "C C C C |] c c c c :|\n";
        Song result = AbcParser.parse(input);
        assertEquals(expected, result);
    }
    
    // Tests string contains header fields L, key signature major, natural
    // body contains notes, rests, durations, repeat bars, multiple endings
    @Test
    public void testParseMultipleEndings() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C", "=", false), 1.0/4.0, new Meter(4, 4), new Tempo(1.0/4.0, 100), "Test Piece 5", 5);
        Rest oneBeat = new Rest(1);
        Note highA = new Note(1, new Pitch('A')).transpose(12);
        Note highB = new Note(1, new Pitch('B')).transpose(12);
        List<Music> bar1 = Arrays.asList(F, F, F.rescale(2));
        List<Music> bar2 = Arrays.asList(C, D, E, oneBeat);
        List<Music> bar3 = Arrays.asList(F, G, highA, highB);
        Measure measure1 = new Measure(bar1, 4, true, false, false, false, false);
        Measure measure2 = new Measure(bar2, 4, false, true, true, false, false);
        Measure measure3 = new Measure(bar3, 4, false, false, false, true, false);
        List<Measure> notes = Arrays.asList(measure1, measure2, measure3);
        Voice voice = new Voice(notes);
        Voices music = new Voices(voice);
        Song expected = new Song(music, header);
        
        String input = "X: 5\n"
                     + "T: Test Piece 5\n"
                     + "L: 1/4\n"
                     + "K: C\n"
                     + "|: F F F2 |[1 C D E z :|[2 F G a b |\n";
        Song result = AbcParser.parse(input);
        assertEquals(expected, result);
    }
    
    // Tests string contains header fields L, key signature major, sharp
    // body contains notes, rests, durations, multiple voices
    @Test
    public void testParseMultipleVoices() throws UnableToParseException, IOException {
        Header header = new Header(Arrays.asList("upper", "lower"), "Unknown", new KeySignature("F", "#", false), 1.0/4.0, new Meter(4, 4), new Tempo(1.0/4.0, 100), "Test Piece 6", 6);
        
        Note aSharp = A.transpose(1);
        Note cSharp = C.transpose(1);
        Note dSharp = D.transpose(1);
        Note fSharp = F.transpose(1);
        Note eSharp = E.transpose(1);
        Rest twoBeats = new Rest(2);
        Rest oneBeat = new Rest(1);
        
        List<Music> bar1 = Arrays.asList(fSharp, eSharp, twoBeats);
        List<Music> bar2 = Arrays.asList(dSharp, cSharp, B, oneBeat);
        Measure measure1 = new Measure(bar1, 4, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 4, false, false, false, false, false);
        List<Measure> notes1 = Arrays.asList(measure1, measure2);
        Voice upper = new Voice(notes1);
        
        List<Music> bar3 = Arrays.asList(aSharp, B, cSharp, dSharp);
        Measure measure3 = new Measure(bar3, 4, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure3);
        Voice lower = new Voice(notes2);
        
        Map<String, Voice> voices = new HashMap<>();
        voices.put("upper", upper);
        voices.put("lower", lower);
        Voices music = new Voices(voices);
        Song expected = new Song(music, header);
        
        String input = "X: 6\n"
                     + "T: Test Piece 6\n"
                     + "L: 1/4\n"
                     + "V: upper\n"
                     + "V: lower\n"
                     + "K: F#\n"
                     + "V: upper\n"
                     + "F E z2 | D C B z |\n"
                     + "V: lower\n"
                     + "A B C D |\n";
        Song result = AbcParser.parse(input);
        assertEquals(expected, result);
    }
    
    // Tests string contains header fields all fields except tuplet, key signature major, flat
    // body contains all elements
    @Test
    public void testParseEverything() throws UnableToParseException, IOException {
        Header header = new Header(Arrays.asList("upper", "lower"), "A Person", new KeySignature("F", "=", false), 1.0/4.0, new Meter(4, 4), new Tempo(1.0/4.0, 120), "Test Piece 7", 7);
        
        Note highA = A.transpose(12);
        Note bFlat = B.transpose(-1);
        Note highE = E.transpose(12);
        Note highF = F.transpose(12);
        Note highG = G.transpose(12);
        Rest halfBeat = new Rest(0.5);
        
        List<Music> bar1 = Arrays.asList(F, F, F.rescale(2));
        List<Music> bar2 = Arrays.asList(halfBeat, F.rescale(0.5), E.rescale(0.5), D.rescale(0.5), E.rescale(0.5), F.rescale(0.5), G);
        Measure measure1 = new Measure(bar1, 4, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 4, false, false, false, false, false);
        List<Measure> notes1 = Arrays.asList(measure1, measure2);
        Voice upper = new Voice(notes1);
        
        Chord chord1 = new Chord(new HashSet<>(Arrays.asList(D.rescale(2), highF.rescale(2), highA.rescale(2))));
        Chord chord2 = new Chord(new HashSet<>(Arrays.asList(bFlat.rescale(2), highF.rescale(2), highA.rescale(2))));
        Chord chord3 = new Chord(new HashSet<>(Arrays.asList(bFlat.rescale(2), highE.rescale(2), highG.rescale(2))));
        Chord chord4 = new Chord(new HashSet<>(Arrays.asList(A.rescale(2), highE.rescale(2), highG.rescale(2))));
        Chord chord5 = new Chord(new HashSet<>(Arrays.asList(C.rescale(2), highE.rescale(2), highG.rescale(2))));
        List<Music> bar3 = Arrays.asList(chord1, chord2);
        List<Music> bar4 = Arrays.asList(chord3, chord4);
        List<Music> bar5 = Arrays.asList(chord3, chord5);
        Measure measure3 = new Measure(bar3, 4, true, false, false, false, false);
        Measure measure4 = new Measure(bar4, 4, false, true, true, false, false);
        Measure measure5 = new Measure(bar5, 4, false, false, false, true, false);
        List<Measure> notes2 = Arrays.asList(measure3, measure4, measure5);
        Voice lower = new Voice(notes2);
        
        Map<String, Voice> voices = new HashMap<>();
        voices.put("upper", upper);
        voices.put("lower", lower);
        Voices music = new Voices(voices);
        Song expected = new Song(music, header);
        
        String input = "X: 7\n"
                     + "T: Test Piece 7\n"
                     + "C: A Person\n"
                     + "M: 4/4\n"
                     + "L: 1/4\n"
                     + "Q: 1/4=120\n"
                     + "V: upper\n"
                     + "V: lower\n"
                     + "K: F\n"
                     + "V: upper\n"
                     + "F F F2 | z/2 F/2 E/2 D/2 E/2 F/2 G :|\n"
                     + "V: lower\n"
                     + "|: [D2f2a2] [_B2f2a2] |[1 [_B2e2g2] [A2e2g2] :|[2 [_B2e2g2] [C2e2g2] |\n";
        Song result = AbcParser.parse(input);
        assertEquals(expected, result);
    }

}
