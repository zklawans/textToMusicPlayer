



@skip whitespace{
	abc_tune ::= abc_header abc_music;
	abc_header ::= field_number comment* field_title other_fields* field_key;
	field_number ::= "X:" DIGIT+ end_of_line;
	field_title ::= "T:" text end_of_line;
	other_fields ::= field_composer | field_default_length | field_meter | field_tempo | field_voice | comment;
	field_composer ::= "C:" text end_of_line;
	field_default_length ::= "L:" note_length_strict end_of_line;
	field_meter ::= "M:" meter end_of_line;
	field_tempo ::= "Q:" tempo end_of_line;
	field_voice ::= "V:" text end_of_line;
	field_key ::= "K:" key end_of_line;
	
	key ::= keynote mode_minor?;
	keynote ::= basenote key_accidental?;
	key_accidental ::= "#" | "b";
	mode_minor ::= "m";
	
	meter ::= "C" | "C|" | meter_fraction;
	meter_fraction ::= DIGIT+ "/" DIGIT+;
	
	tempo ::= meter_fraction "=" DIGIT+;
}
abc_music ::= (abc_line* voice+)+ | abc_line+;
abc_line ::= (measure+|comment|WHITESPACE*) end_of_line?;
element ::= WHITESPACE* (note_element | tuplet_element) WHITESPACE*;


measure ::= barline? measure_prefix? element+ barline?;
note_element ::= note | multi_note;
voice ::= mid_tune_field abc_line*;

note ::= note_or_rest note_length?;
note_or_rest ::= pitch | rest;
pitch ::= accidental? basenote octave?;
octave ::= "'"+ | ","+;
note_length ::= (DIGIT+)? ("/" (DIGIT+)?)?;
note_length_strict ::= DIGIT+ "/" DIGIT+;

accidental ::= "^" | "^^" | "_" | "__" | "=";

basenote ::= "C" | "D" | "E" | "F" | "G" | "A" | "B"
        | "c" | "d" | "e" | "f" | "g" | "a" | "b";

rest ::= "z";

tuplet_element ::= tuplet_spec note_element+;
tuplet_spec ::= "(" DIGIT;


multi_note ::= "[" (WHITESPACE* note WHITESPACE*)+ "]";

barline ::= "|" | "||" | "[|" | "|]" | ":|" | "|:";
measure_prefix ::= ":" | "[1" | "[2";

mid_tune_field ::= field_voice;




comment ::= "%" text NEWLINE;
end_of_line ::= comment | NEWLINE;

DIGIT ::= [0-9];
NEWLINE ::= "\n" | "\r" "\n"?;
WHITESPACE ::= " " | "\t";

text ::= [^\n\r]*;
