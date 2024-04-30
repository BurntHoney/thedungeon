import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter you name: ");
        String playerName = scanner.nextLine();

        Player player = new Player(playerName, 10, 10);
        Game game = new Game(player);

        while (!game.getGameOver()){
            game.drawGrid(scanner);
            game.gameLoop(scanner);
        }

        scanner.close();
    }
}