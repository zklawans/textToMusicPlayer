package abc.sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Immutable concrete implementation of Music that represents music with multiple voices
 */
public class Voices implements Music {
    
    //AF: using a map mapping voices name to their music to represent a multiple voices music
    //RI: voices != null
    //    voices.values() contain no null values
    //rep exposure: voices is private, final, and no method returns a reference of voices or elements of voices
    //    map keys and values contain immutable values, use defensive copying in constructor
    
    //field
    private final Map<String, Voice> voices;
    private static final String DEFAULT_VOICE = "Default";
    
    /**
     * Generate multiple voices music
     * @param voices voices and their music
     */
    public Voices(Map<String, Voice> voices){
        this.voices = new HashMap<>(voices);
        checkRep();
    }
    
    /**
     * Generate multiple voices music for a default voice
     * @param music music that default voice plays
     */
    public Voices(Voice music){
        this.voices = new HashMap<>();
        voices.put(DEFAULT_VOICE, music);
        checkRep();
    }
    
    /**
     * Generate empty voices
     */
    public Voices(){
        this.voices = new HashMap<>();
        checkRep();
    }
    
    // assert RI
    private void checkRep() {
        assert voices != null;
        for (String key : voices.keySet()) {
            assert key != null;
        }
        for (Voice value : voices.values()) {
            assert value != null;
        }
    }
    
    /**
     * get the Music of some voice
     * @param voiceName the name of designated voice
     * @return the Music of that voice
     */
    public Music getVoice(String voiceName) {
        return voices.get(voiceName);
    }
    
    /**
     * @return a map mapping from voice name to its associated music
     */
    public Map<String, Music> getVoices() {
        return new HashMap<>(voices);
    }
    
    /**
     * change the Music associated with voice of voiceName to newMusic
     * @param voiceName the name of voice
     * @param newMusic the new piece of music
     * @return a new Voices that the Music associated with voice of voiceName has been changed to newMusic
     */
    public Voices change(String voiceName, Voice newMusic) {
        Map<String, Voice> newMap = new HashMap<>(voices);
        newMap.put(voiceName, newMusic);
        checkRep();
        return new Voices(newMap);
    }
    
    /**
     * produce a new Music with appendedMusic appended to the Music associated with voice of voiceName
     * @param voiceName the name of voice
     * @param appendedMusic the new piece of music
     * @return a new Voices that the Music associated with voice of voiceName has been appended by appendedMusic
     */
    public Voices append(String voiceName, List<Measure> appendedMusic) {
        Map<String, Voice> newMap = new HashMap<>(voices);
        if (voices.containsKey(voiceName))
            newMap.put(voiceName, voices.get(voiceName).append(new ArrayList<>(appendedMusic)));
        else
            newMap.put(voiceName, new Voice(appendedMusic));
        checkRep();
        return new Voices(newMap);
    }
    
    /**
     * produce a new Music with appendedMusic appended to the Music associated with voice of voiceName
     * @param voiceName the name of voice
     * @param appendedMusic the new piece of music
     * @return a new Voices that the Music associated with voice of voiceName has been appended by appendedMusic
     */
    public Voices append(String voiceName, Voice appendedMusic) {
        Map<String, Voice> newMap = new HashMap<>(voices);
        if (voices.containsKey(voiceName))
            newMap.put(voiceName, voices.get(voiceName).append(appendedMusic));
        else
            newMap.put(voiceName, appendedMusic);
        checkRep();
        return new Voices(newMap);
    }
    
    
    @Override
    public double duration() {
        double maxDuration = 0;
        for (Music music: voices.values())
            if (music.duration() > maxDuration)
                maxDuration = music.duration();
        checkRep();
        return maxDuration;
    }

    @Override
    //play one measure at a time
    public void play(SequencePlayer player, double atBeat) {
        int numMeasures = 0;
        for (Voice voice: voices.values()){
            if (voice.getNumMeasures() > numMeasures){
                numMeasures= voice.getNumMeasures();
            }
        }
        double beat = atBeat;
        for (int i = 0; i < numMeasures; i++){
            double maxDuration = 0;
            for (Voice voice : voices.values()){
                double duration = voice.playMeasure(player, beat, i);
                if (duration > maxDuration){
                    maxDuration = duration;
                }
            }
            beat += maxDuration;
        }
    }


    @Override
    public Voices rescale(double scale) {
        Map<String, Voice> newVoices = new HashMap<String, Voice>();
        for (String name: voices.keySet()) {
            newVoices.put(name, (Voice)voices.get(name).rescale(scale));
        }
        checkRep();
        return new Voices(newVoices);
    }

    @Override
    public boolean equals(Object thatObject) {
        if (thatObject instanceof Voices) {
            Voices that = (Voices)thatObject;
            return this.voices.equals(that.getVoices());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int code = 0;
        for (Music music: voices.values()) {
            code += music.hashCode();
        }
        return code;
    }
    
    /**
     * Get human readable string representation of Voices
     */
    @Override
    public String toString() {
        String string = "";
        for (String name: voices.keySet()) {
            string += name + ": " + voices.get(name) + "\n";
        }
        return string;
    }
}