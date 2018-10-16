package abc.sound;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Tests Tuplet concrete implementation of Music
 *
 */
public class TupletTest {
    
    /*
     * Testing strategy for Tuplet
     * 
     * type of tuplet: duplet, triplet, or quadruplet
     * 
     * Partition for duration:
     *   notes' duration: 0, >0
     *   output: 0, >0
     * 
     * Partition for play:
     *   compare player.toString
     *   IN SEPERATE FILE
     * 
     * Partition for rescale:
     *   scale: 0, >0
     *   compare output.duration()
     * 
     * Partition for equals:
     *   obj: not Tuplet, unequal Tuplet, equal Concat
     * 
     * Partition for hashCode:
     *   test equal objects have same hashCode
     *   
     * Partition for toString:
     *   test toString isn't empty
     */
    
    private static final Set<Note> NOTES_ZERO = new HashSet<>(Arrays.asList(new Note(0, new Pitch('C')), new Note(0, new Pitch('E'))));
    private static final Set<Note> NOTES = new HashSet<>(Arrays.asList(new Note(3, new Pitch('C')), new Note(3, new Pitch('E'))));
    private static final Music[] NOTES_DUPLET_ZERO = {new Note(0, new Pitch('C')), new Note(0, new Pitch('C'))};
    private static final Music[] NOTES_DUPLET = {new Note(3, new Pitch('C')), new Note(3, new Pitch('C'))};
    private static final Music[] NOTES_TRIPLET_ZERO = {new Chord(NOTES_ZERO), new Chord(NOTES_ZERO), new Chord(NOTES_ZERO)};
    private static final Music[] NOTES_TRIPLET = {new Chord(NOTES), new Chord(NOTES), new Chord(NOTES)};
    private static final Music[] NOTES_QUADRUPLET_ZERO = {new Note(0, new Pitch('C')), new Chord(NOTES_ZERO), new Chord(NOTES_ZERO), new Chord(NOTES_ZERO)};
    private static final Music[] NOTES_QUADRUPLET = {new Note(3, new Pitch('C')), new Note(3, new Pitch('C')), new Note(3, new Pitch('C')), new Chord(NOTES)};
    private static final Tuplet DUPLET_ZERO = new Tuplet(NOTES_DUPLET_ZERO);
    private static final Tuplet DUPLET = new Tuplet(NOTES_DUPLET);
    private static final Tuplet TRIPLET_ZERO = new Tuplet(NOTES_TRIPLET_ZERO);
    private static final Tuplet TRIPLET = new Tuplet(NOTES_TRIPLET);
    private static final Tuplet QUADRUPLET_ZERO = new Tuplet(NOTES_QUADRUPLET_ZERO);
    private static final Tuplet QUADRUPLET = new Tuplet(NOTES_QUADRUPLET);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Tests type: duplet
    
    // Testing duration
    // Tests output = 0, int
    @Test
    public void testDurationDupletZero() {
        Double expected = new Double(0.0);
        Double result = DUPLET_ZERO.duration();
        assertEquals(expected, result);
    }
    
    // Tests output > 0, int
    @Test
    public void testDurationDuplet() {
        Double expected = new Double(9);
        Double result = DUPLET.duration();
        assertEquals(expected, result);
    }
    
    // Testing rescale
    // Tests scale = 0
    @Test
    public void testRescaleDupletZero() {
        Double expected = new Double(0);
        Double result = DUPLET.rescale(0).duration();
        assertEquals(expected, result);
    }
    
    // Tests scale > 0
    @Test
    public void testRescaleDupletAboveZero() {
        Double expected = new Double(18);
        Double result = DUPLET.rescale(2).duration();
        assertEquals(expected, result);
    }
    
    
    // Tests type: triplet
    
    // Testing duration
    // Tests output = 0, int
    @Test
    public void testDurationTripletZero() {
        Double expected = new Double(0.0);
        Double result = TRIPLET_ZERO.duration();
        assertEquals(expected, result);
    }
    
    // Tests output > 0, int
    @Test
    public void testDurationTriplet() {
        Double expected = new Double(6);
        Double result = TRIPLET.duration();
        assertEquals(expected, result);
    }
    
    // Testing rescale
    // Tests scale = 0
    @Test
    public void testRescaleTripletZero() {
        Double expected = new Double(0);
        Double result = TRIPLET.rescale(0).duration();
        assertEquals(expected, result);
    }
    
    // Tests scale > 0
    @Test
    public void testRescaleTripletAboveZero() {
        Double expected = new Double(12);
        Double result = TRIPLET.rescale(2).duration();
        assertEquals(expected, result);
    }
    
    
    // Tests type: quadraplet
    
    // Testing duration
    // Tests output = 0, int
    @Test
    public void testDurationQuadrupletZero() {
        Double expected = new Double(0.0);
        Double result = QUADRUPLET_ZERO.duration();
        assertEquals(expected, result);
    }
    
    // Tests output > 0, int
    @Test
    public void testDurationQuadruplet() {
        Double expected = new Double(9);
        Double result = QUADRUPLET.duration();
        assertEquals(expected, result);
    }
    
    // Testing rescale
    // Tests scale = 0
    @Test
    public void testRescaleQuadrupletZero() {
        Double expected = new Double(0);
        Double result = QUADRUPLET.rescale(0).duration();
        assertEquals(expected, result);
    }
    
    // Tests scale > 0
    @Test
    public void testRescaleQuadrupletAboveZero() {
        Double expected = new Double(18);
        Double result = QUADRUPLET.rescale(2).duration();
        assertEquals(expected, result);
    }
    
    // Testing equals
    // Tests obj not Tuplet
    @Test
    public void testEqualsNotConcat() {
        Rest obj = new Rest(1);
        assertFalse(TRIPLET.equals(obj));
    }
    
    // Tests obj unequal Tuplet
    @Test
    public void testEqualsUnequalTuplet() {
        assertFalse(TRIPLET.equals(DUPLET));
    }
    
    // Tests obj unequal Tuplet
    @Test
    public void testEqualsUnequalTuplet2() {
        assertFalse(TRIPLET.equals(TRIPLET_ZERO));
    }
    
    // Tests obj is equal, hashCode is equal
    @Test
    public void testEqualsEqual() {
        assertTrue(QUADRUPLET.equals(QUADRUPLET));
    }
    
    // Testing hashCode
    @Test
    public void testHashCode() {
        assertEquals(DUPLET.hashCode(), DUPLET.hashCode());
    }
    
    // Testing toString
    @Test
    public void testToString() {
        assertFalse(DUPLET.toString().isEmpty());
        assertFalse(TRIPLET.toString().isEmpty());
        assertFalse(QUADRUPLET.toString().isEmpty());
    }
    
}
