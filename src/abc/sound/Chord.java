package abc.sound;

import java.util.HashSet;
import java.util.Set;

/**
 * A chord represents a group of notes being played at the same time
 * Chord is an immutable concrete implementation of Music
 */
public class Chord implements Music {
    
    private final Set<Note> notes;
    private final double duration;
    
    // Abstraction function:
    //  notes represents all of the notes that are played in the chord
    //  duration represents how long the chord is held
    // RI:
    //  duration >= 0
    // Rep exposure:
    //  notes are private and final and are never return to the user
    //  notes are defensively copied
    //  duration is private, final, and primitive
    
    /**
     * Create a new chord
     * @param notes, notes in the chord, must contain at least 2 note
     */
    public Chord(Set<Note> notes){
        this.notes = new HashSet<>(notes);
        duration = getDuration();
        checkRep();
    }
    
    /**
     * check RI
     */
    private void checkRep(){
        assert this.duration >= 0: "no duration of chord";
    }
    
    @Override
    public double duration() {
        return this.duration;
    }
    
    /**
     * Get the length of the longest note in the chord
     * @return duration of the chord
     */
    private double getDuration(){
        double maxDuration = 0;
        for (Note note: this.notes){
            if (note.duration() > maxDuration){
                maxDuration = note.duration();
            }
        }
        return maxDuration;
    }
    
    /**
     * Get the notes in the chord.
     * @return the set of notes in this Chord
     */
    public Set<Note> getNotes() {
        return new HashSet<>(notes);
    }

    @Override
    public void play(SequencePlayer player, double atBeat) {
        for (Note note: notes){
            note.play(player, atBeat);
        }
    }

    @Override
    public Music rescale(double scale) {
        Set<Note> rescaledNotes = new HashSet<>();
        for (Note note: notes){
            rescaledNotes.add((Note) note.rescale(scale));
        }
        return new Chord(rescaledNotes);
    }

    /**
     * Get human readable string representation of Chord
     */
    @Override
    public String toString() {
        return "[" + notes.toString() + "]";
    }
    
    @Override
    public boolean equals(Object thatObject) {
        if(thatObject instanceof Chord) {
            Chord that = (Chord)thatObject;
            return this.getNotes().equals(that.getNotes());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int code = 0;
        for (Music note : notes) {
            code += note.hashCode();
        }
        return code;
    }
}
