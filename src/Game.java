import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
    private boolean gameOver = false;
    private boolean inBattle = false;

    public int xPos = 0;
    public int yPos = 2;

    public Room[][] grid;

    Game() {

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

        Enemy miniboss = new Enemy("Giant Skeleton", 8, 2);
        miniboss.inventory.add("door_key");
        grid[1][4].enemies.add(miniboss);

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
        Enemy boss = new Enemy("Minotaur", 8, 2);
        boss.inventory.add("exit_key");
        grid[3][4].enemies.add(boss);

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

        boolean success = routeCommand(input);
        if (!success) return; // Let the player retype the command on failure

        // After Command Actions
        Room currentRoom = grid[yPos][xPos];
        ArrayList<Enemy> enemies = currentRoom.enemies;

        if(!enemies.isEmpty() && !inBattle) {
            inBattle = true;
            Main.display.printLine("system", "you encounter some enemies");
        } else {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                Main.player.takeDamage(enemy.damage);
            }

            // End Game if player is dead
            if (Main.player.health == 0) {
                Main.display.printLine("system", "You have died");
                gameOver = true;
            }
        }

        // Check if the win condition is met
        if(xPos == 4 && yPos == 4) {
            Main.display.printLine("system", "You have finally escaped the dungeon :)");
            gameOver = true;
        }
    }

    private boolean routeCommand(String input) {
        String[] args = input.split(" ", 2);

        switch (args[0]) {
            case "move":
                if(args.length == 1) {
                    Main.display.printLine("system", "direction not provided");
                    return false;
                }
                return move(args[1]);
            case "quit":
                gameOver = true;
                return true;
            case "attack":
                if(args.length == 1) {
                    Main.display.printLine("system", "target not provided");
                    return false;
                }
                return attack(grid[yPos][xPos].enemies, args[1]);
            default:
                Main.display.printLine("system", "invalid command");
                return false;
        }
    }

    private boolean move(String direction) {
        if(inBattle) {
            Main.display.printLine("system", "you cannot flee from battle");
            return false;
        }

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
            default:
                Main.display.printLine("system", "invalid direction");
                return false;
        }

        // Make sure the player is in bounds
        if (xPos + offsetX > 4 || xPos + offsetX < 0) {
            Main.display.printLine("system", "error out of bounds");
            return false;
        }

        if (yPos + offsetY > 4 || yPos + offsetY < 0) {
            Main.display.printLine("system", "error out of bounds");
            return false;
        }

        Room currentRoom = grid[yPos + offsetY][xPos + offsetX];
        switch (currentRoom.code) {
            case "w":
                Main.display.printLine("system", "there is a wall there");
                return false;
            case "l":
                // Try to unlock the room
                currentRoom.unlock(Main.player.inventory);
                if(currentRoom.isLocked()) {
                    Main.display.printLine("system", "that room is currently locked");
                    return false;
                }
                Main.display.printLine("system", "you unlock the room");
                break;
        }
        xPos += offsetX;
        yPos += offsetY;
        return true;
    }

    private boolean attack(ArrayList<Enemy> enemies, String targetName) {
        Enemy target = null;
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).name.equals(targetName)) {
                target = enemies.get(i);
                break;
            }
        }

        if (target == null) {
            Main.display.printLine("system", "invalid target");
            return false;
        }


        target.takeDamage(Main.player.damage);
        Main.display.printLine(
                "system",
                String.format("the %s has taken %d damage", target.name, Main.player.damage)
        );

        if (target.health == 0) {
            Main.player.inventory.addAll(target.inventory);
            enemies.remove(target);
            Main.display.printLine("system", String.format("the %s has died", targetName));

            // If the current room is a boss room we mark the room as cleared
            grid[yPos][xPos].code = " ";
        }

        if (enemies.isEmpty()) {
            inBattle = false;
        }
        return true;
    }
}
