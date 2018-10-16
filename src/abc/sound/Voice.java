package abc.sound;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Voice represents the music that a single voice would play.
 * Voice is an immutable concrete implementation of Music.
 *
 */
public class Voice implements Music {

    private final List<Measure> measures;
    private List<Measure> expandedMeasures;
    
    //AF: 
    //   Using a list most measures to represent a voice
    //RI: 
    //   measures != null
    //   nothing contained in measures is null
    //Rep Exposure: 
    //   measures is private and final, and methods always return defensive copy.
    //   use defensive copying in constructor
    //   measures contains immutable data types
    //   expandedMeasures is private, contains immutable types, and never used as input or output of a method
    
    /**
     * Create a Voice.
     * @param measures measures that the voice will play
     */
    public Voice(List<Measure> measures){
        this.measures = new ArrayList<>(measures);
        this.expandedMeasures = new ArrayList<>();
        checkRep();
    }
    
    /**
     * Create a voice that won't play any measures
     */
    public Voice(){
        this.measures = new ArrayList<>();
        checkRep();
    }
    
    // assert RI
    private void checkRep() {
        assert measures != null;
        for (Measure measure : measures) {
            assert measure != null;
        }
    }
    
    private List<Measure> expand() {
        List<Measure> list = new ArrayList<>();
        boolean repeating = false;
        int startRepeatPosition = 0;
        int endRepeatPosition = 0;
        int pos = 0;
        while (pos < measures.size()) {
            if(measures.get(pos).isStartRepeat() && pos > startRepeatPosition) {
                startRepeatPosition = pos;
            }
            if(measures.get(pos).isEndMajorSection()) {
                startRepeatPosition = pos + 1;
            }
            if(repeating) {
                if(measures.get(pos).isStartFirstEnding()) {
                    pos = endRepeatPosition + 1;
                    repeating = false;
                    continue;
                }
                if(measures.get(pos).isEndRepeat()) {
                    repeating = false;
                    list.add(measures.get(pos));
                    pos++;
                    continue;
                }
            }
            else {
                if(measures.get(pos).isEndRepeat()) {
                    repeating = true;
                    endRepeatPosition = pos;
                    list.add(measures.get(pos));
                    pos = startRepeatPosition;
                    continue;
                }
            }
            list.add(measures.get(pos));
            pos++;
        }
        checkRep();
        return list;
    }
    
    @Override
    public double duration() {
        if (measures.isEmpty())
            return 0;
        if (expandedMeasures.isEmpty()){
            expandedMeasures = expand();
        }
        double duration = 0;
        for (Measure measure: expandedMeasures) {
            duration += measure.duration();
        }
        checkRep();
        return duration;
    }

    @Override
    public void play(SequencePlayer player, double atBeat) {
        if (expandedMeasures.isEmpty()){
            expandedMeasures = expand();
        }
        double location = atBeat;
        for (Measure measure: expandedMeasures) {
            measure.play(player, location);
            location += measure.duration();
        }
    }
    
    /**
     * Get the total of measures that voice will play
     * @return number of measures
     */
    public int getNumMeasures(){
        if (expandedMeasures.isEmpty()){
            expandedMeasures = expand();
        }
        return expandedMeasures.size();
    }
    
    /**
     * Play a particular measure
     * @param player sequence player
     * @param atBeat beat to beginning playing at
     * @param measureIndex index of measure
     * @return duration of measure
     */
    public double playMeasure(SequencePlayer player, double atBeat, int measureIndex){
        if (expandedMeasures.isEmpty()){
            expandedMeasures = expand();
        }
        if (measureIndex < expandedMeasures.size()){
            Measure measure = expandedMeasures.get(measureIndex);
            measure.play(player, atBeat);
            return measure.duration();
        }
        return 0;
    }
    


    @Override
    public Music rescale(double scale) {
        List<Measure> newMeasures = new ArrayList<>();
        for (Measure measure: measures) {
            newMeasures.add(measure.rescale(scale));
        }
        checkRep();
        return new Voice(newMeasures);
    }
    
    /**
     * Add measures for the voice to play
     * @param measures measures to play
     * @return new instance of Voice
     */
    public Voice append(List<Measure> measures){
        List<Measure> newMeasures = new ArrayList<>(this.measures);
        newMeasures.addAll(measures);
        checkRep();
        return new Voice(newMeasures);
    }
    
    /**
     * Add the measures of another voice to play
     * @param newVoice voice with new measures
     * @return new instance of Voice
     */
    public Voice append(Voice newVoice){
        List<Measure> newMeasures = new ArrayList<>(this.measures);
        newMeasures.addAll(newVoice.measures);
        checkRep();
        return new Voice(newMeasures);
    }
    
    @Override public boolean equals(Object thatObj) {
        if (thatObj instanceof Voice) {
            Voice that = (Voice)thatObj;
            return this.measures.equals(that.measures);
        }
        return false;
    }
    
    @Override public int hashCode() {
        int code = 0;
        for (Measure measure: measures) {
            code += measure.hashCode();
        }
        return code;
    }
    
    /**
     * Get human readable string representation of Voice
     */
    @Override public String toString() {
        String string = "";
        for (Measure measure: measures) {
            string += measure.toString();
        }
        return string;
    }

}
