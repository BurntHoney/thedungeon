import java.util.Scanner;

public class Game {
    private boolean gameOver;

    private Player player;
    private int xPos;
    private int yPos;

    private Room[] grid;


    Game(Player player) {
        this.player = player;

        this.gameOver = false;
        this.xPos = 0;
        this.yPos = 2;

        // Initialize Room
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void drawGrid(Scanner scanner) {

    }

    public void gameLoop(Scanner scanner){

    }
}
