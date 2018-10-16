package abc.parser;

/**
 * 
 * enum the Music Grammar for a Song
 */
public enum MusicGrammar {
    ABC_TUNE,
    ABC_HEADER,
    FIELD_NUMBER,
    FIELD_TITLE,
    OTHER_FIELDS,
    FIELD_COMPOSER,
    FIELD_DEFAULT_LENGTH,
    FIELD_METER,
    FIELD_TEMPO,
    FIELD_VOICE,
    FIELD_KEY,
    KEY,
    KEYNOTE,
    KEY_ACCIDENTAL,
    MODE_MINOR,
    METER,
    METER_FRACTION,
    TEMPO,
    
    ABC_MUSIC,
    ABC_LINE,
    ELEMENT,
    NOTE_ELEMENT,
    NOTE,
    NOTE_OR_REST,
    PITCH,
    OCTAVE,
    NOTE_LENGTH,
    NOTE_LENGTH_STRICT,
    ACCIDENTAL,
    BASENOTE,
    REST,
    TUPLET_ELEMENT,
    TUPLET_SPEC,
    MULTI_NOTE,
    BARLINE,
    MID_TUNE_FIELD,
    
    COMMENT,
    END_OF_LINE,
    DIGIT,
    NEWLINE,
    WHITESPACE,
    TEXT,
    
    VOICE,
    MEASURE,
    MEASURE_PREFIX
}
