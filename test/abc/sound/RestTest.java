package abc.sound;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Rest concrete implementation of Music.
 *
 */
public class RestTest {

    /*
     * Testing strategy for Rest
     * 
     * Partition for Rest:
     *   duration: 0, >0, integer, decimal
     *   
     * Partition for duration:
     *   output: 0, >0, integer, decimal
     * 
     * Partition for play:
     *   rest durations integer, decimal
     *   atBeat: 0, >0, integer, decimal
     *   output: compare player.toString()
     * 
     * Partition for rescale:
     *   scale: 0, 0<scale<1, 1, >1
     *   output: duration is 0, less than previous, equal to previous, greater than previous
     * 
     * Partition for equals:
     *   obj: not Rest, unequal Rest, equal Rest
     *   output: true, false
     * 
     * Partition for hashCode:
     *   compare hashCodes for two equal Rest
     * 
     * Partition for toString:
     *   test toString isn't empty
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Testing Rest and duration
    // Tests Rest input: duration = 0, integer
    // Tests duration output: 0, integer
    @Test
    public void testRestZero() {
        Rest rest = new Rest(0);
        Double expectedDuration = new Double(0.0);
        Double resultDuration = rest.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Tests Rest input: duration > 0, decimal
    // Tests duration output: > 0, decimal
    @Test
    public void testRestDecimal() {
        Rest rest = new Rest(1.5);
        Double expectedDuration = new Double(1.5);
        Double resultDuration = rest.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Tests Rest input: duration > 0, integer
    // Tests duration output: > 0, integer
    @Test
    public void testDurationZero() {
        Rest rest = new Rest(3);
        Double expectedDuration = new Double(3.0);
        Double resultDuration = rest.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Testing rescale
    // Tests scale = 0, output duration = 0
    @Test
    public void testRescaleZero() {
        Rest rest = new Rest(1);
        Rest newRest = rest.rescale(0);
        Double expectedDuration = new Double(0);
        Double resultDuration = newRest.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Tests 0 < scale < 1, output duration less than previous duration
    @Test
    public void testRescaleLessThanOne() {
        Rest rest = new Rest(1);
        Rest newRest = rest.rescale(0.5);
        Double expectedDuration = new Double(0.5);
        Double resultDuration = newRest.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Tests scale = 1, output duration equal to previous duration
    @Test
    public void testRescaleOne() {
        Rest rest = new Rest(1.5);
        Rest newRest = rest.rescale(1);
        Double expectedDuration = new Double(1.5);
        Double resultDuration = newRest.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Tests scale > 1, output duration greater than previous duration
    @Test
    public void testRescaleGreaterThanOne() {
        Rest rest = new Rest(1.5);
        Rest newRest = rest.rescale(1.5);
        Double expectedDuration = new Double(2.25);
        Double resultDuration = newRest.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Testing equals
    // Tests obj not Rest
    @Test
    public void testEqualsNotRest() {
        Rest rest = new Rest(1);
        Note obj = new Note(1, new Pitch('C'));
        assertFalse(rest.equals(obj));
    }
    
    // Tests obj unequal Rest
    @Test
    public void testEqualsUnequal() {
        Rest rest = new Rest(1);
        Rest obj = new Rest(1.5);
        assertFalse(rest.equals(obj));
    }
    
    // Tests obj equal Rest
    @Test
    public void testEqualsEqual() {
        Rest rest = new Rest(1);
        Rest obj = new Rest(1);
        assertTrue(rest.equals(obj));
        assertEquals(rest.hashCode(), obj.hashCode());
    }
    
    // Testing hashCode
    // Tests 2 equal Rest
    @Test
    public void testHashCodeEqual() {
        Rest rest1 = new Rest(1);
        Rest rest2 = new Rest(1);
        assertEquals(rest1.hashCode(), rest2.hashCode());
    }
    
    // Testing toString
    @Test
    public void testToString() {
        Rest rest = new Rest(1.5);
        assertFalse(rest.toString().isEmpty());
    }
    
}
