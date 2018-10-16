package abc.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import abc.header.*;
import abc.sound.*;
import lib6005.parser.UnableToParseException;

/**
 * Tests for the Song.
 *
 */
public class SongTest {
    
    /*
     * Testing strategy for Song
     *   
     * getMusic: create a song and getMusic
     * 
     * getHeader: create a song and getHeader
     * 
     * Partition for equals:
     *   obj: not Song, unequal Song, equal Song
     *   
     * Partition for hashCode:
     *   test equal objects have same hashCode
     *   
     * Partition for play:
     *   contains header fields C, L, M, Q, doesn't contain those fields, key signature minor, major, natural, sharp, flat
     *   body contains notes, octave changes, sharps, flats, naturals, duration changes, bars,
     *   repeat bars, multiple ending labels, rests, chords, tuplets, multiple voices
     */
    
    private final static Note A = new Note(1, new Pitch('A'));
    private final static Note B = new Note(1, new Pitch('B'));
    private final static Note C = new Note(1, new Pitch('C'));
    private final static Note HIGH_C = new Note(1, new Pitch('C')).transpose(12);
    private final static Note G = new Note(1, new Pitch('G'));
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetMusic() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C", "", false), 1.0/8.0, new Meter(4, 4), new Tempo(1.0/8.0, 100), "Test Piece 1", 1);
        List<Music> notes = Arrays.asList(G, A, B, HIGH_C);
        
        Measure measure = new Measure(notes, 4, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure);
        Voice voice = new Voice(notes2);
        Voices music = new Voices(voice);
        Song song = new Song(music, header);
        
        assertEquals(music, song.getMusic());
    }
    
    @Test
    public void testGetHeader() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C", "", false), 1.0/8.0, new Meter(4, 4), new Tempo(1.0/8.0, 100), "Test Piece 1", 1);
        List<Music> notes = Arrays.asList(G, A, B, HIGH_C);
        
        Measure measure = new Measure(notes, 4, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure);
        Voice voice = new Voice(notes2);
        Voices music = new Voices(voice);
        Song song = new Song(music, header);
        
        assertEquals(header, song.getHeader());
    }
    
    @Test
    public void testEqualsNotSong() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C", "", false), 1.0/8.0, new Meter(4, 4), new Tempo(1.0/8.0, 100), "Test Piece 1", 1);
        List<Music> notes = Arrays.asList(G, A, B, HIGH_C);
        
        Measure measure = new Measure(notes, 4, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure);
        Voice voice = new Voice(notes2);
        Voices music = new Voices(voice);
        Song song = new Song(music, header);
        
        assertFalse("expected not equals", song.equals(music));
    }
    
    @Test
    public void testEqualsNotEquals() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C", "", false), 1.0/8.0, new Meter(4, 4), new Tempo(1.0/8.0, 100), "Test Piece 1", 1);
        List<Music> notes = Arrays.asList(G, A, B, HIGH_C);
        
        Measure measure = new Measure(notes, 4, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure);
        Voice voice = new Voice(notes2);
        Voices music = new Voices(voice);
        Song song = new Song(music, header);
        
        Header header2 = new Header(new ArrayList<>(), "The Goblin Composer", new KeySignature("C", "b", false), 1.0/8.0, new Meter(2, 2), new Tempo(1.0/8.0, 100), "Test Piece 2", 2);
        Note cFlat = C.transpose(-1);
        Note highCFlat = HIGH_C.transpose(-1);
        List<Music> bar1 = Arrays.asList(cFlat, cFlat, highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), highCFlat.rescale(0.5), new Rest(4));
        List<Music> bar2 = Arrays.asList(cFlat, cFlat, highCFlat, highCFlat, cFlat, highCFlat, new Rest(2));
        Measure measure1 = new Measure(bar1, 8, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 8, false, false, false, false, false);
        List<Measure> notes3 = Arrays.asList(measure1, measure2);
        Voice voice2 = new Voice(notes3);
        Voices music2 = new Voices(voice2);
        Song song2 = new Song(music2, header2);
        
        assertFalse("expected not equals", song.equals(song2));
    }
    
    @Test
    public void testEquals() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C", "", false), 1.0/8.0, new Meter(4, 4), new Tempo(1.0/8.0, 100), "Test Piece 1", 1);
        List<Music> notes = Arrays.asList(G, A, B, HIGH_C);
        
        Measure measure = new Measure(notes, 4, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure);
        Voice voice = new Voice(notes2);
        Voices music = new Voices(voice);
        Song song = new Song(music, header);
        Song song2 = new Song(music, header);
        
        assertTrue("expected equals", song.equals(song2));
    }
    
    @Test
    public void testHashCode() throws UnableToParseException, IOException {
        Header header = new Header(new ArrayList<>(), "Unknown", new KeySignature("C", "", false), 1.0/8.0, new Meter(4, 4), new Tempo(1.0/8.0, 100), "Test Piece 1", 1);
        List<Music> notes = Arrays.asList(G, A, B, HIGH_C);
        
        Measure measure = new Measure(notes, 4, false, false, false, false, false);
        List<Measure> notes2 = Arrays.asList(measure);
        Voice voice = new Voice(notes2);
        Voices music = new Voices(voice);
        Song song = new Song(music, header);
        Song song2 = new Song(music, header);
        
        assertTrue("expected equals", song.hashCode() == song2.hashCode());
    }
    

}