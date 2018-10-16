package abc.sound;

/**
 * Tuplet represents a tuplet of type duplet, triplet, or quadruplet, and no other tuplets.
 * It is an immutable concrete implementation of Music.
 */
public class Tuplet implements Music{
    
    private final Music[] notes;
    private final double dupletLength = 1.5;
    private final double tripletLength = 2.0/3.0;
    private final double quadrupletLength = .75; 
    
    /*
     * Abstraction function:
     *   notes represents the notes and/or chords contained in the tuplet
     *   it represents a duplet if it contains 2 notes and/or chords,
     *   a triplet if it contains 3 notes and/or chords,
     *   and a quadruplet if it contains 4 notes and/or chords
     * Rep invariant:
     *   2 <= notes.length <= 4
     *   can contain notes and chords
     *   notes and chords should have the same length 
     *   cannot contain rests
     * Safety from rep exposure:
     *   notes is private and final and contains immutable types
     *   is never returned by a method
     *   other fields private, final, primitive, and only used for calculations, never returned
     */
    
    /**
     * Create a tuplet.
     * @param notes can contain types Note and Chord and should have the same length, 2 <= notes.length <= 4
     */
    public Tuplet(Music[] notes) {
        assert notes.length >= 2 && notes.length <= 4 : "incorrect tuplet size";
        this.notes = new Music[notes.length];
        final int dupletSize = 2;
        final int tripletSize = 3;
        if (notes.length == dupletSize) {
            for (int i = 0; i < notes.length; i++) {
                this.notes[i] = notes[i].rescale(dupletLength);
            }
        } else if (notes.length == tripletSize) {
            for (int i = 0; i < notes.length; i++) {
                this.notes[i] = notes[i].rescale(tripletLength);
            }
        } else {
            for (int i = 0; i < notes.length; i++) {
                this.notes[i] = notes[i].rescale(quadrupletLength);
            }
        }
        checkRep();
    }
    
    // check the rep invariant
    private void checkRep() {
        assert notes.length >= 2 && notes.length <= 4 : "too few or too many notes";
        double duration = notes[0].duration();
        for (Music note: notes) {
            assert note.duration() == duration;
        }
    }

    @Override
    public double duration(){
        double duration = 0;
        for (Music note : notes) {
            duration += note.duration();
        }
        checkRep();
        return duration;
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat){
        checkRep();
        double location = atBeat;
        for (Music note : notes) {
            note.play(player, location);
            location += note.duration();
        }
    }

    @Override
    public Tuplet rescale(double scale) {
        final int dupletSize = 2;
        final int tripletSize = 3;
        Music[] newNotes = new Music[notes.length];
        if (notes.length == dupletSize) {
            for (int i = 0; i < notes.length; i++) {
                newNotes[i] = notes[i].rescale(1.0/dupletLength).rescale(scale);
            }
        } else if (notes.length == tripletSize) {
            for (int i = 0; i < notes.length; i++) {
                newNotes[i] = notes[i].rescale(1.0/tripletLength).rescale(scale);
            }
        } else {
            for (int i = 0; i < notes.length; i++) {
                newNotes[i] = notes[i].rescale(1.0/quadrupletLength).rescale(scale);
            }
        }
        checkRep();
        return new Tuplet(newNotes);
    }
    
    /**
     * Get human readable string representation of Tuplet
     */
    @Override
    public String toString() {
        String result = "(" + notes.length;
        for (Music note : notes) {
            result += note.toString();
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tuplet)) {
            return false;
        }
        Tuplet that = (Tuplet)obj;
        if (notes.length != that.notes.length) {
            return false;
        }
        for (int i = 0; i < notes.length; i++) {
            if (!notes[i].equals(that.notes[i])) {
                return false;
            }
        }
        return true;
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
