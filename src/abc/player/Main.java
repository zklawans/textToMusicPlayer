package abc.player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import abc.parser.*;
import lib6005.parser.UnableToParseException;

/**
 * Main entry point of application.
 */
public class Main {

    /**
     * Plays the input file using Java MIDI API and displays
     * header information to the standard output stream.
     * 
     * (Your code should not exit the application abnormally using
     * System.exit().)
     * 
     * @param file the name of input abc file
     */
    public static void play(String file) {
        String contents = "";
        try {
            contents = new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            throw new RuntimeException("File not found: " + file);
        }
        try {
            AbcParser.parse(contents).play();
        } catch (UnableToParseException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid file: " + contents);
        }
    }

    public static void main(String[] args) {
        try {
            String file = args[0];
            Main.play(file);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: missing file name");
        }
    }
}
