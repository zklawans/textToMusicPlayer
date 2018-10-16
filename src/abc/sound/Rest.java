package abc.sound;

/**
 * Rest is an immutable concrete implementation of Music that represents a pause in a piece of music.
 */
public class Rest implements Music {
    
    //AF:
    //  represents a rest lasting for a length of duration
    //  use a rest of length 0 to represent empty music
    //RI:
    //  duration is non-negative
    //rep exposure:
    //  duration is private, final, and primitive
    
    private final double duration;
    
    /**
     * check RI
     */
    private void checkRep() {
        assert duration >= 0;
    }
    
    /**
     * Make a Rest that lasts for duration beats.
     * @param duration duration in beats, must be >= 0
     */
    public Rest(double duration) { 
        this.duration = duration; 
        checkRep();
    }
    
    @Override
    public double duration() { 
        return duration; 
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        return;
    }
    
    @Override
    public int hashCode() {
        long durationBits = Double.doubleToLongBits(duration);
        return (int) (durationBits ^ (durationBits >>> 32));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Rest other = (Rest) obj;
        return duration == other.duration;
    }
    
    /**
     * Get human readable string representation of Rest
     */
    @Override
    public String toString() {
        return "." + duration;
    }

    @Override
    public Rest rescale(double scale) {
        return new Rest(duration * scale);
    }
}
