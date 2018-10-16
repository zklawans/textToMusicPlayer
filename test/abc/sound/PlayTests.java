package abc.sound;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

/**
 * Tests play methods for all Music types.
 * @category no_didit
 *
 */
public class PlayTests {
    
    private final static Note A = new Note(1, new Pitch('A'));
    private final static Note B = new Note(1, new Pitch('B'));
    private final static Note C = new Note(1, new Pitch('C'));
    private final static Note HIGH_C = new Note(1, new Pitch('C')).transpose(12);
    private final static Note D = new Note(1, new Pitch('D'));
    private final static Note E = new Note(1, new Pitch('E'));
    private final static Note F = new Note(1, new Pitch('F'));
    private final static Note G = new Note(1, new Pitch('G'));

    // Chord play tests
    // Tests player contains notes, atBeat > 0
    @Test
    public void testChordPlayNotes() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(1, new Pitch('C')));
        notes.add(new Note(2, new Pitch('E')));
        Chord chord = new Chord(notes);
        SequencePlayer player = new SequencePlayer(100, 1);
        double atBeat = 5;
        chord.play(player, atBeat);
        String expected1 = "Event: NOTE_ON  Pitch: 60  Tick: 5\n"
                + "Event: NOTE_ON  Pitch: 64  Tick: 5\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 6\n"
                + "Event: NOTE_OFF Pitch: 64  Tick: 7\n"
                + "Meta event: END_OF_TRACK Tick: 7\n";
        String expected2 = "Event: NOTE_ON  Pitch: 64  Tick: 5\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 5\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 6\n"
                + "Event: NOTE_OFF Pitch: 64  Tick: 7\n"
                + "Meta event: END_OF_TRACK Tick: 7\n";
        assertTrue(player.toString().equals(expected1) || player.toString().equals(expected2));
        player.play();
        Thread.sleep(5000);
    }
    
    // Measure play tests
    // Tests player contains notes, atBeat > 0
    @Test
    public void testMeasurePlayNotes() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        Measure measure = new Measure(Arrays.asList(new Note(1, new Pitch('C')), new Rest(2), new Note(2, new Pitch('C'))), 5, false, false, false, false, false);
        SequencePlayer player = new SequencePlayer(100, 1);
        double atBeat = 5;
        measure.play(player, atBeat);
        String expected = "Event: NOTE_ON  Pitch: 60  Tick: 5\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 6\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 8\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 10\n"
                + "Meta event: END_OF_TRACK Tick: 10\n";
        assertEquals(expected, player.toString());
        player.play();
        Thread.sleep(7000);
        
    }
    
    // Tests player is empty, atBeat = 0
    @Test
    public void testMeasurePlayEmpty() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, false, false, false, false, false);
        SequencePlayer player = new SequencePlayer(100, 12);
        double atBeat = 0;
        measure.play(player, atBeat);
        assertEquals("Meta event: END_OF_TRACK Tick: 0\n", player.toString());
        player.play();
        Thread.sleep(1000);
    }
    
    // Note play tests
    // Tests atBeat = 0, integer, note duration is integer
    @Test
    public void testNotePlayIntegerInteger() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        String expected = "Event: NOTE_ON  Pitch: 60  Tick: 0\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 12\n"
                + "Meta event: END_OF_TRACK Tick: 12\n";
        Note note = new Note(1, new Pitch('C'));
        SequencePlayer player = new SequencePlayer(100, 12);
        double atBeat = 0;
        note.play(player, atBeat);
        assertEquals(expected, player.toString());
        player.play();
        Thread.sleep(2000);
    }
    
    // Tests atBeat > 0, decimal, note duration is integer
    @Test
    public void testNotePlayDecimalInteger() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        String expected = "Event: NOTE_ON  Pitch: 60  Tick: 15\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 25\n"
                + "Meta event: END_OF_TRACK Tick: 25\n";
        Note note = new Note(1, new Pitch('C'));
        SequencePlayer player = new SequencePlayer(100, 10);
        double atBeat = 1.5;
        note.play(player, atBeat);
        assertEquals(expected, player.toString());
        player.play();
        Thread.sleep(2000);
    }
    
    // Tests atBeat > 0, decimal, note duration is decimal
    @Test
    public void testNotePlayDecimalDecimal() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        String expected = "Event: NOTE_ON  Pitch: 60  Tick: 15\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 27\n"
                + "Meta event: END_OF_TRACK Tick: 27\n";
        Note note = new Note(1.2, new Pitch('C'));
        SequencePlayer player = new SequencePlayer(100, 10);
        double atBeat = 1.5;
        note.play(player, atBeat);
        assertEquals(expected, player.toString());
        player.play();
        Thread.sleep(2000);
    }
    
    // Rest play tests
    // Tests rest duration integer, atBeat = 0, integer, player string empty
    @Test
    public void testRestPlayZero() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        Rest rest = new Rest(0);
        SequencePlayer player = new SequencePlayer(100, 12);
        double atBeat = 0;
        rest.play(player, atBeat);
        assertEquals("Meta event: END_OF_TRACK Tick: 0\n", player.toString());
        player.play();
        Thread.sleep(3000);
    }
    
    // Tests rest duration decimal, atBeat > 0, integer, player string empty
    @Test
    public void testRestPlayDecimal() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        Rest rest = new Rest(1.5);
        SequencePlayer player = new SequencePlayer(100, 12);
        double atBeat = 1;
        rest.play(player, atBeat);
        assertEquals("Meta event: END_OF_TRACK Tick: 0\n", player.toString());
        Thread.sleep(3000);
    }
    
    // Tests rest duration integer, atBeat > 0, decimal, player string empty
    @Test
    public void testRestPlayInteger() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        Rest rest = new Rest(1);
        SequencePlayer player = new SequencePlayer(100, 12);
        double atBeat = 1.5;
        rest.play(player, atBeat);
        assertEquals("Meta event: END_OF_TRACK Tick: 0\n", player.toString());
        player.play();
        Thread.sleep(3000);
    }
    
    // Tuplet play tests
    // Tests player contains notes, atBeat > 0
    @Test
    public void testTupletPlayDupletNotes() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        SequencePlayer player = new SequencePlayer(100, 12);
        double atBeat = 5;
        Music[] notesDuplet = {new Note(3, new Pitch('C')), new Note(3, new Pitch('C'))};
        Tuplet duplet = new Tuplet(notesDuplet);
        duplet.play(player, atBeat);
        String expected = "Event: NOTE_ON  Pitch: 60  Tick: 60\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 114\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 114\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 168\n"
                + "Meta event: END_OF_TRACK Tick: 168\n";
        assertEquals(expected, player.toString());
        player.play();
        Thread.sleep(9000);
    }
    
    // Tests player contains notes, atBeat > 0
    @Test
    public void testTupletPlayQuadrupletNotes() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        SequencePlayer player = new SequencePlayer(100, 12);
        double atBeat = 5;
        Set<Note> notes = new HashSet<>(Arrays.asList(new Note(3, new Pitch('C')), new Note(3, new Pitch('E'))));
        Music [] notesQuadruplet = {new Note(3, new Pitch('C')), new Note(3, new Pitch('C')), new Note(3, new Pitch('C')), new Chord(notes)};
        Tuplet quadruplet = new Tuplet(notesQuadruplet);
        quadruplet.play(player, atBeat);
        String expected1 = "Event: NOTE_ON  Pitch: 60  Tick: 60\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 87\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 87\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 114\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 114\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 141\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 141\n"
                + "Event: NOTE_ON  Pitch: 64  Tick: 141\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 168\n"
                + "Event: NOTE_OFF Pitch: 64  Tick: 168\n"
                + "Meta event: END_OF_TRACK Tick: 168\n";
        String expected2 = "Event: NOTE_ON  Pitch: 60  Tick: 60\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 87\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 87\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 114\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 114\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 141\n"
                + "Event: NOTE_ON  Pitch: 64  Tick: 141\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 141\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 168\n"
                + "Event: NOTE_OFF Pitch: 64  Tick: 168\n"
                + "Meta event: END_OF_TRACK Tick: 168\n";
        String expected3 = "Event: NOTE_ON  Pitch: 60  Tick: 60\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 87\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 87\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 114\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 114\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 141\n"
                + "Event: NOTE_ON  Pitch: 64  Tick: 141\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 141\n"
                + "Event: NOTE_OFF Pitch: 64  Tick: 168\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 168\n"
                + "Meta event: END_OF_TRACK Tick: 168\n";
        String expected4 = "Event: NOTE_ON  Pitch: 60  Tick: 60\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 87\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 87\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 114\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 114\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 141\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 141\n"
                + "Event: NOTE_ON  Pitch: 64  Tick: 141\n"
                + "Event: NOTE_OFF Pitch: 64  Tick: 168\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 168\n"
                + "Meta event: END_OF_TRACK Tick: 168\n";
        assertTrue(player.toString().equals(expected4) || player.toString().equals(expected3) || player.toString().equals(expected2) || player.toString().equals(expected1));
        player.play();
        Thread.sleep(10000);
    }
    
    // Voices play test
    // Tests atBeat = 0, integer
    @Test
    public void testVoicesPlayInteger() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        String expected = "Event: NOTE_ON  Pitch: 72  Tick: 0\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 0\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 10\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 10\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 10\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 10\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 20\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 20\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 20\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 20\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 30\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 30\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 30\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 30\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 40\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 40\n"
                + "Meta event: END_OF_TRACK Tick: 40\n";
        Measure measure = new Measure(Arrays.asList(C, C, C, C), 4, false, false, false, false, false);
        Voice voice = new Voice(Arrays.asList(measure));
        Measure measure2 = new Measure(Arrays.asList(HIGH_C, HIGH_C, HIGH_C, HIGH_C), 4, false, false, false, false, false);
        Voice voice2 = new Voice(Arrays.asList(measure2));
        Map<String, Voice> voiceMap = new HashMap<>();
        voiceMap.put("upper", voice2);
        voiceMap.put("lower", voice);
        Voices voices = new Voices(voiceMap);
        SequencePlayer player = new SequencePlayer(100, 10);
        double atBeat = 0;
        voices.play(player, atBeat);
        assertEquals(expected, player.toString());
        player.play();
        Thread.sleep(4000);
    }
    
    // Tests atBeat > 0, decimal
    @Test
    public void testVoicesPlayDecimal() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        String expected = "Event: NOTE_ON  Pitch: 60  Tick: 12\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 12\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 22\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 22\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 22\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 22\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 32\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 32\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 32\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 32\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 42\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 42\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 42\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 42\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 52\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 52\n"
                + "Meta event: END_OF_TRACK Tick: 52\n";
        Measure measure = new Measure(Arrays.asList(C, C, C, C), 4, false, false, false, false, false);
        Voice voice = new Voice(Arrays.asList(measure));
        Measure measure2 = new Measure(Arrays.asList(HIGH_C, HIGH_C, HIGH_C, HIGH_C), 4, false, false, false, false, false);
        Voice voice2 = new Voice(Arrays.asList(measure2));
        Map<String, Voice> voiceMap = new HashMap<>();
        voiceMap.put("lower", voice);
        voiceMap.put("upper", voice2);
        Voices voices = new Voices(voiceMap);
        SequencePlayer player = new SequencePlayer(100, 10);
        double atBeat = 1.2;
        voices.play(player, atBeat);
        assertEquals(expected, player.toString());
        player.play();
        Thread.sleep(5000);
    }
    
    // Voice play test
    @Test
    public void testVoicePlayDefaultValues() {
        List<Music> notes = Arrays.asList(C, D, E, F, G, A, B, HIGH_C);
        Measure measure = new Measure(notes, 8, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure);
        Voice voice = new Voice(notes2);
        try {
            SequencePlayer player = new SequencePlayer(100,24);
            voice.play(player, 0);
            player.play();
            Thread.sleep(10000);
        } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testVoicePlayMajorSectionRepeat() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        List<Music> notes1 = Arrays.asList(C, C, C, C);
        List<Music> notes2 = Arrays.asList(HIGH_C, HIGH_C, HIGH_C, HIGH_C);
        Measure measure1 = new Measure(notes1, 4, false, false, false, false, true);
        Measure measure2 = new Measure(notes2, 4, false, true, false, false, false);
        Voice voice = new Voice(Arrays.asList(measure1, measure2));
        
        SequencePlayer player = new SequencePlayer(100, 10);
        voice.play(player, 0);
        String expected = "Event: NOTE_ON  Pitch: 60  Tick: 0\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 10\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 10\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 20\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 20\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 30\n"
                + "Event: NOTE_ON  Pitch: 60  Tick: 30\n"
                + "Event: NOTE_OFF Pitch: 60  Tick: 40\n"
                
                + "Event: NOTE_ON  Pitch: 72  Tick: 40\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 50\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 50\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 60\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 60\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 70\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 70\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 80\n"
                
                + "Event: NOTE_ON  Pitch: 72  Tick: 80\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 90\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 90\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 100\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 100\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 110\n"
                + "Event: NOTE_ON  Pitch: 72  Tick: 110\n"
                + "Event: NOTE_OFF Pitch: 72  Tick: 120\n"
                + "Meta event: END_OF_TRACK Tick: 120\n";
        assertEquals(expected, player.toString());
        
        player.play();
        Thread.sleep(8000);
    }
    
    @Test
    public void testVoicePlayMeasure() {
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
        try {
            SequencePlayer player = new SequencePlayer(100,24);
            double result = voice.playMeasure(player, 0, 3);
            player.play();
            assertTrue("expected 4", result == measure1.duration());
            Thread.sleep(5000);
        } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testVoicePlayRepeat() {
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
        try {
            SequencePlayer player = new SequencePlayer(100,24);
            voice.play(player, 0);
            player.play();
            Thread.sleep(10000);
        } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testVoicePlayRepeatWithoutStart() {
        Rest oneBeat = new Rest(1);
        Note cHalf = C.transpose(-1).rescale(0.5);
        Note dHalf = D.rescale(0.5);
        Note eHalf = E.rescale(0.5);
        List<Music> bar1 = Arrays.asList(F, F, F, oneBeat);
        List<Music> bar2 = Arrays.asList(cHalf, cHalf, dHalf, dHalf, eHalf, eHalf, oneBeat);
        Measure measure1 = new Measure(bar1, 4, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 4, false, true, false, false, false);
        List<Measure> notes = Arrays.asList(measure1, measure2);
        Voice voice = new Voice(notes);
        try {
            SequencePlayer player = new SequencePlayer(100,24);
            voice.play(player, 0);
            player.play();
            Thread.sleep(10000);
        } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testVoicePlayDoubleRepeat() {
        Rest oneBeat = new Rest(1);
        Note cHalf = C.transpose(-1).rescale(0.5);
        Note dHalf = D.rescale(0.5);
        Note eHalf = E.rescale(0.5);
        List<Music> bar1 = Arrays.asList(F, F, F, oneBeat);
        List<Music> bar2 = Arrays.asList(cHalf, cHalf, dHalf, dHalf, eHalf, eHalf, oneBeat);
        Measure measure1 = new Measure(bar1, 4, false, false, false, false, false);
        Measure measure3 = new Measure(bar1, 4, true, false, false, false, false);
        Measure measure2 = new Measure(bar2, 4, false, true, false, false, false);
        List<Measure> notes = Arrays.asList(measure1, measure2, measure3, measure2);
        Voice voice = new Voice(notes);
        try {
            SequencePlayer player = new SequencePlayer(100,24);
            voice.play(player, 0);
            player.play();
            Thread.sleep(20000);
        } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testVoicePlayMultipleEndings() {
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
        try {
            SequencePlayer player = new SequencePlayer(100,24);
            voice.play(player, 0);
            player.play();
            Thread.sleep(10000);
        } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
