package abc.header;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Tempo
 *
 */
public class TempoTest {
    
    /*
     * Testing strategy for Tempo
     * 
     * Partition for getBeatLength:
     *   result =0, >0
     *   create a Tempo and get its beat length
     * 
     * Partition for getTempo:
     *   create a Tempo and get its tempo
     * 
     * TODO: decide whether to strengthen the spec for toString
     * Partition for toString:
     * 
     * Partition for equals:
     *   obj: not Tempo, unequal Tempo, equal Tempo
     * 
     * Partition for hashCode:
     *   test equal objects have same hashCode
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetBeatLengthZero() {
        Tempo tempo = new Tempo(0,100);
        assertTrue("expected 0", 0.0 == tempo.getBeatLength());
    }
    
    @Test
    public void testGetBeatLengthPositive() {
        Tempo tempo = new Tempo(1,100);
        assertTrue("expected 1", 1.0 == tempo.getBeatLength());
    }
    
    @Test
    public void testGetTempo() {
        Tempo tempo = new Tempo(1,100);
        assertTrue("expected 100", 100 == tempo.getTempo());
    }
    
    @Test
    public void testEqualsNotTempo() {
        Meter meter = new Meter("C|");
        Tempo tempo = new Tempo(1,100);
        assertFalse(tempo.equals(meter));
    }
    
    @Test
    public void testEqualsNotEqual() {
        Tempo tempo1 = new Tempo(1,100);
        Tempo tempo2 = new Tempo(1,102);
        assertFalse(tempo1.equals(tempo2));
    }
    
    @Test
    public void testEqualsEqual() {
        Tempo tempo1 = new Tempo(1,100);
        Tempo tempo2 = new Tempo(1,100);
        assertTrue(tempo1.equals(tempo2));
    }
    
    @Test
    public void testHashCode() {
        Tempo tempo1 = new Tempo(1,100);
        Tempo tempo2 = new Tempo(1,100);
        assertTrue(tempo1.hashCode() == tempo2.hashCode());
    }
}