package abc.parser;

import java.util.HashMap;
import java.util.Map;

import abc.header.KeySignature;
import abc.sound.Accidental;

/**
 * 
 * Wrapped key signature map mapping key signature to a map from character to accidental
 */
public class KeySignatureMap {
    private final Map<KeySignature, Map<Character, Accidental>> keys = new HashMap<>();
    private final static KeySignature cMajor = new KeySignature("C", false);
    private final static Map<Character, Accidental> cMajorMap = new HashMap<>();
    private final static KeySignature gMajor = new KeySignature("G", false);
    private final static KeySignature dMajor = new KeySignature("D", false);
    private final static KeySignature aMajor = new KeySignature("A", false);
    private final static KeySignature eMajor = new KeySignature("E", false);
    private final static KeySignature bMajor = new KeySignature("B", false);
    private final static KeySignature fSharpMajor = new KeySignature("F","#", false);
    private final static KeySignature cSharpMajor = new KeySignature("C","#", false);
    private final static KeySignature fMajor = new KeySignature("F", false);
    private final static KeySignature bFlatMajor = new KeySignature("B", "b", false);
    private final static KeySignature eFlatMajor = new KeySignature("E", "b", false);
    private final static KeySignature aFlatMajor = new KeySignature("A", "b", false);
    private final static KeySignature dFlatMajor = new KeySignature("D", "b", false);
    private final static KeySignature gFlatMajor = new KeySignature("G", "b", false);
    private final static KeySignature cFlatMajor = new KeySignature("C", "b", false);
    
    private final static KeySignature cMinor = new KeySignature("C", true);
    private final static KeySignature gMinor = new KeySignature("G", true);
    private final static KeySignature dMinor = new KeySignature("D", true);
    private final static KeySignature aMinor = new KeySignature("A", true);
    private final static KeySignature eMinor = new KeySignature("E", true);
    private final static KeySignature bMinor = new KeySignature("B", true);
    private final static KeySignature fSharpMinor = new KeySignature("F","#", true);
    private final static KeySignature cSharpMinor = new KeySignature("C","#", true);
    private final static KeySignature fMinor = new KeySignature("F", true);
    private final static KeySignature bFlatMinor = new KeySignature("B", "b", true);
    private final static KeySignature eFlatMinor = new KeySignature("E", "b", true);
    private final static KeySignature aFlatMinor = new KeySignature("A", "b", true);
    private final static KeySignature gSharpMinor = new KeySignature("G","#", true);
    private final static KeySignature dSharpMinor = new KeySignature("D","#", true);
    private final static KeySignature aSharpMinor = new KeySignature("A","#", true);
    
    public KeySignatureMap(){
        keys.put(cMajor, cMajorMap);
        Map<Character, Accidental> gMajorMap = new HashMap<>();
        gMajorMap.put('F', Accidental.SHARP);
        keys.put(gMajor, gMajorMap);
        Map<Character, Accidental> dMajorMap = new HashMap<>(gMajorMap);
        dMajorMap.put('C', Accidental.SHARP);
        keys.put(dMajor, dMajorMap);
        Map<Character, Accidental> aMajorMap = new HashMap<>(dMajorMap);
        aMajorMap.put('G', Accidental.SHARP);
        keys.put(aMajor, aMajorMap);
        Map<Character, Accidental> eMajorMap = new HashMap<>(aMajorMap);
        eMajorMap.put('D', Accidental.SHARP);
        keys.put(eMajor, eMajorMap);
        Map<Character, Accidental> bMajorMap = new HashMap<>(eMajorMap);
        bMajorMap.put('A', Accidental.SHARP);
        keys.put(bMajor, bMajorMap);
        Map<Character, Accidental> fSharpMajorMap = new HashMap<>(bMajorMap);
        fSharpMajorMap.put('E', Accidental.SHARP);
        keys.put(fSharpMajor, fSharpMajorMap);
        Map<Character, Accidental> cSharpMajorMap = new HashMap<>(fSharpMajorMap);
        cSharpMajorMap.put('B', Accidental.SHARP);
        keys.put(cSharpMajor, cSharpMajorMap);
        
        Map<Character, Accidental> fMajorMap = new HashMap<>();
        fMajorMap.put('B', Accidental.FLAT);
        keys.put(fMajor, fMajorMap);
        Map<Character, Accidental> bFlatMajorMap = new HashMap<>(fMajorMap);
        bFlatMajorMap.put('E', Accidental.FLAT);
        keys.put(bFlatMajor, bFlatMajorMap);
        Map<Character, Accidental> eFlatMajorMap = new HashMap<>(bFlatMajorMap);
        eFlatMajorMap.put('A', Accidental.FLAT);
        keys.put(eFlatMajor, eFlatMajorMap);
        Map<Character, Accidental> aFlatMajorMap = new HashMap<>(eFlatMajorMap);
        aFlatMajorMap.put('D', Accidental.FLAT);
        keys.put(aFlatMajor, aFlatMajorMap);
        Map<Character, Accidental> dFlatMajorMap = new HashMap<>(aFlatMajorMap);
        dFlatMajorMap.put('G', Accidental.FLAT);
        keys.put(dFlatMajor, dFlatMajorMap);
        Map<Character, Accidental> gFlatMajorMap = new HashMap<>(dFlatMajorMap);
        gFlatMajorMap.put('C', Accidental.FLAT);
        keys.put(gFlatMajor, gFlatMajorMap);
        Map<Character, Accidental> cFlatMajorMap = new HashMap<>(gFlatMajorMap);
        cFlatMajorMap.put('F', Accidental.FLAT);
        keys.put(cFlatMajor, cFlatMajorMap);
        
        keys.put(aMinor, cMajorMap);
        keys.put(eMinor, gMajorMap);
        keys.put(bMinor, dMajorMap);
        keys.put(fSharpMinor, aMajorMap);
        keys.put(cSharpMinor, eMajorMap);
        keys.put(gSharpMinor, bMajorMap);
        keys.put(dSharpMinor, fSharpMajorMap);
        keys.put(aSharpMinor, cSharpMajorMap);
        keys.put(dMinor, fMajorMap);
        keys.put(gMinor, bFlatMajorMap);
        keys.put(cMinor, eFlatMajorMap);
        keys.put(fMinor, aFlatMajorMap);
        keys.put(bFlatMinor, dFlatMajorMap);
        keys.put(eFlatMinor, gFlatMajorMap);
        keys.put(aFlatMinor, cFlatMajorMap);
    }
    
    /**
     * Get the accidentals of a key signature
     * @param key key signature
     * @return a map from character to accidental
     */
    public Map<Character, Accidental> getKey(KeySignature key){
        return new HashMap<>(keys.get(key));
    }
    
    /**
     * KeySignatureMaps are always equal (since they are basically the same map)
     */
    @Override
    public boolean equals(Object thatObj) {
        if (thatObj instanceof KeySignatureMap)
            return true;
        else
            return false;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public String toString() {
        return keys.toString();
    }
}
