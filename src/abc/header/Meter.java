package abc.header;

/**
 * Immutable datatype representing the meter of a piece of music
 */
public class Meter {
    
    //AF: 
    //  using numerator, denominator to represent a meter fraction
    //RI:
    //  numerator >= 1, denominator >= 1
    //rep exposure: 
    //  all fields are private final primitives
    
    private final int numerator, denominator;
    
    /**
     * create a meter
     * @param numerator meter's numerator
     * @param denominator meter's denominator
     */
    public Meter(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        checkRep();
    }
    
    /**
     * create a special meter
     * @param meterString The special meterString "C" (common time) means 4/4, 
     *        and "C|" (cut common time) means 2/2
     */
    public Meter(String meterString) {
        final int commonParameter = 4;
        final int cutCommonParameter = 2;
        if (meterString.equals("C")) {
            numerator = commonParameter;
            denominator = commonParameter;
        }
        else if (meterString.equals("C|")){
            numerator = cutCommonParameter;
            denominator = cutCommonParameter;
        }
        else
            throw new IllegalArgumentException("invalid meter");
        checkRep();
    }
    
    /**
     * check RI
     */
    private void checkRep() {
        assert numerator >= 1;
        assert denominator >= 1;
    }
    
    /**
     * get numerator
     * @return the numerator of the meter
     */
    public int getNumerator() {
        return numerator;
    }
    
    /**
     * get denominator
     * @return the denominator of the meter
     */
    public int getDenominator() {
        return denominator;
    }
    
    /**
     * Get human readable string representation of Meter
     */
    @Override public String toString() {
        return numerator + "/" + denominator;
    }
    
    /**
     * 2 meters are equal if and only if their numerators are equal and  their denominators
     */
    @Override public boolean equals(Object thatObject) {
        if (thatObject instanceof Meter) {
            Meter that = (Meter)thatObject;
            return this.numerator == that.numerator && this.denominator == that.denominator;
        }
        return false;
    }
    
    @Override public int hashCode() {
        return numerator + denominator;
    }
}
