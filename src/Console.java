import java.util.Scanner;

/**
 * Small helper class that wraps common and repeated behaviour for interacting with the console
 */
public class Console {

    /**
     * the log function is a shorthand to the log function in the console component
     * @param source the auther of the message
     * @param message the message contents
     */
    public static void log(String source, String message) {
        Window.consoleComponent.log(source, message);
    }

    /**
     * A helper function to make reading user input easier
     * @param scanner the scanner to read the input from
     * @param message the message to display to the user when asking for input
     * @return
     */
    public static String readLine(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}
