package abc.parser;

import abc.sound.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import abc.header.*;

/** 
 *  A piece of Song
 */
public class Song {
    //AF:
    //  using a music and a header to represent a song  
    //  header contains all the information about the song: meter, composer, tempo, title, track number, default beat length, key signature
    //  music contains the notes of the song
    //RI:
    //  music != null
    //  header != null
    //rep exposure:
    //  all fields are private and final and immutable data type
    
    //field
    private final Music music;
    private final Header header;
    
    private void checkRep() {
        assert music != null;
        assert header != null;
    }
    
    /**
     * play the song
     */
    public void play() {
        int beatsPerMinute = (int)header.getBeatsPerMinute();
        final int ticksPerBeat = 64*27*125; // this number is chosen to avoid decimal ticks
        try {
            SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
            music.play(player, 0);
            System.out.println(header);
            player.play();
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * create a song with music and header
     * @param music the music in the song
     * @param header the header for the song
     */
    public Song (Music music, Header header) {
        this.music = music;
        this.header = header;
        checkRep();
    }
    
    /**
     * Get the music.
     * @return the music of the song
     */
    public Music getMusic() {
        return music;
    }
    
    /**
     * Get the header.
     * @return the header of the song
     */
    public Header getHeader() {
        return header;
    }
    
    /**
     * 2 pieces of songs are considered as equal if and only if their headers and music are exactly the same
     */
    @Override 
    public boolean equals(Object thatObject) {
        if (thatObject instanceof Song) {
            Song that = (Song)thatObject;
            return this.getHeader().equals(that.getHeader()) && this.getMusic().equals(that.getMusic());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return music.hashCode() + header.hashCode();
    }
    
    /**
     * Get human readable string representation of Song
     */
    @Override
    public String toString() {
        return header.toString() + "\n\n" + music.toString();
    }
}
