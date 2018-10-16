package abc.parser;

import abc.sound.*;
import abc.header.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;
import lib6005.parser.UnableToParseException;

/**
 * 
 * Parser for parsing abc file contents into Song
 */
public class AbcParser {
    private static final String END_REPEAT = ":|";
    private static final String START_REPEAT = ":";
    private static final String START_FIRST_ENDING = "[1";
    private static final String START_SECOND_ENDING = "[2";
    private static final Set<String> SECTION_ENDINGS = new HashSet<>(Arrays.asList("||", "[|", "|]"));
    /**
     * Parse a string into an integer arithmetic expression, displaying various
     * debugging output.
     * @param string the string representation of the text contained in an abc file
     * @throws If the string cannot be parsed, this method throws an UnableToParseException.
     * @throws If the grammar file Abc.g is not present, this will throw an IOException.
     * @throws If the grammar file is corrupted and cannot be parsed, the method will also throw an UnableToParseException.
     */
    public static Song parse(String string) throws UnableToParseException, IOException{
         Parser<MusicGrammar> parser = GrammarCompiler.compile(new File("src/abc/parser/Abc.g"), MusicGrammar.ABC_TUNE);
         ParseTree<MusicGrammar> tree = parser.parse(string);
         Song song = buildAST(tree);
         return song;
    }
    
    /**
     * Function converts a ParseTree to a Song. 
     * @param p
     *  ParseTree<MusicGrammar> that is assumed to have been constructed by the grammar in Abc.g
     * @return Song constructed from ParseTree p
     * @throws UnableToParseException If the song cannot be parsed, this method throws an UnableToParseException.
     */
    private static Song buildAST(ParseTree<MusicGrammar> p) throws UnableToParseException{
        
        if (p.getName().equals(MusicGrammar.ABC_TUNE)){
            Header header = buildHeaderAST(p.childrenByName(MusicGrammar.ABC_HEADER).get(0));
            KeySignature key = header.getKeySignature();
            KeySignatureMap keyMap = new KeySignatureMap();
            Map<Character, Accidental> accidentals = keyMap.getKey(key);
            Music music = buildMusicAST(p.childrenByName(MusicGrammar.ABC_MUSIC).get(0), header, accidentals);
            return new Song(music, header);
        }   
        /*
         * The compiler should be smart enough to tell that this code is unreachable, but it isn't.
         */
        throw new RuntimeException("You should never reach here:" + p);
    }
    
    /**
     * Function converts a ParseTree to a Header. 
     * @param p
     *  ParseTree<MusicGrammar> that is assumed to have been constructed by the Header grammar in Abc.g 
     * @return Header constructed from ParseTree p
     * @throws UnableToParseException
     */
    private static Header buildHeaderAST(ParseTree<MusicGrammar> p) throws UnableToParseException{
        final int defaultTempo = 100;
        final double eighth = 1.0/8.0;
        final double sixteenth = 1.0/16.0;
        List<String> voice = new ArrayList<>();
        String composerName = "Unknown";
        String keyBase = "C";
        String keyAccidental = "=";
        boolean ifMinor = false;
        KeySignature keySignature = new KeySignature("C");
        Meter meter = new Meter("C");
        double defaultLength = eighth;
        boolean ifSetDefaultLength = false;
        Tempo tempo = new Tempo(defaultLength, defaultTempo);
        boolean specifiedTempo = false;
        String title = "Untitled";
        int indexNumber = 0;
        if (p.getName().equals(MusicGrammar.ABC_HEADER)) {
            String indexString = "";
            for (ParseTree<MusicGrammar> digit : p.childrenByName(MusicGrammar.FIELD_NUMBER).get(0).childrenByName(MusicGrammar.DIGIT)) {
                indexString += digit.getContents();
            }
            indexNumber = Integer.parseInt(indexString);
            title = p.childrenByName(MusicGrammar.FIELD_TITLE).get(0)
                    .childrenByName(MusicGrammar.TEXT).get(0).getContents();
            ParseTree<MusicGrammar> keySubtree = p.childrenByName(MusicGrammar.FIELD_KEY).get(0)
                    .childrenByName(MusicGrammar.KEY).get(0);
            ifMinor = !keySubtree.childrenByName(MusicGrammar.MODE_MINOR).isEmpty();
            keyBase = keySubtree.childrenByName(MusicGrammar.KEYNOTE).get(0)
                    .childrenByName(MusicGrammar.BASENOTE).get(0).getContents();
            try {
                keyAccidental = keySubtree.childrenByName(MusicGrammar.KEYNOTE).get(0)
                        .childrenByName(MusicGrammar.KEY_ACCIDENTAL).get(0).getContents();
            } catch (IndexOutOfBoundsException e) {
                keyAccidental = "=";
            }
            keySignature = new KeySignature(keyBase, keyAccidental, ifMinor);
            List<ParseTree<MusicGrammar>> optionals = p.childrenByName(MusicGrammar.OTHER_FIELDS);
            for(ParseTree<MusicGrammar> optional: optionals) {
                ParseTree<MusicGrammar> field = optional.children().get(0);
                switch(field.getName()) {
                case FIELD_COMPOSER: {
                    composerName = field.childrenByName(MusicGrammar.TEXT).get(0).getContents();
                    break;
                }
                case FIELD_DEFAULT_LENGTH: {
                    defaultLength = getDuration(field, true);
                    ifSetDefaultLength = true;
                    if(!specifiedTempo)
                        tempo = new Tempo(defaultLength, defaultTempo);
                    break;
                }
                case FIELD_METER: {
                    if (field.childrenByName(MusicGrammar.METER).get(0)
                            .childrenByName(MusicGrammar.METER_FRACTION).isEmpty()) {
                        String meterString = field.childrenByName(MusicGrammar.METER).get(0).getContents();
                        try {
                            meter = new Meter(meterString);
                        } catch(IllegalArgumentException e) {
                            throw new UnableToParseException("invalid meter");
                        }
                        if (!ifSetDefaultLength) {
                            if ((double)meter.getNumerator() / (double)meter.getDenominator() >= 0.75)
                                defaultLength = eighth;
                            else
                                defaultLength = sixteenth;
                        }
                    }
                    else {
                        String meterFraction = field.childrenByName(MusicGrammar.METER).get(0)
                                                       .childrenByName(MusicGrammar.METER_FRACTION).get(0).getContents();
                        String[] ratio = meterFraction.split("/");
                        meter = new Meter(Integer.parseInt(ratio[0]), Integer.parseInt(ratio[1]));
                    }
                    break;
                }
                case FIELD_TEMPO: {
                    String tempoString = field.childrenByName(MusicGrammar.TEMPO).get(0).getContents();
                    String[] dividedString = tempoString.split("=");
                    
                    String meterFraction = field.childrenByName(MusicGrammar.TEMPO).get(0)
                                                   .childrenByName(MusicGrammar.METER_FRACTION).get(0).getContents();
                    String[] ratio = meterFraction.split("/");
                    tempo = new Tempo(Double.parseDouble(ratio[0]) / Double.parseDouble(ratio[1]), Integer.parseInt(dividedString[1]));
                    specifiedTempo = true;
                    break;
                }
                case FIELD_VOICE: {
                    voice.add(field.childrenByName(MusicGrammar.TEXT).get(0).getContents());
                    break;
                }
                default:
                    throw new UnableToParseException("invalid header");
                }
            }
            return new Header(voice, composerName, keySignature, defaultLength, meter, tempo, title, indexNumber);
        }
        else
            throw new UnableToParseException("unable to parse header");
    }
    /**
     * Function converts a ParseTree to music. 
     * @param p
     *  ParseTree<MusicGrammar> that is assumed to have been constructed by the Music grammar in Abc.g
     * @param header the Header containing the information about this song
     * @param accidentals the key signature map
     * @return Music constructed from ParseTree p
     * @throws UnableToParseException unable to parse
     */
    private static Music buildMusicAST(ParseTree<MusicGrammar> p, Header header, Map<Character, Accidental> accidentals) throws UnableToParseException{
        
        switch(p.getName()){
        /*
         * Since p is a ParseTree parameterized by the type MusicGrammar, p.getName() 
         * returns an instance of the MusicGrammar enum. This allows the compiler to check
         * that we have covered all the cases.
         */
        case VOICE:
            Voice voiceTune = new Voice();
            for (ParseTree<MusicGrammar> line : p.childrenByName(MusicGrammar.ABC_LINE)){
                voiceTune = voiceTune.append((Voice)buildMusicAST(line, header, accidentals));
            }
            return voiceTune;
        case ABC_MUSIC:
            Voices voices = new Voices();
            List<ParseTree<MusicGrammar>> defaultVoice = p.childrenByName(MusicGrammar.ABC_LINE);
            if(defaultVoice.size() > 0){
                Voice tune = new Voice();
                boolean empty = true;
                for (ParseTree<MusicGrammar> line : defaultVoice){
                    if (line.childrenByName(MusicGrammar.MEASURE).size()>0){
                        tune = tune.append((Voice)buildMusicAST(line, header, accidentals));
                        empty = false;
                    }
                }
                if (!empty){
                    voices = new Voices(tune);
                }
            }
            for (ParseTree<MusicGrammar> voice : p.childrenByName(MusicGrammar.VOICE)){
                String name = getName(voice);
                voices = voices.append(name, (Voice)buildMusicAST(voice, header, accidentals));
            }
            return voices;
        case ABC_LINE:
            List<Measure> measures = new ArrayList<>();
            for (ParseTree<MusicGrammar> child : p.childrenByName(MusicGrammar.MEASURE)){
                measures.add((Measure)buildMusicAST(child, header, accidentals));
            }
            return new Voice(measures);
        case MEASURE:
            Map<Character, Accidental> measureAccidentals = new HashMap<>(accidentals);
            List<Music> measureElements = new ArrayList<>();
            for (ParseTree<MusicGrammar> child : p.childrenByName(MusicGrammar.ELEMENT)){
                measureElements.add(buildMusicAST(child, header, measureAccidentals));
            }
            boolean startSecondEnding = false;
            boolean startFirstEnding = false;
            boolean endRepeat = false;
            boolean startRepeat = false;
            boolean endMajorSection = false;
            if (p.childrenByName(MusicGrammar.BARLINE).size() > 0){
                String bar = p.childrenByName(MusicGrammar.BARLINE).get(0).getContents();
                if (bar.equals(END_REPEAT)){
                    endRepeat = true;
                }
                if (SECTION_ENDINGS.contains(bar)){
                    endMajorSection = true;
                }
            }
            List<ParseTree<MusicGrammar>> prefixList = p.childrenByName(MusicGrammar.MEASURE_PREFIX);
            if (prefixList.size() > 0){
                String prefix = prefixList.get(0).getContents();
                if (prefix.equals(START_REPEAT)){
                    startRepeat = true;
                }
                else if (prefix.equals(START_FIRST_ENDING)){
                    startFirstEnding = true;
                }
                else if (prefix.equals(START_SECOND_ENDING)){
                    startSecondEnding = true;
                }
            }     
            return new Measure(measureElements, header.getMeter().getNumerator(), startRepeat, endRepeat, startFirstEnding, startSecondEnding, endMajorSection);
        case ELEMENT:
            List<ParseTree<MusicGrammar>> noteElementChild = p.childrenByName(MusicGrammar.NOTE_ELEMENT);
            if (noteElementChild.size() > 0){
                return buildMusicAST(noteElementChild.get(0), header, accidentals);
            }
            List<ParseTree<MusicGrammar>> tupletElementList = p.childrenByName(MusicGrammar.TUPLET_ELEMENT);
            if (tupletElementList.size() >0){
                return buildMusicAST(tupletElementList.get(0), header, accidentals);
            }
            return new Rest(0);
        case NOTE_ELEMENT:
            return buildMusicAST(p.children().get(0), header, accidentals);
        case NOTE:
            Music noteOrRest = buildMusicAST(p.childrenByName(MusicGrammar.NOTE_OR_REST).get(0), header, accidentals);
            double duration = getDuration(p, false);
            return noteOrRest.rescale(duration);
        case NOTE_OR_REST:
            return buildMusicAST(p.children().get(0), header, accidentals);
        case PITCH:
            char baseNote = p.childrenByName(MusicGrammar.BASENOTE).get(0).getContents().charAt(0);
            boolean isLowerCase = Character.isLowerCase(baseNote);
            baseNote = Character.toUpperCase(baseNote);
            Pitch pitch = new Pitch(baseNote);
            if (isLowerCase){
                pitch = pitch.addOctave();
            }
            List<ParseTree<MusicGrammar>> accidentalList = p.childrenByName(MusicGrammar.ACCIDENTAL);
            if (accidentalList.size() > 0){
                String accidental = accidentalList.get(0).getContents();
                if (accidental.contains("^")){
                    pitch = pitch.addSharp();
                    accidentals.put(baseNote, Accidental.SHARP);
                }
                else if(accidental.contains("_")){
                    pitch = pitch.addFlat();
                    accidentals.put(baseNote, Accidental.FLAT);
                }
                if (accidental.equals("^^")){
                    pitch = pitch.addSharp();
                    accidentals.put(baseNote, Accidental.DOUBLESHARP);
                }
                else if(accidental.equals("__")){
                    pitch = pitch.addFlat();
                    accidentals.put(baseNote, Accidental.DOUBLEFLAT);
                }
                else if(accidental.equals("=")){
                    accidentals.put(baseNote, Accidental.NATURAL);
                }
            }
            else if (accidentals.containsKey(baseNote)){
                Accidental accidental = accidentals.get(baseNote);
                if (accidental.equals(Accidental.FLAT)){
                    pitch = pitch.addFlat();
                }
                else if (accidental.equals(Accidental.DOUBLEFLAT)){
                    pitch = pitch.addDoubleFlat();
                }
                else if (accidental.equals(Accidental.DOUBLESHARP)){
                    pitch = pitch.addDoubleSharp();
                }
                else if (accidental.equals(Accidental.SHARP)){
                    pitch = pitch.addSharp();
                }
            }
            List<ParseTree<MusicGrammar>> octaveList = p.childrenByName(MusicGrammar.OCTAVE);
            if (octaveList.size() > 0){
                char[] octaves = octaveList.get(0).getContents().toCharArray();
                for (char octave: octaves){
                    if (octave == '\''){
                        pitch = pitch.addOctave();
                    }
                    else if (octave == ','){
                        pitch = pitch.subtractOctave();
                    }
                }
            }
            return new Note(header.getBeatsPerDefaultNote(), pitch);
        case REST:
            return new Rest(header.getBeatsPerDefaultNote());
        case MULTI_NOTE:
            Set<Note> notes = new HashSet<>();
            List<ParseTree<MusicGrammar>> noteList = p.childrenByName(MusicGrammar.NOTE);
            for (ParseTree<MusicGrammar> noteTree: noteList){
                notes.add((Note)buildMusicAST(noteTree, header, accidentals));
            }
            return new Chord(notes);
        case TUPLET_ELEMENT:
            List<ParseTree<MusicGrammar>> noteElementList = p.childrenByName(MusicGrammar.NOTE_ELEMENT);
            Music[] tupletElements = new Music[noteElementList.size()];
            for (int i = 0; i < tupletElements.length; i++){
                tupletElements[i] = buildMusicAST(noteElementList.get(i), header, accidentals);
            }
            return new Tuplet(tupletElements);
        default:
            throw new UnableToParseException("invalid music");
        }
    }
    
    /**
     * Get the name of a voice
     * @param voice parse tree
     * @return name of voice
     */
    private static String getName(ParseTree<MusicGrammar> voice) {
        ParseTree<MusicGrammar> fieldVoice = voice.childrenByName(MusicGrammar.MID_TUNE_FIELD).get(0).childrenByName(MusicGrammar.FIELD_VOICE).get(0);
        String name = fieldVoice.childrenByName(MusicGrammar.TEXT).get(0).getContents();
        return name;
    }

    /**
     * Parse the duration of a note/rest
     * @param p parse tree
     * @param ifStrict true if the abc file text follows the strict definition of note length, false otherwise
     * @return duration
     */
    private static double getDuration(ParseTree<MusicGrammar> p, boolean ifStrict){
        double numerator = 1;
        double denominator = 1;
        List<ParseTree<MusicGrammar>> children;
        if (ifStrict) 
            children = p.childrenByName(MusicGrammar.NOTE_LENGTH_STRICT);
        else
            children = p.childrenByName(MusicGrammar.NOTE_LENGTH);
        if (children.size() > 0){
            String noteLength = children.get(0).getContents();
            if (noteLength.equals("/")){
                return 1;
            }
            String[] ratio = noteLength.split("/");
            if (ratio[0].length()>0){
                numerator = Double.parseDouble(ratio[0]);
            }
            if (ratio.length > 1 && ratio[1].length()>0){
               denominator = Double.parseDouble(ratio[1]);
            }
        }
        
        return numerator/denominator;
    }


}