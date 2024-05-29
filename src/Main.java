import java.util.Scanner;

public class Main {

    public static Dungeon dungeon = new Dungeon();
    public static Character player = new Character();
    public static Window window = new Window();

    // Component's

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Window.consoleComponent.log(
            "system",
            "Welcome to the dungeon prisoner!"
        );

        Window.consoleComponent.log("system", "what is your name prisoner?");
        window.draw();

        System.out.print("name: ");
        String prisonerName = input.nextLine();

        player.setName(prisonerName);

        Window.consoleComponent.log("player", prisonerName);
        window.draw();

        while (
            player.currentHealth > 0 ||
            ((dungeon.xPos == 4 && dungeon.yPos == 4))
        ) {
            System.out.print("command: ");

            window.draw();
        }

        input.close();
    }
}
