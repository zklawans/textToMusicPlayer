package abc.sound;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Note concrete implementation of Music.
 */
public class NoteTest {

    /*
     * Testing strategy for Note
     * 
     * Partition for Note(duration, Pitch, Instrument):
     *   duration: 0, >0
     *   Pitch: valid pitch
     *   Instrument: default instrument, not default instrument
     * 
     * Partition for Note(duration, Pitch):
     *   duration: 0, >0
     *   Pitch: valid pitch
     * 
     * Partition for pitch:
     *   output: same pitch as given in constructor
     * 
     * Partition for duration:
     *   output: integer, decimal, 0, >0
     * 
     * Partition for instrument:
     *   output: same instrument as given in constructor, default instrument
     * 
     * Partition for transpose:
     *   semitonesUp: <-12, -12, -12<semitonesUp<0, 0, 0<semitonesUp<12, 12, >12
     *   output: Pitch transposed greater than octave down, octave down, less than octave down,
     *           same as before, less than octave up, octave up, greater than octave up
     * 
     * Partition for play:
     *   note durations integer, decimal
     *   atBeat: 0, >0, integer, decimal
     *   output: compare player.toString()
     *   IN SEPERATE FILE
     * 
     * Partition for rescale:
     *   scale: 0, 0<scale<1, 1, >1
     *   output: instrument and pitch are equal
     *           duration is 0, less than previous, equal to previous, greater than previous
     * 
     * Partition for equals:
     *   obj: not Note, unequal Note, equal Note
     *   output: true, false
     * 
     * Partition for hashCode:
     *   compare hashCodes for two equal Note,
     * 
     * Partition for toString:
     *   FOR NOW test toString isn't empty
     */
    
    private static final Instrument DEFAULT_INSTR = Instrument.GOBLINS;
    private static final Pitch DEFAULT_PITCH = new Pitch('C');
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Testing Note(duration, Pitch, Instrument)
    // Tests duration = 0, valid Pitch, default instrument
    @Test
    public void testNoteConstructor1Zero() {
        Double expectedDuration = new Double(0.0);
        Pitch expectedPitch = DEFAULT_PITCH;
        Instrument expectedInstrument = DEFAULT_INSTR;
        Note note = new Note(0, DEFAULT_PITCH, DEFAULT_INSTR);
        Double resultDuration = note.duration();
        assertEquals(expectedDuration, resultDuration);
        assertEquals(expectedPitch, note.pitch());
        assertEquals(expectedInstrument, note.instrument());
    }
    
    // Tests duration > 0, valid Pitch, not default instrument
    @Test
    public void testNoteConstructor1Positive() {
        Double expectedDuration = new Double(1.0);
        Pitch expectedPitch = DEFAULT_PITCH;
        Instrument expectedInstrument = Instrument.BAG_PIPE;
        Note note = new Note(1, DEFAULT_PITCH, Instrument.BAG_PIPE);
        Double resultDuration = note.duration();
        assertEquals(expectedDuration, resultDuration);
        assertEquals(expectedPitch, note.pitch());
        assertEquals(expectedInstrument, note.instrument());
    }
    
    // Testing Note(duration, Pitch)
    // Tests duration = 0, valid Pitch
    @Test
    public void testNoteConstructor2Zero() {
        Double expectedDuration = new Double(0.0);
        Pitch expectedPitch = DEFAULT_PITCH;
        Instrument expectedInstrument = DEFAULT_INSTR;
        Note note = new Note(0, DEFAULT_PITCH);
        Double resultDuration = note.duration();
        assertEquals(expectedDuration, resultDuration);
        assertEquals(expectedPitch, note.pitch());
        assertEquals(expectedInstrument, note.instrument());
    }
    
    // Tests duration > 0, valid Pitch
    @Test
    public void testNoteConstructor2Positive() {
        Double expectedDuration = new Double(1.5);
        Pitch expectedPitch = DEFAULT_PITCH;
        Instrument expectedInstrument = DEFAULT_INSTR;
        Note note = new Note(1.5, DEFAULT_PITCH);
        Double resultDuration = note.duration();
        assertEquals(expectedDuration, resultDuration);
        assertEquals(expectedPitch, note.pitch());
        assertEquals(expectedInstrument, note.instrument());
    }
    
    // Testing duration, pitch, instrument
    // Tests duration = 0, integer, default instrument, valid pitch
    @Test
    public void testObservers1() {
        Double expectedDuration = new Double(0.0);
        Pitch expectedPitch = new Pitch('D');
        Instrument expectedInstrument = DEFAULT_INSTR;
        Note note = new Note(0, new Pitch('D'));
        Double resultDuration = note.duration();
        assertEquals(expectedDuration, resultDuration);
        assertEquals(expectedPitch, note.pitch());
        assertEquals(expectedInstrument, note.instrument());
    }
    
    // Tests duration > 0, decimal, not default instrument, valid pitch
    @Test
    public void testObservers2() {
        Double expectedDuration = new Double(2.3);
        Pitch expectedPitch = new Pitch('E');
        Instrument expectedInstrument = Instrument.CHOIR_AAHS;
        Note note = new Note(2.3, new Pitch('E'), Instrument.CHOIR_AAHS);
        Double resultDuration = note.duration();
        assertEquals(expectedDuration, resultDuration);
        assertEquals(expectedPitch, note.pitch());
        assertEquals(expectedInstrument, note.instrument());
    }
    
    // Testing transpose
    // Tests semitonesUp < -12, pitch transposed greater than octave down
    @Test
    public void testTransposeLargerThanOctaveDown() {
        Note first = new Note(1, new Pitch('C'));
        Note second = first.transpose(-14);
        assertTrue(second.pitch().toMidiNote() == 46);
    }
    
    // Tests semitonesUp = -12, pitch transposed octave down
    @Test
    public void testTransposeOctaveDown() {
        Note first = new Note(1, new Pitch('C'));
        Note second = first.transpose(-12);
        assertTrue(second.pitch().toMidiNote() == 48);
    }
    
    // Tests -12 < semitonesUp < 0, pitch transposed less than octave down
    @Test
    public void testTransposeLessThanOctaveDown() {
        Note first = new Note(1, new Pitch('C'));
        Note second = first.transpose(-1);
        assertTrue(second.pitch().toMidiNote() == 59);
    }
    
    // Tests semitonesUp = 0, pitch unchanged
    @Test
    public void testTransposeUnchanged() {
        Note first = new Note(1, new Pitch('C'));
        Note second = first.transpose(0);
        assertTrue(second.pitch().toMidiNote() == 60);
        assertTrue(first.pitch().toMidiNote() == second.pitch().toMidiNote());
    }
    
    // Tests 0 < semitonesUp < 12, pitch transposed less than octave up
    @Test
    public void testTransposeLessThanOctaveUp() {
        Note first = new Note(1, new Pitch('C'));
        Note second = first.transpose(3);
        assertTrue(second.pitch().toMidiNote() == 63);
    }
    
    // Tests semitonesUp = 12, pitch transposed octave up
    @Test
    public void testTransposeOctaveUp() {
        Note first = new Note(1, new Pitch('C'));
        Note second = first.transpose(12);
        assertTrue(second.pitch().toMidiNote() == 72);
    }
    
    // Tests semitonesUp > 12, pitch transposed greater than octave up
    @Test
    public void testTransposeGreaterThanOctaveUp() {
        Note first = new Note(1, new Pitch('C'));
        Note second = first.transpose(15);
        assertTrue(second.pitch().toMidiNote() == 75);
    }
    
    // Testing rescale
    // Tests scale = 0, output: new duration = 0
    @Test
    public void testRescaleZero() {
        Note first = new Note(1, DEFAULT_PITCH);
        Note second = first.rescale(0);
        Double expected = new Double(0.0);
        Double result = second.duration();
        assertEquals(expected, result);
        assertEquals(first.pitch(), second.pitch());
        assertEquals(first.instrument(), second.instrument());
    }
    
    // Tests 0 < scale < 1, output: new duration less than previous duration
    @Test
    public void testRescaleLessThanOne() {
        Note first = new Note(1, DEFAULT_PITCH);
        Note second = first.rescale(0.5);
        Double expected = new Double(0.5);
        Double result = second.duration();
        assertEquals(expected, result);
        assertEquals(first.pitch(), second.pitch());
        assertEquals(first.instrument(), second.instrument());
    }
    
    // Tests scale = 1, output: new duration equal to previous duration
    @Test
    public void testRescaleEqual() {
        Note first = new Note(1, DEFAULT_PITCH);
        Note second = first.rescale(1);
        Double expected = new Double(1.0);
        Double result = second.duration();
        assertEquals(expected, result);
        assertEquals(first.pitch(), second.pitch());
        assertEquals(first.instrument(), second.instrument());
    }
    
    // Tests scale > 1, output: new duration greater than previous duration
    @Test
    public void testRescaleGreaterThanOne() {
        Note first = new Note(1, DEFAULT_PITCH);
        Note second = first.rescale(1.5);
        Double expected = new Double(1.5);
        Double result = second.duration();
        assertEquals(expected, result);
        assertEquals(first.pitch(), second.pitch());
        assertEquals(first.instrument(), second.instrument());
    }
    
    // Testing equals
    // Tests obj not Note, output false
    @Test
    public void testEqualsNotNote() {
        Note thisNote = new Note(1, DEFAULT_PITCH);
        Rest obj = new Rest(1);
        assertFalse(thisNote.equals(obj));
    }
    
    // Tests obj unequal Note, output false
    @Test
    public void testEqualsNotEqual() {
        Note thisNote = new Note(1, DEFAULT_PITCH);
        Note thatNote = new Note(2, DEFAULT_PITCH);
        assertFalse(thisNote.equals(thatNote));
    }
    
    // Tests obj equal Note, output true
    @Test
    public void testEqualsEqual() {
        Note thisNote = new Note(1, DEFAULT_PITCH);
        Note thatNote = new Note(1, DEFAULT_PITCH);
        assertTrue(thisNote.equals(thatNote));
        assertEquals(thisNote.hashCode(), thatNote.hashCode());
    }
    
    // Testing hashCode
    // Tests 2 equal Note
    @Test
    public void testHashCodeEqual() {
        Note thisNote = new Note(1.5, DEFAULT_PITCH);
        Note thatNote = new Note(1.5, DEFAULT_PITCH);
        assertEquals(thisNote.hashCode(), thatNote.hashCode());
    }
    
    // Testing toString
    @Test
    public void testToString() {
        Note note = new Note(1, new Pitch('C'));
        assertFalse(note.toString().isEmpty());
    }
    
}
