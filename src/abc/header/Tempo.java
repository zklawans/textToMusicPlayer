package abc.header;

/**
 * Immutable datatype representing the tempo of a piece of music
 */
public class Tempo {
    
    //AF: 
    //  using beatLength and tempo to represent a Tempo
    //  tempo is the number of beats of length beatLength to play per minute
    //RI:
    //  beatLength >= 0, tempo > 0
    //rep exposure: 
    //  all fields are private final primitives
    
    private final double beatLength;
    private final int tempo;
    
    /**
     * create a tempo with beatLength and tempo
     * @param beatLength the beat length of each default note, must be greater than 0
     * @param tempo the number of beats to play per minute
     */
    public Tempo(double beatLength, int tempo) {
        this.beatLength = beatLength;
        this.tempo = tempo;
        checkRep();
    }
    
    /**
     * check RI
     */
    private void checkRep() {
        assert beatLength >= 0;
        assert tempo > 0;
    }
    
    /**
     * 
     * @return beat length
     */
    public double getBeatLength() {
        return beatLength;
    }
    
    /**
     * 
     * @return tempo number
     */
    public int getTempo() {
        return tempo;
    }
    
    
    /**
     * Get human readable string representation of Tempo
     */
    @Override public String toString() {
        return beatLength + "=" + tempo;
    }
    
    /**
     * 2 Tempos are equal if and only if their beat lengths and tempos are equal
     */
    @Override public boolean equals(Object thatObject) {
        if (thatObject instanceof Tempo) {
            Tempo that = (Tempo)thatObject;
            return this.beatLength == that.beatLength && this.tempo == that.tempo;
        }
        return false;
    }
    
    @Override public int hashCode() {
        return tempo;
    }
}
