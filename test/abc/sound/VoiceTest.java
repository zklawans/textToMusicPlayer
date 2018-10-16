package abc.sound;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * Tests for Voice concrete implementation of Music.
 *
 */
public class VoiceTest {
    
    /*
     * Testing strategy for Voice
     * 
     * Partition for duration:
     *   output: 0, >0
     *   
     * Partition for append:
     *   empty, non-empty, voice
     * 
     * Partition for play:
     *   player: empty, without repeat, simple repeat, different endings, repeat from start, repeat from end major section
     *   atBeat: 0, >0
     *   compare player.toString
     *   IN SEPERATE FILE
     *   
     * Partition for playMeasure:
     *   play a measure
     *   IN SEPERATE FILE
     *   
     * Partition for getNumMeasure:
     *   result: 0, >0
     * 
     * Partition for rescale: 
     *   scale: 0, >0
     *   compare output.duration()
     *
     * Partition for toString:
     *   test toString isn't empty
     * 
     * Partition for equals:
     *   obj: not Voice, unequal Voice, equal Voice
     * 
     * Partition for hashCode:
     *   test equal objects have same hashCode
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
    
    // Testing duration
    @Test
    public void testDurationZero() {
        Voice voice = new Voice();
        assertTrue("expecte 0", 0 == voice.duration());
    }
    @Test
    public void testDurationNonzero() {
        List<Music> notes = Arrays.asList(C, D, E, F, G, A, B, HIGH_C);
        Measure measure = new Measure(notes, 8, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure);
        Voice voice = new Voice(notes2);
        assertTrue("expecte 8", 8 == voice.duration());
    }
    
    // Testing getNumMeasure
    @Test
    public void testGetNumMeasureZero() {
        Voice voice = new Voice(Collections.emptyList());
        assertTrue("expecte 0", 0 == voice.getNumMeasures());
    }
    
    @Test
    public void testGetNumMeasureNonZero() {
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
        assertTrue("expecte 4", 4 == voice.getNumMeasures());
    }
    
    // Testing rescale
    @Test
    public void testRescaleZero() {
        Note cFlat = C.transpose(-1);
        Note highCFlat = HIGH_C.transpose(-1);
        List<Music> bar1 = Arrays.asList(cFlat, cFlat, highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), new Rest(4));
        List<Music> bar2 = Arrays.asList(cFlat, cFlat, highCFlat, highCFlat, cFlat, highCFlat, new Rest(2));
        Measure measure1 = new Measure(bar1, 8, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 8, false, false, false, false, false);
        List<Measure> notes = Arrays.asList(measure1, measure2);
        Voice voice = new Voice(notes);
        assertTrue("expecte 0", 0 == voice.rescale(0).duration());
    }
    
    @Test
    public void testRescaleNonzero() {
        Note cFlat = C.transpose(-1);
        Note highCFlat = HIGH_C.transpose(-1);
        List<Music> bar1 = Arrays.asList(cFlat, cFlat, highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), new Rest(4));
        List<Music> bar2 = Arrays.asList(cFlat, cFlat, highCFlat, highCFlat, cFlat, highCFlat, new Rest(2));
        Measure measure1 = new Measure(bar1, 8, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 8, false, false, false, false, false);
        List<Measure> notes = Arrays.asList(measure1, measure2);
        Voice voice = new Voice(notes);
        assertTrue("expecte 8", 32 == voice.rescale(2).duration());
    }
    
    // Testing append
    @Test
    public void testAppendEmpty() {
        Note cFlat = C.transpose(-1);
        Note highCFlat = HIGH_C.transpose(-1);
        List<Music> bar1 = Arrays.asList(cFlat, cFlat, highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), new Rest(4));
        List<Music> bar2 = Arrays.asList(cFlat, cFlat, highCFlat, highCFlat, cFlat, highCFlat, new Rest(2));
        Measure measure1 = new Measure(bar1, 8, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 8, false, false, false, false, false);
        List<Measure> notes = Arrays.asList(measure1, measure2);
        Voice voice = new Voice(notes);
        assertEquals(voice, voice.append(Collections.emptyList()));
    }
    
    @Test
    public void testAppendNonEmpty() {
        Note cFlat = C.transpose(-1);
        Note highCFlat = HIGH_C.transpose(-1);
        List<Music> bar1 = Arrays.asList(cFlat, cFlat, highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), new Rest(4));
        List<Music> bar2 = Arrays.asList(cFlat, cFlat, highCFlat, highCFlat, cFlat, highCFlat, new Rest(2));
        Measure measure1 = new Measure(bar1, 8, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 8, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure1, measure2);
        List<Measure> notes = Arrays.asList(measure1);
        Voice voice2 = new Voice(notes2);
        Voice voice = new Voice(notes);
        assertEquals(voice2, voice.append(Arrays.asList(measure2)));
    }
    
    @Test
    public void testAppendVoice() {
        Note cFlat = C.transpose(-1);
        Note highCFlat = HIGH_C.transpose(-1);
        List<Music> bar1 = Arrays.asList(cFlat, cFlat, highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), new Rest(4));
        List<Music> bar2 = Arrays.asList(cFlat, cFlat, highCFlat, highCFlat, cFlat, highCFlat, new Rest(2));
        Measure measure1 = new Measure(bar1, 8, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 8, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure1, measure2);
        List<Measure> notes1 = Arrays.asList(measure2);
        List<Measure> notes = Arrays.asList(measure1);
        Voice voice2 = new Voice(notes2);
        Voice voice1 = new Voice(notes1);
        Voice voice = new Voice(notes);
        assertEquals(voice2, voice.append(voice1));
    }

    // Testing equals
    // Tests obj not Voice
    @Test
    public void testEqualsNotVoice() {
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
        Rest obj = new Rest(1);
        assertFalse(voice.equals(obj));
    }
    
    // Tests obj unequal Voice
    @Test
    public void testEqualsUnequalVoice() {
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
        List<Measure> notes2 = Arrays.asList(measure2, measure1, measure3);
        Voice voice2 = new Voice(notes2);
        assertFalse(voice.equals(voice2));
    }
    
    // Tests obj is equal, hashCode is equal
    @Test
    public void testEqualsVoice() {
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
        Voice voice2 = new Voice(notes);
        assertTrue(voice.equals(voice2));
    }
    
    @Test
    public void testHashCode() {
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
        Voice voice2 = new Voice(notes);
        assertEquals(voice2.hashCode(), voice.hashCode());
    }
    
    // Testing toString
    @Test
    public void testToString() {
        List<Music> bar1 = Arrays.asList(C, C, C, C);
        List<Music> bar2 = Arrays.asList(E, E, E, E);
        Measure measure1 = new Measure(bar1, 4, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 4, false, false, false, false, false);
        List<Measure> notes = Arrays.asList(measure1, measure2);
        Voice voice = new Voice(notes);
        assertFalse(voice.toString().isEmpty());
    }
}
