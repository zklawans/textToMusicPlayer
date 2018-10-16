package abc.sound;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Tests Chord concrete implementation of Music
 *
 */
public class ChordTest {
    
    /*
     * Testing strategy for Chord
     * 
     * Partition for duration:
     *   notes' duration: 0, >0, different
     *   output: 0, >0
     *          int, double
     * 
     * Partition for getNotes:
     *   notes: 2, more elements
     * 
     * Partition for play:
     *   compare player.toString()
     *   IN SEPERATE FILE
     * 
     * Partition for rescale:
     *   scale: 0, >0
     *   compare output.duration()
     * 
     * Partition for toString:
     *   FOR NOW test toString isn't empty
     * 
     * Partition for equals:
     *   obj: not Chord, unequal Chord, equal Concat
     * 
     * Partition for hashCode:
     *   test equal objects have same hashCode
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Testing getNotes
    // Tests 2 note
    @Test
    public void testgetNotesTwo() {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(2, new Pitch('C')));
        notes.add(new Note(2, new Pitch('E')));
        Chord chord = new Chord(notes);
        assertEquals(notes, chord.getNotes());
    }
    
    // Testing getNotes
    // Tests more note
    @Test
    public void testgetNotesThree() {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(2, new Pitch('C')));
        notes.add(new Note(2, new Pitch('E')));
        notes.add(new Note(2, new Pitch('G')));
        Chord chord = new Chord(notes);
        assertEquals(notes, chord.getNotes());
    }
    
    // Testing duration
    // Tests output = 0, int
    @Test
    public void testDurationZero() {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(0, new Pitch('C')));
        notes.add(new Note(0, new Pitch('E')));
        Chord chord = new Chord(notes);
        Double expected = new Double(0.0);
        Double result = chord.duration();
        assertEquals(expected, result);
    }
    
    // Tests output > 0, int
    @Test
    public void testDurationAboveZero() {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(2, new Pitch('C')));
        notes.add(new Note(2, new Pitch('E')));
        Chord chord = new Chord(notes);
        Double expected = new Double(2.0);
        Double result = chord.duration();
        assertEquals(expected, result);
    }
    
    // Tests output > 0, different, decimal
    @Test
    public void testDurationDecimal() {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(2.3, new Pitch('C')));
        notes.add(new Note(2.5, new Pitch('E')));
        Chord chord = new Chord(notes);
        Double expected = new Double(2.5);
        Double result = chord.duration();
        assertEquals(expected, result);
    }
    
    // Testing rescale
    // Tests scale = 0
    @Test
    public void testRescaleZero() {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(1, new Pitch('C')));
        notes.add(new Note(2, new Pitch('E')));
        Chord chord = new Chord(notes);
        Double expected = new Double(0);
        Double result = chord.rescale(0).duration();
        assertEquals(expected, result);
    }
    
    // Tests scale > 0
    @Test
    public void testRescaleAboveZero() {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(1, new Pitch('C')));
        notes.add(new Note(2, new Pitch('E')));
        Chord chord = new Chord(notes);
        Double expected = new Double(4);
        Double result = chord.rescale(2).duration();
        assertEquals(expected, result);
    }
    
    // Testing equals
    // Tests obj not Chord
    @Test
    public void testEqualsNotConcat() {
        Rest obj = new Rest(1);
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(1, new Pitch('C')));
        notes.add(new Note(2, new Pitch('E')));
        Chord chord = new Chord(notes);
        assertFalse(chord.equals(obj));
    }
    
    // Tests obj unequal Chord
    @Test
    public void testEqualsUnequalChord() {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(1, new Pitch('C')));
        notes.add(new Note(2, new Pitch('E')));
        Chord chord1 = new Chord(notes);
        Set<Note> notes2 = new HashSet<>();
        notes2.add(new Note(1, new Pitch('C')));
        notes2.add(new Note(1, new Pitch('E')));
        Chord chord2 = new Chord(notes2);
        assertFalse(chord1.equals(chord2));
    }
    
    // Tests obj is equal, hashCode is equal
    @Test
    public void testEqualsEqual() {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(1, new Pitch('C')));
        notes.add(new Note(2, new Pitch('E')));
        Chord chord1 = new Chord(notes);
        Set<Note> notes2 = new HashSet<>();
        notes2.add(new Note(1, new Pitch('C')));
        notes2.add(new Note(2, new Pitch('E')));
        Chord chord2 = new Chord(notes2);
        assertTrue(chord1.equals(chord2));
    }
    
    // Testing hashCode
    @Test
    public void testHashCode() {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(1, new Pitch('C')));
        notes.add(new Note(2, new Pitch('E')));
        Chord chord1 = new Chord(notes);
        Set<Note> notes2 = new HashSet<>();
        notes2.add(new Note(1, new Pitch('C')));
        notes2.add(new Note(2, new Pitch('E')));
        Chord chord2 = new Chord(notes2);
        assertEquals(chord1.hashCode(), chord2.hashCode());
    }
    
    // Testing toString
    @Test
    public void testToString() {
        Set<Note> notes = new HashSet<>();
        notes.add(new Note(1, new Pitch('C')));
        notes.add(new Note(3, new Pitch('F')));
        Chord chord = new Chord(notes);
        assertFalse(chord.toString().isEmpty());
    }
    
}
