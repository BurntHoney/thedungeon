import java.util.ArrayList;
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

        grid[0][0] = new Room(" ");
        grid[0][0].enemies.add(new Enemy("Bat", rand.nextInt(1, 5), rand.nextInt(1, 5)));

        grid[0][1] = new Room(" ");
        grid[0][2] = new Room(" ");
        grid[0][3] = new Room(" ");
        grid[0][4] = new Room(" ");

        grid[1][0] = new Room(" ");
        grid[1][1] = new Room("w");
        grid[1][2] = new Room("l", "door_key");
        grid[1][3] = new Room("w");

        grid[1][4] = new Room("m");
        grid[1][4].enemies.add(new Enemy("Giant Skeleton", 100, 10));

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
        grid[3][4].enemies.add(new Enemy("Minotaur", 200, 15));

        grid[4][0] = new Room("t");
        grid[4][1] = new Room(" ");
        grid[4][2] = new Room(" ");
        grid[4][3] = new Room("w");
        grid[4][4] = new Room("l", "exit_key");
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void gameLoop() {
        // Get Input from user
        String input = Main.display.readLine("Command: ");
        Main.display.printLine("player", input);
        String[] args = input.split(" ");


        switch (args[0]) {
            case "move":
                move(args[1]);
                break;
            case "quit":
                gameOver = true;
                break;
            case "attack":
                attack(grid[yPos][xPos].enemies, args[1]);
                break;
            default:
                Main.display.printLine("system", "invalid command");
        }

        // After Command Actions
        Room currentRoom = grid[yPos][xPos];
        ArrayList<Enemy> enemies = currentRoom.enemies;
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            Main.player.takeDamage(enemy.damage);
        }

        // End Game if player is dead
        if(Main.player.health == 0){
            Main.display.printLine("system","You have died");
            gameOver = true;
        }
    }

    private void move(String direction) {
        int offsetX = 0;
        int offsetY = 0;
        switch (direction) {
            case "r":
                offsetX += 1;
                break;
            case "l":
                offsetX -= 1;
                break;
            case "u":
                offsetY -= 1;
                break;
            case "d":
                offsetY += 1;
                break;
        }

        // Make sure the player is in bounds
        if (xPos + offsetX > 4 || xPos + offsetX < 0) {
            Main.display.printLine("system", "error out of bounds");
            return;
        }

        if (yPos + offsetY > 4 || yPos + offsetY < 0) {
            Main.display.printLine("system", "error out of bounds");
            return;
        }

        Room currentRoom = grid[yPos + offsetY][xPos + offsetX];
        switch (currentRoom.code) {
            case "w":
                Main.display.printLine("system", "there is a wall there");
                break;
            case "l":
                // Try to unlock the room
                currentRoom.unlock(Main.player.inventory);
                if(currentRoom.isLocked()) {
                    Main.display.printLine("system", "that room is currently locked");
                }
                break;
            default:
                xPos += offsetX;
                yPos += offsetY;
        }
    }

    private void attack(ArrayList<Enemy> enemies, String targetName) {
        Enemy target = null;
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).name.equals(targetName)) {
                target = enemies.get(i);
                break;
            }
        }

        if (target == null) {
            Main.display.printLine("system", "invalid target");
            return;
        }


        target.takeDamage(Main.player.damage);
        Main.display.printLine(
                "system",
                String.format("the %s has taken %d damage", target.name, Main.player.damage)
        );

        if (target.health == 0) {
            enemies.remove(target);
            Main.display.printLine("system", String.format("the %s has died", targetName));
        }

    }
}
