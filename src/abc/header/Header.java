package abc.header;

import java.util.ArrayList;
import java.util.List;

/**
 * Header contains all of the header information of a song
 * Header is an immutable datatype
 */
public class Header {
    
    //AF: title represents the name of the song
    //    keySignature is represented by the integer denotes the Music's semitones up from middle C
    //    defaultLength represents the default length of a note or rest
    //    meter represents the time signature of the song
    //    tempo represents the speed of the song based on defaultLength
    //    composerName represents the name of the composer of the song
    //    indexNumber represents the index number of the song
    //    voice represents the names of all the voices in the song
    //    beatsPerDefaultNote represents how many beats (beat size is based off of meter) are contained in each note of length defaultLength
    //RI: defaultLength > 0
    //    indexNumber >= 1
    //    beatsPerDefaultNote > 0
    //RE: all fields are private, final
    //    all fields are immutable data types or primitives except for voice
    //    we always make defensive copy for voice.
    
    //fields
    private final String composerName;
    private final KeySignature keySignature;
    private final double defaultLength;
    private final Meter meter;
    private final Tempo tempo;
    private final String title;
    private final int indexNumber;
    private final List<String> voice;
    private final double beatsPerDefaultNote;
    
    /**
     * Create a new header
     * @param voice list of names of voices if there are multiple voices
     * @param composerName name of composer
     * @param keySignature semitones up from middle C of key
     * @param defaultLength default length of note or rest
     * @param meter time signature
     * @param tempo speed of song
     * @param title title of song
     * @param indexNumber index number of song
     */
    public Header(List<String> voice, String composerName, KeySignature keySignature, double defaultLength, Meter meter, Tempo tempo, String title, int indexNumber) {
        this.composerName = composerName;
        this.keySignature = keySignature;
        this.defaultLength = defaultLength;
        this.meter = meter;
        this.tempo = tempo;
        this.title = title;
        this.indexNumber = indexNumber;
        this.voice = new ArrayList<>(voice);
        this.beatsPerDefaultNote = defaultLength*meter.getDenominator();
        checkRep();
    }
    
    /**
     * check RI
     */
    private void checkRep() {
        assert (defaultLength > 0);
        assert indexNumber >= 1;
        assert beatsPerDefaultNote >= 0;
        assert keySignature != null;
        assert meter != null;
        assert tempo != null;
        assert voice != null;
    }
    
    /**
     * Get the number of beats for the default note length
     * @return number of beats
     */
    public double getBeatsPerDefaultNote(){
        return this.beatsPerDefaultNote;
    }
    
    /**
     * Get the name of the composer
     * @return composer's name or "Unknown"
     */
    public String getComposer() {
        return composerName;
    }
    
    /**
     * Get the key signature
     * @return key signature
     */
    public KeySignature getKeySignature() {
        return keySignature;
    }
    
    /**
     * Get default length of note
     * @return default length
     */
    public double getDefaultLength() {
        return defaultLength;
    }
    
    /**
     * Get time signature
     * @return time signature
     */
    public Meter getMeter() {
        return meter;
    }
    
    /**
     * Get tempo
     * @return tempo
     */
    public Tempo getTempo() {
        return tempo;
    }
    
    /**
     * Get title of song
     * @return title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Get index number of song
     * @return index number
     */
    public int getIndexNumber() {
        return indexNumber;
    }
    
    /**
     * Get voices
     * @return list of voices
     */
    public List<String> getVoice() {
        return new ArrayList<>(voice);
    }
    
    /**
     * Get beats per minute of song
     * @return beats per minute
     */
    public double getBeatsPerMinute(){
        return this.tempo.getTempo()*this.tempo.getBeatLength()*meter.getDenominator();
    }
    
    /**
     * Get human readable string representation of header
     */
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("X: " + indexNumber);
        sb.append("\nT: " + title);
        sb.append("\nC: " + composerName);
        sb.append("\nQ: " + tempo);
        sb.append("\nM: " + meter);
        sb.append("\nL: " + defaultLength);
        sb.append("\nK: " + keySignature);
        for (String name: voice) {
            sb.append("\nV: " + name);
        }
        return sb.toString();
    }
    
    /**
     * 2 headers are the equal if and only if their components are exactly the same
     */
    @Override public boolean equals(Object thatObject) {
        if (thatObject instanceof Header) {
            Header that = (Header)thatObject;
            return this.composerName.equals(that.composerName) 
                    && this.keySignature.equals(that.keySignature)
                    && this.defaultLength == that.defaultLength
                    && this.meter.equals(that.meter)
                    && this.tempo.equals(that.tempo) 
                    && this.title.equals(that.title)
                    && this.indexNumber == that.indexNumber
                    && this.voice.equals(that.voice);
        }
        return false;
    }
    
    @Override public int hashCode() {
        return keySignature.hashCode() + title.hashCode();
    }
}
