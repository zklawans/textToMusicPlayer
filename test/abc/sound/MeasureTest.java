package abc.sound;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * Tests Measure concrete implementation of Music
 *
 */
public class MeasureTest {
    
    /*
     * Testing strategy for Measure
     *   
     * Partition for isStartRepeat:
     *   output: true, false
     * 
     * Partition for isEndRepeat:
     *   output: true, false
     *   
     * Partition for isFirstEnding:
     *   output: true, false
     *   
     * Partition for isSecondEnding:
     *   output: true, false
     *   
     * Partition for isEndMajorSection:
     *   output: true, false
     * 
     * Partition for duration:
     *   output: 0, >0
     * 
     * Partition for play:
     *   player: empty, contains notes
     *   atBeat: 0, >0
     *   compare player.toString
     *   IN SEPERATE FILE
     * 
     * Partition for rescale:
     *   scale: 0, >0
     *   compare output.duration()
     *
     * Partition for toString:
     *    test toString isn't empty
     * 
     * Partition for equals:
     *   obj: not Measure, unequal Measure, equal Measure
     * 
     * Partition for hashCode:
     *   test equal objects have same hashCode
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testIsStartRepeatFalse() {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, false, false, false, false,false);
        assertFalse("expected false", measure.isStartRepeat());
    }
    
    @Test
    public void testIsStartRepeatTrue() {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, true, false, false, false,false);
        assertTrue("expected true", measure.isStartRepeat());
    }
    
    @Test
    public void testIsEndRepeatFalse() {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, false, false, false, false,false);
        assertFalse("expected false", measure.isEndRepeat());
    }
    
    @Test
    public void testIsEndRepeatTrue() {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, false, true, false, false,false);
        assertTrue("expected true", measure.isEndRepeat());
    }
    
    @Test
    public void testIsStartFirstEndingFalse() {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, false, false, false, false,false);
        assertFalse("expected false", measure.isStartFirstEnding());
    }
    
    @Test
    public void testIsStartFirstEndingTrue() {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, false, false, true, false,false);
        assertTrue("expected true", measure.isStartFirstEnding());
    }
    
    @Test
    public void testIsStartSecondEndingFalse() {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, false, false, false, false,false);
        assertFalse("expected false", measure.isStartSecondEnding());
    }
    
    @Test
    public void testIsStartSecondEndingTrue() {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, false, false, false, true, false);
        assertTrue("expected true", measure.isStartSecondEnding());
    }
    
    @Test
    public void testIsEndMajorSectionTrue() {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, false, false, false, true, true);
        assertTrue("expected true", measure.isEndMajorSection());
    }
    
    @Test
    public void testIsEndMajorSectionFalse() {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, false, false, false, true, false);
        assertFalse("expected false", measure.isEndMajorSection());
    }
    
    // Testing duration
    // Tests output = 0, int
    @Test
    public void testDurationZero() {
        Measure measure = new Measure(Arrays.asList(new Rest(0), new Rest(0)), 0, false, false, false, false,false);
        Double expected = new Double(0.0);
        Double result = measure.duration();
        assertEquals(expected, result);
    }
    
    // Tests output > 0, int
    @Test
    public void testDurationAboveZero() {
        Measure measure = new Measure(Arrays.asList(new Rest(1), new Note(2, new Pitch('C'))), 3, false, false, false, false,false);
        Double expected = new Double(3.0);
        Double result = measure.duration();
        assertEquals(expected, result);
    }
    
    // Testing rescale
    // Tests scale = 0
    @Test
    public void testRescaleZero() {
        Measure measure = new Measure(Arrays.asList(new Note(1, new Pitch('C')), new Note(2, new Pitch('C'))), 3, false, false, false, false,false);
        Measure rescaled = (Measure)measure.rescale(0);
        Double expected = new Double(0);
        Double result = rescaled.duration();
        assertEquals(expected, result);
    }
    
    // Tests scale > 0
    @Test
    public void testRescaleAboveZero() {
        Measure measure = new Measure(Arrays.asList(new Note(1, new Pitch('C')), new Note(2, new Pitch('C'))), 3, false, false, false, false,false);
        Measure rescaled = measure.rescale(2);
        Double expected = new Double(6);
        Double result = rescaled.duration();
        assertEquals(expected, result);
    }
    
    // Testing equals
    // Tests obj not Measure
    @Test
    public void testEqualsNotMeasure() {
        Rest obj = new Rest(1);
        Measure measure = new Measure(Arrays.asList(new Rest(1), new Note(1, new Pitch('C'))), 2, false, false, false, false,false);
        assertFalse(measure.equals(obj));
    }
    
    // Tests obj unequal Measure
    @Test
    public void testEqualsUnequalMeasure() {
        Measure obj = new Measure(Arrays.asList(new Note(1.5, new Pitch('C')), new Note(1.5, new Pitch('C')), new Note(1, new Pitch('C'))), 4, false, false, false, false,false);
        Measure measure = new Measure(Arrays.asList(new Note(2, new Pitch('C')), new Rest(1)), 3, false, false, false, false,false);
        assertFalse(measure.equals(obj));
    }
    
    // Tests obj is equal, hashCode is equal
    @Test
    public void testEqualsEqual() {
        Measure obj = new Measure(Arrays.asList(new Note(1, new Pitch('C')), new Rest(2)), 3, false, false, false, false,false);
        Measure measure = new Measure(Arrays.asList(new Note(1, new Pitch('C')), new Rest(2)), 3, false, false, false, false,false);
        assertTrue(measure.equals(obj));
        assertEquals(measure.hashCode(), obj.hashCode());
    }
    
    @Test
    public void testHashCodeRest() {
        Rest m1 = new Rest(1);
        Rest m2 = new Rest(2);
        Measure measure = new Measure(Arrays.asList(m1, m2, new Rest(2)), 5, false, false, false, false,false);
        Measure measure2 = new Measure(Arrays.asList(m1, m2, new Rest(2)), 5, false, false, false, false,false);
        assertEquals(measure2.hashCode(), measure.hashCode());
    }

}
