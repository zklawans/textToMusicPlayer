package abc.header;

import static org.junit.Assert.*;

import org.junit.Test;

import abc.sound.Accidental;

/**
 * Tests KeySignature
 *
 */
public class KeySignatureTest {
    
    /*
     * Testing strategy for KeySignature
     * 
     * Partition for getKeyBase:
     *   create a KeySignature and get its key base
     * 
     * Partition for getKeyAccidental:
     *   specified, not specified
     *   create a KeySignature and get its key accidental
     *   
     * Partition for getIsMinor:
     *   specified, not specified
     *   create a KeySignature and get whether it is minor
     * 
     * TODO: decide whether to strengthen the spec for toString
     * Partition for toString:
     * 
     * Partition for equals:
     *   obj: not KeySignature, unequal KeySignature, equal KeySignature
     * 
     * Partition for hashCode:
     *   test equal objects have same hashCode
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetKeyBase() {
        KeySignature signature = new KeySignature("C");
        assertEquals("C", signature.getKeyBase());
    }
    
    @Test
    public void testGetKeyAccidentalSpecified() {
        KeySignature signature = new KeySignature("C","#",false);
        assertEquals(Accidental.SHARP, signature.getKeyAccidental());
    }
    
    @Test
    public void testGetKeyAccidentalNotSpecified() {
        KeySignature signature = new KeySignature("C",false);
        assertEquals(Accidental.NATURAL, signature.getKeyAccidental());
    }
    
    @Test
    public void testGetIsMinorNotSpecified() {
        KeySignature signature = new KeySignature("C","_");
        assertFalse(signature.getIsMinor());
    }
    
    @Test
    public void testGetIsMinorSpecified() {
        KeySignature signature = new KeySignature("C",true);
        assertTrue(signature.getIsMinor());
    }
    
    @Test
    public void testEqualsNotKeySignature() {
        KeySignature signature = new KeySignature("C",true);
        Tempo tempo = new Tempo(1,102);
        assertFalse(signature.equals(tempo));
    }
    
    @Test
    public void testEqualsNotEqual() {
        KeySignature signature1 = new KeySignature("C","_");
        KeySignature signature2 = new KeySignature("C","_",true);
        assertFalse(signature1.equals(signature2));
    }
    
    @Test
    public void testEqualsEqual() {
        KeySignature signature1 = new KeySignature("C","_",true);
        KeySignature signature2 = new KeySignature("C","_",true);
        assertTrue(signature1.equals(signature2));
    }
    
    @Test
    public void testHashCode() {
        KeySignature signature1 = new KeySignature("C","_",true);
        KeySignature signature2 = new KeySignature("C","_",true);
        assertTrue(signature1.hashCode() == signature2.hashCode());
    }
}