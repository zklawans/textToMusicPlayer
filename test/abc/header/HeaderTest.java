package abc.header;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * Tests Header
 *
 */
public class HeaderTest {
    /*
     * Testing strategy for Header
     * 
     * Partition for getComposer:
     *   create a Header and get its composer
     * 
     * Partition for getKeySignature:
     *   create a Header and get its key signature
     *   
     * Partition for getDefaultLength:
     *   create a Header and get its default length
     *   
     * Partition for getMeter:
     *   create a Header and get its meter
     * 
     * Partition for getTempo:
     *   create a Header and get its tempo
     *   
     * Partition for getTitle:
     *   create a Header and get its title
     *   
     * Partition for getIndexNumber:
     *   create a Header and get its index number
     *   
     * Partition for getVoice:
     *   create a Header and get its voice
     *   
     * Partition for getBeatsPerDefaultNote:
     *   create a Header and get its beats per default note
     *   
     * Partition for getBeatsPerMinute:
     *   create a Header and get its beats per miniute
     * 
     * Partition for toString:
     *   NOT empty
     * 
     * Partition for equals:
     *   obj: not Header, unequal Header, equal Header
     * 
     * Partition for hashCode:
     *   test equal objects have same hashCode
     */
    
    private static Header HEADER = new Header(new ArrayList<String>(Arrays.asList("up","middle","low")), "Bach", 
            new KeySignature("C"), 2.0, new Meter("C"), new Tempo(1.0,100), "Untitled (I)", 1024);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetComposer() {
        assertEquals("Bach", HEADER.getComposer());
    }
    
    @Test
    public void testGetKeySignature() {
        assertEquals(new KeySignature("C"), HEADER.getKeySignature());
    }
    
    @Test
    public void testGetDefaultLength() {
        assertTrue("expected 2.0", 2.0 == HEADER.getDefaultLength());
    }
    
    @Test
    public void testGetMeter() {
        assertEquals(new Meter("C"), HEADER.getMeter());
    }
    
    @Test
    public void testGetTempo() {
        assertEquals(new Tempo(1.0,100), HEADER.getTempo());
    }
    
    @Test
    public void testGetTitle() {
        assertEquals("Untitled (I)", HEADER.getTitle());
    }
    
    @Test
    public void testGetIndexNumber() {
        assertTrue("expected 1024", 1024 == HEADER.getIndexNumber());
    }
    
    @Test
    public void testGetVoice() {
        assertEquals(new ArrayList<String>(Arrays.asList("up","middle","low")), HEADER.getVoice());
    }
    
    @Test
    public void testGetBeatsPerDefaultNote() {
        assertTrue(HEADER.getBeatsPerDefaultNote() == 8);
    }
    
    @Test
    public void testGetBeatsPerMinutes() {
        assertTrue(HEADER.getBeatsPerMinute() == 400);
    }
    
    @Test
    public void testEqualsNotKeyHeader() {
        KeySignature signature = new KeySignature("C",true);
        assertFalse(HEADER.equals(signature));
    }
    
    @Test
    public void testEqualsNotEqual() {
        Header header2 = new Header(new ArrayList<String>(Arrays.asList("up","middle","low")), "Bach", 
                new KeySignature("C"), 2.0, new Meter("C|"), new Tempo(1.0,100), "Untitled (I)", 1024);
        assertFalse(HEADER.equals(header2));
    }
    
    @Test
    public void testEqualsEqual() {
        Header header2 = new Header(new ArrayList<String>(Arrays.asList("up","middle","low")), "Bach", 
                new KeySignature("C"), 2.0, new Meter("C"), new Tempo(1.0,100), "Untitled (I)", 1024);
        assertTrue(HEADER.equals(header2));
    }
    
    @Test
    public void testHashCode() {
        Header header2 = new Header(new ArrayList<String>(Arrays.asList("up","middle","low")), "Bach", 
                new KeySignature("C"), 2.0, new Meter("C"), new Tempo(1.0,100), "Untitled (I)", 1024);
        assertTrue(HEADER.hashCode() == header2.hashCode());
    }
}