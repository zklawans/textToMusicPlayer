package abc.sound;

/**
 * Music represents a piece of music.
 */
public interface Music {
    
    // Music = Note(duration: double, pitch: Pitch, instrument: Instrument) + Rest(duration: double) + Chord(notes: Set<Note>) 
    //        + Measure(notes: List<Music>, measureDuration: int, startRepeat: boolean, endRepeat: boolean,
    //                  startFirstEnding: boolean, startSecondEnding: boolean, endMajorSection: boolean)
    //        + Tuplet(notes: Music[]) + Voice(measures: List<Measure>) + Voices(voices: Map<String, Voice>)
    
    /**
     * @return total duration of this piece in beats
     */
    public double duration();
    
    /**
     * Play this piece.
     * @param player player to play on
     * @param atBeat when to play
     */
    public void play(SequencePlayer player, double atBeat);

    /**
     * Scale the duration of this piece of music by factor scale
     * @param scale the non-negative scaling factor
     * @return a piece of music that is identical to this music 
     *         except for a proportional speed, which result in
     *         a duration proportion of scale
     */
    public Music rescale(double scale);
    
    /**
     * 
     * @return a human readable string representation of Music
     */
    @Override 
    public String toString();
    
    /**
     * @param thatObject
     * @return 2 pieces of Music are the same if and only if they share exactly the same music and music notation
     */
    @Override
    public boolean equals(Object thatObject);
    
    @Override
    public int hashCode();
}
