package abc.parser;

import static org.junit.Assert.*;

import java.util.Collections;

import abc.header.KeySignature;
import abc.sound.Accidental;

import org.junit.Test;

/**
 * Tests for the KeySignatureMap.
 *
 */
public class KeySignatureMapTest {
    
    /*
     * Testing strategy for KeySignatureMap
     *   
     * Partition for getKey:
     *   key: major, minor, flat, neutral, sharp
     *   
     * Partition for equals:
     *   thatObj: not KeySignatureMap, KeySignatureMap
     *   
     * Partition for hashCode:
     *   KeySignatureMaps are all equal thus share the same hashCode
     *   
     * Partition for toString:
     *   Test the result is not empty
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetKeyMajorNeutral() {
        KeySignatureMap map = new KeySignatureMap();
        assertEquals(Collections.emptyMap(), map.getKey(new KeySignature("C", false)));
    }
    
    @Test
    public void testGetKeyMinorSharp() {
        KeySignatureMap map = new KeySignatureMap();
        assertEquals(Accidental.SHARP, map.getKey(new KeySignature("F","#", true)).get('F'));
        assertEquals(Accidental.SHARP, map.getKey(new KeySignature("F","#", true)).get('G'));
        assertEquals(Accidental.SHARP, map.getKey(new KeySignature("F","#", true)).get('C'));
        assertEquals(3, map.getKey(new KeySignature("F","#", true)).size());
    }
    
    @Test
    public void testGetKeyFlat() {
        KeySignatureMap map = new KeySignatureMap();
        assertEquals(Accidental.FLAT, map.getKey(new KeySignature("A", "b", false)).get('D'));
        assertEquals(Accidental.FLAT, map.getKey(new KeySignature("A", "b", false)).get('B'));
        assertEquals(Accidental.FLAT, map.getKey(new KeySignature("A", "b", false)).get('A'));
        assertEquals(Accidental.FLAT, map.getKey(new KeySignature("A", "b", false)).get('E'));
        assertEquals(4, map.getKey(new KeySignature("A", "b", false)).size());
    }

    @Test
    public void testEqualsEqual() {
        KeySignatureMap map = new KeySignatureMap();
        KeySignatureMap map2 = new KeySignatureMap();
        assertEquals(map,map2);
    }
    
    @Test
    public void testEqualsNotKeySignatureMap() {
        KeySignatureMap map = new KeySignatureMap();
        assertFalse(map.equals("map"));
    }
    
    @Test
    public void testHashCode() {
        KeySignatureMap map = new KeySignatureMap();
        KeySignatureMap map2 = new KeySignatureMap();
        assertEquals(map.hashCode(),map2.hashCode());
    }
    
    @Test
    public void testToString() {
        KeySignatureMap map = new KeySignatureMap();
        assertFalse(map.toString().equals(""));
    }
}