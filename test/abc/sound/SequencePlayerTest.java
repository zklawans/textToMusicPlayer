package abc.sound;

import static org.junit.Assert.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

/**
 * Tests playing music using SequencePlayer.
 * @category no_didit
 */
public class SequencePlayerTest {

    // Warmup:
    //  tests playing piece1 and piece2
    
    private static final int A = new Pitch('A').toMidiNote();
    private static final int HIGH_A = new Pitch('A').transpose(12).toMidiNote();
    private static final int B = new Pitch('B').toMidiNote();
    private static final int B_FLAT = new Pitch('B').transpose(-1).toMidiNote();
    private static final int C = new Pitch('C').toMidiNote();
    private static final int HIGH_C = new Pitch('C').transpose(12).toMidiNote();
    private static final int D = new Pitch('D').toMidiNote();
    private static final int HIGH_D = new Pitch('D').transpose(12).toMidiNote();
    private static final int E = new Pitch('E').toMidiNote();
    private static final int HIGH_E = new Pitch('E').transpose(12).toMidiNote();
    private static final int F = new Pitch('F').toMidiNote();
    private static final int F_SHARP = new Pitch('F').transpose(1).toMidiNote();
    private static final int HIGH_F = new Pitch('F').transpose(12).toMidiNote();
    private static final int G = new Pitch('G').toMidiNote();
    private static final int HIGH_G = new Pitch('G').transpose(12).toMidiNote();
    
    // Tests playing piece1.abc
    @Test
    public void testPlayPiece1() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        SequencePlayer player = new SequencePlayer(140, 12);

        // bar 1
        player.addNote(C, 0, 12);
        player.addNote(C, 12, 12);
        player.addNote(C, 24, 9);
        player.addNote(D, 33, 3);
        player.addNote(E, 36, 12);
        
        // bar 2
        player.addNote(E, 48, 9);
        player.addNote(D, 57, 3);
        player.addNote(E, 60, 9);
        player.addNote(F, 69, 3);
        player.addNote(G, 72, 24);
        
        // bar 3
        player.addNote(HIGH_C, 96, 4);
        player.addNote(HIGH_C, 100, 4);
        player.addNote(HIGH_C, 104, 4);
        player.addNote(G, 108, 4);
        player.addNote(G, 112, 4);
        player.addNote(G, 116, 4);
        player.addNote(E, 120, 4);
        player.addNote(E, 124, 4);
        player.addNote(E, 128, 4);
        player.addNote(C, 132, 4);
        player.addNote(C, 136, 4);
        player.addNote(C, 140, 4);
        
        // bar 4
        player.addNote(G, 144, 9);
        player.addNote(F, 153, 3);
        player.addNote(E, 156, 9);
        player.addNote(D, 165, 3);
        player.addNote(C, 168, 24); //192

        String expected = "Event: NOTE_ON  Pitch: 60  Tick: 0\n"
      + "Event: NOTE_OFF Pitch: 60  Tick: 12\n"
      + "Event: NOTE_ON  Pitch: 60  Tick: 12\n"
      + "Event: NOTE_OFF Pitch: 60  Tick: 24\n"
      + "Event: NOTE_ON  Pitch: 60  Tick: 24\n"
      + "Event: NOTE_OFF Pitch: 60  Tick: 33\n"
      + "Event: NOTE_ON  Pitch: 62  Tick: 33\n"
      + "Event: NOTE_OFF Pitch: 62  Tick: 36\n"
      + "Event: NOTE_ON  Pitch: 64  Tick: 36\n"
      + "Event: NOTE_OFF Pitch: 64  Tick: 48\n"
      // bar 2
      + "Event: NOTE_ON  Pitch: 64  Tick: 48\n"
      + "Event: NOTE_OFF Pitch: 64  Tick: 57\n"
      + "Event: NOTE_ON  Pitch: 62  Tick: 57\n"
      + "Event: NOTE_OFF Pitch: 62  Tick: 60\n"
      + "Event: NOTE_ON  Pitch: 64  Tick: 60\n"
      + "Event: NOTE_OFF Pitch: 64  Tick: 69\n"
      + "Event: NOTE_ON  Pitch: 65  Tick: 69\n"
      + "Event: NOTE_OFF Pitch: 65  Tick: 72\n"
      + "Event: NOTE_ON  Pitch: 67  Tick: 72\n"
      + "Event: NOTE_OFF Pitch: 67  Tick: 96\n"
      // bar 3
      + "Event: NOTE_ON  Pitch: 72  Tick: 96\n"
      + "Event: NOTE_OFF Pitch: 72  Tick: 100\n"
      + "Event: NOTE_ON  Pitch: 72  Tick: 100\n"
      + "Event: NOTE_OFF Pitch: 72  Tick: 104\n"
      + "Event: NOTE_ON  Pitch: 72  Tick: 104\n"
      + "Event: NOTE_OFF Pitch: 72  Tick: 108\n"
      + "Event: NOTE_ON  Pitch: 67  Tick: 108\n"
      + "Event: NOTE_OFF Pitch: 67  Tick: 112\n"
      + "Event: NOTE_ON  Pitch: 67  Tick: 112\n"
      + "Event: NOTE_OFF Pitch: 67  Tick: 116\n"
      + "Event: NOTE_ON  Pitch: 67  Tick: 116\n"
      + "Event: NOTE_OFF Pitch: 67  Tick: 120\n"
      + "Event: NOTE_ON  Pitch: 64  Tick: 120\n"
      + "Event: NOTE_OFF Pitch: 64  Tick: 124\n"
      + "Event: NOTE_ON  Pitch: 64  Tick: 124\n"
      + "Event: NOTE_OFF Pitch: 64  Tick: 128\n"
      + "Event: NOTE_ON  Pitch: 64  Tick: 128\n"
      + "Event: NOTE_OFF Pitch: 64  Tick: 132\n"
      + "Event: NOTE_ON  Pitch: 60  Tick: 132\n"
      + "Event: NOTE_OFF Pitch: 60  Tick: 136\n"
      + "Event: NOTE_ON  Pitch: 60  Tick: 136\n"
      + "Event: NOTE_OFF Pitch: 60  Tick: 140\n"
      + "Event: NOTE_ON  Pitch: 60  Tick: 140\n"
      + "Event: NOTE_OFF Pitch: 60  Tick: 144\n"
      // bar 4
      + "Event: NOTE_ON  Pitch: 67  Tick: 144\n"
      + "Event: NOTE_OFF Pitch: 67  Tick: 153\n"
      + "Event: NOTE_ON  Pitch: 65  Tick: 153\n"
      + "Event: NOTE_OFF Pitch: 65  Tick: 156\n"
      + "Event: NOTE_ON  Pitch: 64  Tick: 156\n"
      + "Event: NOTE_OFF Pitch: 64  Tick: 165\n"
      + "Event: NOTE_ON  Pitch: 62  Tick: 165\n"
      + "Event: NOTE_OFF Pitch: 62  Tick: 168\n"
      + "Event: NOTE_ON  Pitch: 60  Tick: 168\n"
      + "Event: NOTE_OFF Pitch: 60  Tick: 192\n"
      + "Meta event: END_OF_TRACK Tick: 192\n";
        String result = player.toString();
        System.out.println(result);
        assertEquals(expected, result);
        
        player.play();
        Thread.sleep(10000);

    }
    
    // Tests playing piece2.abc
    @Test
    public void testPlayPiece2() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        SequencePlayer player = new SequencePlayer(200, 12);
        
        // bar 1
        player.addNote(F_SHARP, 0, 6);
        player.addNote(HIGH_E, 0, 6);
        player.addNote(F_SHARP, 6, 6);
        player.addNote(HIGH_E, 6, 6);
        // rest from 12 to 18
        player.addNote(F_SHARP, 18, 6);
        player.addNote(HIGH_E, 18, 6);
        // rest from 24 to 30
        player.addNote(F_SHARP, 30, 6);
        player.addNote(HIGH_C, 30, 6);
        player.addNote(F_SHARP, 36, 12);
        player.addNote(HIGH_E, 36, 12);
        
        // bar 2
        player.addNote(G, 48, 12);
        player.addNote(B, 48, 12);
        player.addNote(HIGH_G, 48, 12);
        // rest from 60 to 72
        player.addNote(G, 72, 12);
        // rest from 84 to 96
        
        // bar 3
        player.addNote(HIGH_C, 96, 18);
        player.addNote(G, 114, 6);
        // rest from 120 to 132
        player.addNote(E, 132, 12);
        
        // bar 4
        player.addNote(E, 144, 6);
        player.addNote(A, 150, 12);
        player.addNote(B, 162, 12);
        player.addNote(B_FLAT, 174, 6);
        player.addNote(A, 180, 12);
        
        // bar 5
        player.addNote(G, 192, 8);
        player.addNote(HIGH_E, 200, 8);
        player.addNote(HIGH_G, 208, 8);
        player.addNote(HIGH_A, 216, 12);
        player.addNote(HIGH_F, 228, 6);
        player.addNote(HIGH_G, 234, 6);
        
        // bar 6
        // rest from 240 to 246
        player.addNote(HIGH_E, 246, 12);
        player.addNote(HIGH_C, 258, 6);
        player.addNote(HIGH_D, 264, 6);
        player.addNote(B, 270, 9);
        // rest from 279 to 288
        
        System.out.println(player);
        
        player.play();
        Thread.sleep(10000);
    }

}
