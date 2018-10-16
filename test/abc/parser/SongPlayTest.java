package abc.parser;

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
 * Tests for the Song.
 * @category no_didit
 */
public class SongPlayTest {
    
    /*
     * Testing play for Song
     *   Partition can be found in SongTest.java
     */
    
    private final static Note A = new Note(1, new Pitch('A'));
    private final static Note B = new Note(1, new Pitch('B'));
    private final static Note C = new Note(1, new Pitch('C'));
    private final static Note HIGH_C = new Note(1, new Pitch('C')).transpose(12);
    private final static Note D = new Note(1, new Pitch('D'));
    private final static Note E = new Note(1, new Pitch('E'));
    private final static Note F = new Note(1, new Pitch('F'));
    private final static Note G = new Note(1, new Pitch('G'));
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testPlayDefaultValues() throws UnableToParseException, IOException, InterruptedException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C", "", false), 1.0/8.0, new Meter(4, 4), new Tempo(1.0/8.0, 100), "Test Piece 1", 1);
        List<Music> notes = Arrays.asList(G, A, B, HIGH_C);
        
        Measure measure = new Measure(notes, 4, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure);
        Voice voice = new Voice(notes2);
        Voices music = new Voices(voice);
        Song song = new Song(music, header);
        song.play();
        Thread.sleep(10000);
    }
    
    @Test
    public void testPlayCMRestDurationOctave() throws UnableToParseException, IOException, InterruptedException {
        Header header = new Header(new ArrayList<>(), "The Goblin Composer", new KeySignature("C", "b", false), 1.0/8.0, new Meter(2, 2), new Tempo(1.0/8.0, 100), "Test Piece 2", 2);
        Note cFlat = C.transpose(-1);
        Note highCFlat = HIGH_C.transpose(-1);
        List<Music> bar1 = Arrays.asList(cFlat, cFlat, highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), new Rest(4));
        List<Music> bar2 = Arrays.asList(cFlat, cFlat, highCFlat, highCFlat, cFlat, highCFlat, new Rest(2));
        Measure measure1 = new Measure(bar1, 8, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 8, false, false, false, false, false);
        List<Measure> notes = Arrays.asList(measure1, measure2);
        Voice voice = new Voice(notes);
        Voices music = new Voices(voice);
        Song song = new Song(music, header);
        song.play();
        Thread.sleep(10000);
    }
    
    @Test
    public void testPlayLQChordTuplet() throws UnableToParseException, IOException, InterruptedException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("F", "#", true), 1.0/4.0, new Meter(4, 4), new Tempo(1.0/8.0, 200), "Test Piece 3", 3);
        Music[] tuplet1 = {F.transpose(1), F.transpose(1), new Rest(1)};
        List<Music> bar1 = Arrays.asList(F.transpose(1), new Tuplet(tuplet1), F.transpose(1));
        Music[] tuplet2 = {E.transpose(1).rescale(0.5), E.transpose(1).rescale(0.5), E.transpose(1).rescale(0.5)};
        List<Music> bar2 = Arrays.asList(C.transpose(1).rescale(0.5), C.transpose(1).rescale(0.5), new Tuplet(tuplet2), E.rescale(2));
        Measure measure1 = new Measure(bar1, 4, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 4, false, false, false, false, false);
        List<Measure> notes = Arrays.asList(measure1, measure2);
        Voice voice = new Voice(notes);
        Voices music = new Voices(voice);
        Song song = new Song(music, header);
        song.play();
        Thread.sleep(10000);
    }
    
    @Test
    public void testPlayRepeat() throws UnableToParseException, IOException, InterruptedException {
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
        Song song = new Song(music, header);
        song.play();
        Thread.sleep(10000);
    }
    
    @Test
    public void testPlayMultipleEndings() throws UnableToParseException, IOException, InterruptedException {
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
        Song song = new Song(music, header);
        song.play();
        Thread.sleep(10000);
    }
    
    @Test
    public void testPlayMultipleVoices() throws UnableToParseException, IOException, InterruptedException {
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
        Song song = new Song(music, header);
        song.play();
        Thread.sleep(10000);
    }
    
    @Test
    public void testPlayEverything() throws UnableToParseException, IOException, InterruptedException {
        Header header = new Header(Arrays.asList("upper", "lower"), "A Person", new KeySignature("F", "#", false), 1.0/4.0, new Meter(4, 4), new Tempo(1.0/4.0, 120), "Test Piece 7", 7);
        
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
        Song song = new Song(music, header);
        song.play();
        Thread.sleep(10000);
    }
}