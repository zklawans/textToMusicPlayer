package abc.sound;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Tests Voices concrete implementation of Music.
 *
 */
public class VoicesTest {

    /*
     * Testing strategy for Voices
     * 
     * Partition for Voices(Map<String, Voice>):
     *   voices: size: 1, >1
     *   output: test using getVoices
     * 
     * Partition for Voices(Voice):
     *   music: valid Voice
     *   output: test map size 1, string is "Default"
     * 
     * Partition for getVoice:
     *   voiceName: "Default", non-empty string, empty string
     *              is in the voice map, isn't in the voice map
     *   output: voice, null
     * 
     * Partition for getVoices:
     *   output: test size of map, contains string "Default", doesn't contain "Default"
     * 
     * Partition for change:
     *   voiceName: empty string, non-empty string
     *              already in map, not already in map
     *   newMusic: a valid Voice
     *   output: getVoice(voiceName) = newMusic
     *           map size same as before, +1 from before
     * 
     * Partition for append(String, List<Measure>):
     *   voiceName: "", non-empty string
     *              in voice map, not in voice map
     *   appendedMusic: empty list, non-empty list
     *   output: map size same as before, +1 from before
     *           getVoice(voiceName) contains appendedMusic
     * 
     * Partition for append(String, Voice):
     *   voiceName: "", non-empty string
     *              in voice map, not in voice map
     *   appendedMusic: valid Voice
     *   output: map size same as before, +1 from before
     *           getVoice(voiceName) contains appendedMusic
     * 
     * Partition for duration:
     *   all voices same duration, not same duration
     * 
     * Partition for play:
     *   player: valid SequencePlayer
     *   atBeat: 0 >0, integer, decimal
     *   output: compare player.toString()
     *   IN SEPERATE FILE
     * 
     * Partition for rescale:
     *   scale: 0, 0<scale<1, 1, >1
     *   output: duration is 0, less than before, same as before, greater than before
     * 
     * Partition for equals:
     *   thatObject: not Voices, unequal Voices, equal Voices
     * 
     * Partition for hashCode:
     *   test on equal Voices
     * 
     * Partition for toString:
     *   test toString isn't empty
     */
    
    private static final Note C = new Note(1, new Pitch('C'));
    private static final List<Music> BAR = Arrays.asList(C, C, C, C);
    private static final Measure MEASURE = new Measure(BAR, 4, false, false, false, false, false);
    private static final Voice VOICE = new Voice(Arrays.asList(MEASURE));
    private static final List<Music> BAR2 = Arrays.asList(C, C, C.rescale(0.5), C, C.rescale(0.5));
    private static final Measure MEASURE2 = new Measure(BAR2, 4, false, false, false, false, false);
    private static final Voice VOICE2 = new Voice(Arrays.asList(MEASURE2));
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Testing Voices(Map<String, Voice>)
    // Tests voices.size = 1
    @Test
    public void testVoicesConstructor1stOne() {
        Map<String, Voice> voices = new HashMap<>();
        voices.put("only", VOICE);
        Voices result = new Voices(voices);
        assertTrue(result.getVoices().size() == 1);
        assertEquals(VOICE, result.getVoice("only"));
    }
    
    // Tests voices.size > 1
    @Test
    public void testVoicesConstructor1stGreaterThan1() {
        List<Music> bar1 = Arrays.asList(C, C, C, C);
        List<Music> bar2 = Arrays.asList(C, C, C.rescale(0.5), C, C.rescale(0.5));
        Measure measure1 = new Measure(bar1, 4, false, false, false, false, false);
        Measure measure2 = new Measure(bar2, 4, false, false, false, false, false);
        List<Measure> measures1 = Arrays.asList(measure1);
        List<Measure> measures2 = Arrays.asList(measure2);
        Voice voice1 = new Voice(measures1);
        Voice voice2 = new Voice(measures2);
        Map<String, Voice> voices = new HashMap<>();
        voices.put("upper", voice1);
        voices.put("lower", voice2);
        Voices result = new Voices(voices);
        assertTrue(result.getVoices().size() == 2);
        assertEquals(voice1, result.getVoice("upper"));
        assertEquals(voice2, result.getVoice("lower"));
    }
    
    // Testing Voices(Voice)
    // Tests valid voice
    @Test
    public void testVoicesConstructor2nd() {
        Voices result = new Voices(VOICE);
        assertTrue(result.getVoices().size() == 1);
        assertEquals(VOICE, result.getVoice("Default"));
    }
    
    // Testing getVoice
    // Tests voiceName = "Default", is in voice map, output voice
    @Test
    public void testGetVoiceDefault() {
        Voices result = new Voices(VOICE);
        assertTrue(result.getVoices().containsKey("Default"));
        assertEquals(VOICE, result.getVoice("Default"));
    }
    
    // Tests voiceName = "", not in voice map, output null
    @Test
    public void testGetVoiceNull() {
        Voices result = new Voices(VOICE);
        assertFalse(result.getVoices().containsKey(""));
        assertEquals(null, result.getVoice(""));
    }
    
    // Tests voiceName = "upper", in voice map, output voice
    @Test
    public void testGetVoiceVoice() {
        Map<String, Voice> voices = new HashMap<>();
        voices.put("upper", VOICE);
        voices.put("lower", VOICE2);
        Voices result = new Voices(voices);
        assertTrue(result.getVoices().containsKey("upper"));
        assertEquals(VOICE, result.getVoice("upper"));
    }
    
    // Testing getVoices
    // Tests map size = 1, voice name "Default"
    @Test
    public void testGetVoicesSizeDefault() {
        Voices result = new Voices(VOICE);
        assertTrue(result.getVoices().containsKey("Default"));
        assertEquals(VOICE, result.getVoice("Default"));
    }
    
    // Tests map size > 1, voice name not "Default"
    @Test
    public void testGetVoicesNotDefault() {
        Map<String, Voice> voices = new HashMap<>();
        voices.put("upper", VOICE);
        voices.put("lower", VOICE2);
        Voices result = new Voices(voices);
        assertFalse(result.getVoices().containsKey("Default"));
        assertEquals(VOICE, result.getVoice("upper"));
        assertEquals(VOICE2, result.getVoice("lower"));
    }
    
    // Testing change
    // Tests voiceName = "", already in map, map size same as before
    @Test
    public void testChangeEmptyString() {
        Measure measure1 = new Measure(BAR, 4, true, false, false, false, false);
        Measure measure2 = new Measure(BAR, 4, false, true, true, false, false);
        Measure measure3 = new Measure(BAR2, 4, false, false, false, true, false);
        Voice voice1 = new Voice(Arrays.asList(MEASURE2, MEASURE2, MEASURE));
        Voice voice2 = new Voice(Arrays.asList(measure1, measure2, measure3));
        Map<String, Voice> voices = new HashMap<>();
        voices.put("", voice1);
        voices.put("2", voice2);
        Voices first = new Voices(voices);
        Voices result = first.change("", voice2);
        assertTrue(result.getVoices().size() == 2);
        assertEquals(voice2, result.getVoice(""));
    }
    
    // Tests voiceName = non-empty string, not already in map, map size +1 from before
    @Test
    public void testChangeNonEmptyString() {
        Map<String, Voice> voices = new HashMap<>();
        voices.put("first", VOICE);
        voices.put("second", VOICE2);
        Voices start = new Voices(voices);
        Measure measure1 = new Measure(Arrays.asList(new Rest(2), C, C), 4, false, false, false, false, false);
        Voice newMusic = new Voice(Arrays.asList(measure1));
        Voices result = start.change("third", newMusic);
        assertTrue(result.getVoices().size() == 3);
        assertEquals(newMusic, result.getVoice("third"));
    }
    
    // Testing append(String, List<Measure>)
    // Tests voiceName = "", not in voice map, appendedMusic non-empty list
    // output map size +1 from before
    @Test
    public void testFirstAppendNotInVoiceMap() {
        Voices start = new Voices(VOICE);
        Voices result = start.append("", Arrays.asList(MEASURE2));
        assertTrue(result.getVoices().size() == 2);
        assertEquals(new Voice(Arrays.asList(MEASURE2)), result.getVoice(""));
    }
    
    // Tests voiceName is non-empty string, in voice map, appendedMusic empty list
    // output map same size as before
    @Test
    public void testFirstAppendEmptyList() {
        Voices start = new Voices(VOICE);
        Voices result = start.append("Default", Arrays.asList());
        assertTrue(result.getVoices().size() == 1);
        assertEquals(VOICE, result.getVoice("Default"));
    }
    
    // Tests voiceName is non-empty string, in voice map, appendedMusic non-empty list
    // output map same size as before
    @Test
    public void testFirstAppendNonEmptyList() {
        Voices start = new Voices(VOICE);
        Voices result = start.append("Default", Arrays.asList(MEASURE2));
        Voice voice = new Voice(Arrays.asList(MEASURE, MEASURE2));
        assertTrue(result.getVoices().size() == 1);
        assertEquals(voice, result.getVoice("Default"));
    }
    
    // Testing append(String, Voice)
    // Tests voiceName = "", not in voice map, map size +1 from before
    @Test
    public void testSecondAppendNotInVoiceMap() {
        Voices first = new Voices(VOICE);
        Voices result = first.append("", VOICE2);
        assertTrue(result.getVoices().size() == 2);
        assertEquals(VOICE2, result.getVoice(""));
    }
    
    // Tests voiceName is non-empty string, in voice map, map size same as before
    @Test
    public void testSecondAppendInVoiceMap() {
        Voices first = new Voices(VOICE);
        Voices result = first.append("Default", VOICE2);
        assertTrue(result.getVoices().size() == 1);
        assertEquals(VOICE.append(VOICE2), result.getVoice("Default"));
    }
    
    // Testing duration
    // Tests all voices same duration
    @Test
    public void testDurationAllSame() {
        Map<String, Voice> voices = new HashMap<>();
        voices.put("upper", VOICE);
        voices.put("lower", VOICE2);
        Voices result = new Voices(voices);
        Double expectedDuration = new Double(4.0);
        Double resultDuration = result.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Tests different voice durations
    @Test
    public void testDurationDifferent() {
        Map<String, Voice> voices = new HashMap<>();
        voices.put("first", VOICE);
        voices.put("second", VOICE.append(VOICE2));
        Voices result = new Voices(voices);
        Double expectedDuration = new Double(8.0);
        Double resultDuration = result.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Testing rescale
    // Tests scale = 0, output duration = 0
    @Test
    public void testRescaleZero() {
        Voices voices = new Voices(VOICE);
        Voices newVoices = voices.rescale(0);
        Double expectedDuration = new Double(0);
        Double resultDuration = newVoices.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Tests 0<scale<1, output duration less than previous duration
    @Test
    public void testRescaleLessThanPrevious() {
        Voices voices = new Voices(VOICE);
        Voices newVoices = voices.rescale(0.5);
        Double expectedDuration = new Double(2);
        Double resultDuration = newVoices.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Tests scale = 1, output duration equal to previous duration
    @Test
    public void testRescaleEqualToPrevious() {
        Voices voices = new Voices(VOICE);
        Voices newVoices = voices.rescale(1);
        Double expectedDuration = new Double(4);
        Double resultDuration = newVoices.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Tests scale > 1, output duration greater than previous duration
    @Test
    public void testRescaleGreaterThanPrevious() {
        Voices voices = new Voices(VOICE);
        Voices newVoices = voices.rescale(1.5);
        Double expectedDuration = new Double(6);
        Double resultDuration = newVoices.duration();
        assertEquals(expectedDuration, resultDuration);
    }
    
    // Testing equals
    // Tests thatObject not Voices
    @Test
    public void testEqualsNotVoices() {
        Voices voices = new Voices(VOICE);
        Note thatObject = new Note(1, new Pitch('D'));
        assertFalse(voices.equals(thatObject));
    }
    
    // Tests thatObject unequal voices
    @Test
    public void testEqualsUnequalVoices() {
        Voices voices = new Voices(VOICE);
        Voices thatObject = new Voices(VOICE2);
        assertFalse(voices.equals(thatObject));
    }
    
    // Tests thatObject equal voices
    @Test
    public void testEqualsEqualVoices() {
        Voices voices = new Voices(VOICE);
        Voices thatObject = new Voices(VOICE);
        assertTrue(voices.equals(thatObject));
        assertEquals(voices.hashCode(), thatObject.hashCode());
    }
    
    // Testing hashCode
    // Tests hashCodes of 2 equal Voices
    @Test
    public void testHashCode() {
        Voices first = new Voices(VOICE2);
        Voices second = new Voices(VOICE2);
        assertEquals(first.hashCode(), second.hashCode());
    }
    
    // Testing toString
    // Tests with one voice
    @Test
    public void testToStringOne() {
        Voices voice = new Voices(VOICE);
        assertFalse(voice.toString().isEmpty());
    }
    
    // Tests with multiple voices
    @Test
    public void testToStringMultiple() {
        Map<String, Voice> voiceMap = new HashMap<>();
        voiceMap.put("first", VOICE);
        voiceMap.put("second", VOICE2);
        Voices voices = new Voices(voiceMap);
        assertFalse(voices.toString().isEmpty());
    }
    
}
