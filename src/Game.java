import java.util.Random;

public class Game {
    private boolean gameOver;

    public int xPos = 0;
    public int yPos = 2;

    public Room[][] grid;

    Game() {
        this.gameOver = false;

        Random rand = new Random();
        // Initialize Room
        this.grid = new Room[5][5];
        grid[0][0] = new Room(" ", new Enemy[]{new Enemy("Bat", rand.nextInt(1, 5), rand.nextInt(1, 5))});
        grid[0][1] = new Room(" ", new Enemy[]{new Enemy("Skeleton", rand.nextInt(1, 5), rand.nextInt(1, 5))});
        grid[0][2] = new Room(" ");
        grid[0][3] = new Room(" ");
        grid[0][4] = new Room(" ");

        grid[1][0] = new Room(" ");
        grid[1][1] = new Room("w");
        grid[1][2] = new Room("l");
        grid[1][3] = new Room("w");
        grid[1][4] = new Room("m");

        grid[2][0] = new Room(" ");
        grid[2][1] = new Room("w");
        grid[2][2] = new Room(" ");
        grid[2][3] = new Room("w");
        grid[2][4] = new Room("w");

        grid[3][0] = new Room("w");
        grid[3][1] = new Room("w");
        grid[3][2] = new Room(" ");
        grid[3][3] = new Room(" ");
        grid[3][4] = new Room("b");

        grid[4][0] = new Room("t");
        grid[4][1] = new Room(" ");
        grid[4][2] = new Room(" ");
        grid[4][3] = new Room("w");
        grid[4][4] = new Room("l");
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void gameLoop() {
        String input = Main.display.readLine("Command: ");
        Main.display.printLine("player", input);

        System.out.println(input);
        if (input.contains("quit")) {
            gameOver = true;
        }
    }
}
