import java.util.Scanner;

/**
 * Controlls the entire program
 */
public class Main {

    public static Dungeon dungeon = new Dungeon();
    public static Character player = new Character();
    public static Window window = new Window();

    public static boolean isGameOver = false;
    public static boolean isInBattle = false;
    public static boolean enteredRoom = false;

    public static void main(String[] args) {
        // Initialize Player Defaults
        player.setDamage(2);
        player.setMaxHealth(20);

        Scanner input = new Scanner(System.in);

        Console.log("system", "Welcome to the dungeon prisoner!");
        Console.log("system", "What is your name prisoner?");
        window.draw();

        String prisonerName = Console.readLine(input, "name? ");

        player.setName(prisonerName);

        Console.log("player", prisonerName);
        window.draw();

        Console.log(
            "System",
            "you have been trapped in a dungeon by the dragon after failing to steal his gold"
        );
        Console.log(
            "System",
            "in order to escape you must first learn how to move around"
        );
        Console.log(
            "System",
            "use the command move <direction> where direction is a cardinal direction n/s/e/w"
        );
        Console.log("System", "please use the command move n to move up");

        window.draw();

        String tutorialCommand = Console.readLine(input, "Command: ").strip();
        while (!tutorialCommand.equals("move n")) {
            Console.log("System", "please use the command move n move up");
            window.draw();

            tutorialCommand = Console.readLine(input, "Command: ").strip();
        }
        dungeon.movePlayer("n");
        Console.log("system", "well done making it to your next room");
        Console.log(
            "system",
            "in the next room you can battle your first enemy, good luck"
        );
        window.draw();

        while (!isGameOver) {
            // Process Commands
            String command = Console.readLine(input, "Command: ").strip();
            Console.log("player", command);
            int result = processCommand(command);

            // If in battle let the enemies attack the player
            if (
                result == 0 && isInBattle && !enteredRoom
            ) dungeon.attackPlayer();
            if (enteredRoom) enteredRoom = false;

            // Redraw the window
            window.draw();
        }

        input.close();
    }

    /**
     * the function processCommand takes care of processing the input and performs
     * an action based of the command
     *
     * @param input
     * @return int 0 for no error, -1 for no error but skip the action and 1 for an
     *         error and skip the action
     */
    private static int processCommand(String input) {
        String[] args = input.split(" ", 2);

        String command = args[0];
        String command_args;
        try {
            command_args = args[1];
        } catch (IndexOutOfBoundsException e) {
            command_args = "";
        }

        switch (command) {
            case "move":
                return dungeon.movePlayer(command_args);
            case "attack":
                return dungeon.attackEnemy(command_args);
            case "quit":
                isGameOver = true;
                return 0;
            case "use":
                return player.useItem(command_args);
            case "resize_width":
                try {
                    int width = Integer.parseInt(command_args);
                    window.setDisplayWidth(width);
                    return -1;
                } catch (NumberFormatException e) {
                    Console.log("system", "the input must be a number");
                    return 1;
                }
            case "resize_height":
                try {
                    int height = Integer.parseInt(command_args);
                    window.setDisplayHeight(height);
                    return -1;
                } catch (NumberFormatException e) {
                    Console.log("system", "the input must be a number");
                    return 1;
                }
            default:
                Console.log("system", "this command does not exist!!");
                return -1;
        }
    }
}
