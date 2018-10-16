package abc.sound;

/**
 * An immutable concrete implementation of Music representing a note played by an instrument. 
 */
public class Note implements Music {
    
    
    //AF:
    //  using duration, pitch and instrument to represent 
    //  a note with a pitch played by an instrument that lasts for a duration
    //RI:
    //  duration is nonegative
    //  pitch != null
    //  instrument != null
    //rep exposure:
    //  duration, pitch and instrument are all private final and either immutable data type or primitive
    
    private final double duration;
    private final Pitch pitch;
    private final Instrument instrument;
    private static final Instrument DEFAULT_INSTRUMENT = Instrument.GOBLINS;
    
    /**
     * check RI
     */
    private void checkRep() {
        assert duration >= 0;
        assert pitch != null;
        assert instrument != null;
    }
    
    /**
     * Make a Note played by instrument for duration beats.
     * @param duration duration in beats, must be >= 0
     * @param pitch pitch to play
     * @param instrument instrument to use
     */
    public Note(double duration, Pitch pitch, Instrument instrument) {
        this.duration = duration;
        this.pitch = pitch;
        this.instrument = instrument;
        checkRep();
    }
    
    /**
     * Make a Note played by instrument for duration beats.
     * @param duration duration in beats, must be >= 0
     * @param pitch pitch to play
     * @param instrument instrument to use
     */
    public Note(double duration, Pitch pitch) {
        this.duration = duration;
        this.pitch = pitch;
        this.instrument = DEFAULT_INSTRUMENT;
        checkRep();
    }
    
    /**
     * @return pitch of this note
     */
    public Pitch pitch() {
        return pitch;
    }

    /**
     * @return instrument that should play this note
     */
    public Instrument instrument() {
        return instrument;
    }
    
    /**
     * @return duration of this note
     */
    public double duration() {
        return duration;
    }
    
    /**
     * Transpose note upward or downward in pitch.
     * @param semitonesUp semitones by which to transpose
     * @return transposed note
     */
    public Note transpose(int semitonesUp) {
        return new Note(duration, pitch.transpose(semitonesUp), instrument);
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        player.addNote(pitch.toMidiNote(), atBeat, duration);
    }

    @Override
    public int hashCode() {
        long durationBits = Double.doubleToLongBits(duration);
        return (int) (durationBits ^ (durationBits >>> 32))
                + instrument.hashCode()
                + pitch.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        final Note other = (Note) obj;
        return duration == other.duration
                && instrument.equals(other.instrument)
                && pitch.equals(other.pitch);
    }

    /**
     * Get human readable string representation of Note
     */
    @Override
    public String toString() {
        return pitch.toString() + duration;
    }

    @Override
    public Note rescale(double scale) {
        return new Note(scale * duration, pitch, instrument);
    }
}
