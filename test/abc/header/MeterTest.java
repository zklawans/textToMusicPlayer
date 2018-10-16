package abc.header;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Meter
 *
 */
public class MeterTest {
    
    /*
     * Testing strategy for Meter
     * 
     * Partition for getNumerator:
     *   created numerically, with "C", with "C|"
     *   create a meter and get its numerator
     * 
     * Partition for getDenomenator:
     *   created numerically, with "C", with "C|"
     *   create a meter and get its denomenator
     * 
     * TODO: decide whether to strengthen the spec for toString
     * Partition for toString:
     * 
     * Partition for equals:
     *   obj: not Meter, unequal Meter, equal Meter
     * 
     * Partition for hashCode:
     *   test equal objects have same hashCode
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetNumeratorNum() {
        Meter meter = new Meter(3,8);
        assertEquals(3, meter.getNumerator());
    }
    
    @Test
    public void testGetNumeratorC() {
        Meter meter = new Meter("C");
        assertEquals(4, meter.getNumerator());
    }
    
    @Test
    public void testGetNumeratorCut() {
        Meter meter = new Meter("C|");
        assertEquals(2, meter.getNumerator());
    }
    
    @Test
    public void testGetDenomenatorNum() {
        Meter meter = new Meter(3,8);
        assertEquals(8, meter.getDenominator());
    }
    
    @Test
    public void testGetDenomenatorC() {
        Meter meter = new Meter("C");
        assertEquals(4, meter.getDenominator());
    }
    
    @Test
    public void testGetDenomenatorCut() {
        Meter meter = new Meter("C|");
        assertEquals(2, meter.getDenominator());
    }
        
    @Test
    public void testEqualsNotMeter() {
        Meter meter = new Meter("C|");
        String thatObject = "C|";
        assertFalse(meter.equals(thatObject));
    }
    
    @Test
    public void testEqualsNotEqual() {
        Meter meter1 = new Meter("C|");
        Meter meter2 = new Meter("C");
        assertFalse(meter1.equals(meter2));
    }
    
    @Test
    public void testEqualsEqual() {
        Meter meter1 = new Meter("C");
        Meter meter2 = new Meter("C");
        assertTrue(meter1.equals(meter2));
    }
    
    @Test
    public void testHashCode() {
        Meter meter1 = new Meter("C");
        Meter meter2 = new Meter("C");
        assertTrue(meter1.hashCode() == meter2.hashCode());
    }
}