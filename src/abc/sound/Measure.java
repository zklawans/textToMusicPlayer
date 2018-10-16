package abc.sound;

import java.util.ArrayList;
import java.util.List;

/**
 * Measure represents a measure of music played by an instrument.
 * An immutable concrete implementation of Music.
 */
public class Measure implements Music {
    
    private final List<Music> notes = new ArrayList<>();
    private final int measureDuration;
    private final boolean startRepeat;
    private final boolean endRepeat;
    private final boolean startFirstEnding;
    private final boolean startSecondEnding;
    private final boolean endMajorSection;
    
    /* 
     * AF:
     *   represents one measure of music using:
     *      notes notes a list of Music types which are the music in this measure
     *      measureDuration the specified duration of this measure
     *      startRepeat whether this measure is a start of a repeat
     *      endRepeat whether this measure is an end of a repeat
     *      startFirstEnding whether this measure is a start of a first ending
     *      startSecondEnding whether this measure is a start of a second ending
     *      endMajorSection whether measure is the end of major section
     *   
     * RI:
     *   none of the objects in notes are null
     *   measureDuration >= 0
     *   
     * Safety from rep exposure:
     *   notes is private and final
     *   all other values are private and primitive
     *   in constructor copied notes input list
     *   notes never returned to client
     */
    
    /**
     * 
     * @return if the measure is the start of repeat
     */
    public boolean isStartRepeat() {
        return startRepeat;
    }
    
    /**
     * 
     * @return if the measure is the end of repeat
     */
    public boolean isEndRepeat() {
        return endRepeat;
    }
    
    /**
     * 
     * @return if the measure is the start of first ending
     */
    public boolean isStartFirstEnding() {
        return startFirstEnding;
    }
    
    /**
     * 
     * @return if the measure is the start of second ending
     */
    public boolean isStartSecondEnding() {
        return startSecondEnding;
    }
    
    /**
     * 
     * @return if the measure is the end of major section
     */
    public boolean isEndMajorSection() {
        return endMajorSection;
    }
    
    
    /**
     * create a measure
     * @param notes notes a list of Music types which are the notes in this measure
     * @param measureDuration the specified duration of an ordinary measure
     * @param startRepeat whether this measure is a start of a repeat
     * @param endRepeat whether this measure is an end of a repeat
     * @param startFirstEnding whether this measure is a start of a first ending
     * @param startSecondEnding whether this measure is a start of a second ending
     * @param endMajorSection whether measure is the end of major section
     */
    public Measure(List<Music> notes, int measureDuration, boolean startRepeat, boolean endRepeat, boolean startFirstEnding, boolean startSecondEnding, boolean endMajorSection) {
        this.measureDuration = measureDuration;
        for (Music note : notes) {
            this.notes.add(note);
        }
        this.startRepeat = startRepeat;
        this.endRepeat = endRepeat;
        this.startFirstEnding = startFirstEnding;
        this.startSecondEnding = startSecondEnding;
        this.endMajorSection = endMajorSection;
        checkRep();
    }
    
    // check rep invariant
    private void checkRep() {
        for (Music note : notes) {
            assert note != null : "a note is null";
        }
        assert measureDuration >= 0;
    }
    
    @Override
    public double duration() {
        double duration = 0;
        for (Music note : notes) {
            duration += note.duration();
        }
        return duration;
    }
    
    @Override
    public void play(SequencePlayer player, double atBeat) {
        double location = atBeat;
        for (Music note : notes) {
            note.play(player, location);
            location += note.duration();
        }
    }

    @Override
    public Measure rescale(double scale) {
        List<Music> newNotes = new ArrayList<>();
        for (Music note : notes) {
            newNotes.add(note.rescale(scale));
        }
        checkRep();
        return new Measure(newNotes, (int)(measureDuration*scale) , startRepeat, endRepeat, startFirstEnding, startSecondEnding, endMajorSection);
    }
    
    /**
     * Get human readable string representation of Measure
     */
    @Override 
    public String toString() {
        String result = "| ";
        for (Music note : notes) {
            result += note.toString();
        }
        result += " |";
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Measure)) {
            return false;
        }
        Measure that = (Measure)obj;
        if (notes.size() != that.notes.size()) {
            return false;
        }
        for (int i = 0; i < notes.size(); i++) {
            if (!notes.get(i).equals(that.notes.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = 0;
        for (Music note : notes) {
            result += note.hashCode();
        }
        return result;
    }

}
