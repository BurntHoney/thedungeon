import java.util.Scanner;

public class Main {

    public static Dungeon dungeon = new Dungeon();
    public static Character player = new Character();
    public static Window window = new Window();

    public static boolean isGameOver = false;
    public static boolean isInBattle = false;
    public static boolean enteredRoom = false;

    public static void main(String[] args) {
        // Initialize Player Defaults
        player.setDamage(5);
        player.setHealth(20);

        Scanner input = new Scanner(System.in);

        Console.log("system", "Welcome to the dungeon prisoner!");
        Console.log("system", "What is your name prisoner?");
        window.draw();

        String prisonerName = Console.readLine(input, "name? ");

        player.setName(prisonerName);

        Console.log("player", prisonerName);
        window.draw();

        while (!isGameOver) {
            // Process Commands
            String command = Console.readLine(input, "Command: ");
            Console.log("player", command);
            int result = processCommand(command);

            // If in battle let the enemies attack the player
            if (
                result == 0 && isInBattle && !enteredRoom
            ) dungeon.simulateBattle();
            if (enteredRoom) enteredRoom = false;

            // Redraw the window
            window.draw();
        }

        input.close();
    }

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
