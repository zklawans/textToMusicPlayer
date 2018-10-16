package abc.header;

import java.util.Arrays;
import java.util.HashSet;

import abc.sound.Accidental;

/**
 * Immutable datatype representing a key signature of music
 */
public class KeySignature {
    
    //AF: 
    //  using key base, key accidental and isMinor to represent a key signature
    //RI:
    //  keyBase should be A-G or a-g
    //rep exposure: 
    //  all fields are private final primitives or immutable data types
    
    private final String keyBase;
    private final Accidental keyAccidental;
    private final boolean isMinor;
    private static final String SHARP = "#";
    private static final String FLAT = "b";
    
    /**
     * check RI
     */
    private void checkRep() {
        assert new HashSet<String>(Arrays.asList("A","B","C","D","E","F","G","a","b","c","d","e","f","g")).contains(keyBase);
    }
    
    /**
     * create a keySignature with key base, key accidental and isMinor
     * @param keyBase key base, should be A-G or a-g
     * @param keyAccidental key accidental, should be "^" | "^^" | "_" | "__" | "=" | ""
     * @param isMinor true if is minor, false if not
     */
    public KeySignature (String keyBase, String keyAccidental, boolean isMinor) {
        this.keyBase = keyBase;
        if (keyAccidental.equals(SHARP)){
            this.keyAccidental = Accidental.SHARP;
        }
        else if (keyAccidental.equals(FLAT)){
            this.keyAccidental = Accidental.FLAT;
        }
        else{
            this.keyAccidental = Accidental.NATURAL;
        }
        this.isMinor = isMinor;
        checkRep();
    }
    
    /**
     * create a keySignature with key base and isMinor without key accidental
     * @param keyBase key base, should be A-G or a-g
     * @param isMinor true if is minor, flase if not
     */
    public KeySignature (String keyBase, boolean isMinor) {
        this.keyBase = keyBase;
        this.keyAccidental = Accidental.NATURAL;
        this.isMinor = isMinor;
        checkRep();
    }
    
    /**
     * create a non-minor keySignature with key base, key accidental
     * @param keyBase key base, should be A-G or a-g
     * @param keyAccidental key accidental, should be "^" | "^^" | "_" | "__" | "=" | ""
     */
    public KeySignature (String keyBase, String keyAccidental) {
        this.keyBase = keyBase;
        if (keyAccidental.equals(SHARP))
            this.keyAccidental = Accidental.SHARP;
        else if (keyAccidental.equals(FLAT))
            this.keyAccidental = Accidental.FLAT;
        else
            this.keyAccidental = Accidental.NATURAL;
        this.isMinor = false;
        checkRep();
    }
    
    /**
     * create a non-minor keySignature with key base without accidental
     * @param keyBase key base, should be A-G or a-g
     */
    public KeySignature (String keyBase) {
        this.keyBase = keyBase;
        this.keyAccidental = Accidental.NATURAL;
        this.isMinor = false;
        checkRep();
    }
    
    /**
     * @return key base
     */
    public String getKeyBase() {
        return keyBase;
    }
    
    /**
     * 
     * @return key accidental
     */
    public Accidental getKeyAccidental() {
        return keyAccidental;
    }
    
    /**
     * 
     * @return if it is minor
     */
    public boolean getIsMinor() {
        return isMinor;
    }
    
    /**
     * Get human readable string representation of KeySignature
     */
    @Override public String toString() {
        String accidentalSymbol = "";
        if (keyAccidental.equals(FLAT))
            accidentalSymbol = "b";
        else if (keyAccidental.equals(SHARP))
            accidentalSymbol = "#";
        String result = keyBase + accidentalSymbol;
        if (isMinor)
            result = result + "m";
        return result;
    }
    
    /**
     * 2 key signatures are equal if and only if they are exactly the same
     */
    @Override public boolean equals(Object thatObject) {
        if (thatObject instanceof KeySignature) {
            KeySignature that = (KeySignature)thatObject;
            return this.keyBase.equals(that.keyBase) && this.keyAccidental.equals(that.keyAccidental) && this.isMinor == that.isMinor;
        }
        return false;
    }
    
    @Override public int hashCode() {
        return keyBase.hashCode();
    }
}
