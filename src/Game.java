import java.util.ArrayList;

public class Game {
    private boolean gameOver = false;
    private boolean inBattle = false;

    public int xPos = 0;
    public int yPos = 2;

    public Room[][] grid;

    Game() {


        // Enemies

        Enemy bat = new Enemy("bat", 6, 1);
        bat.inventory.add("lesser_potion");

        Enemy goblin = new Enemy("goblin", 10, 2);
        goblin.inventory.add("greater_potion");

        Enemy skeleton = new Enemy("skeleton", 15, 3);
        skeleton.inventory.add("milk");

        Enemy giantSkeleton = new Enemy("Giant Skeleton", 20, 5);
        giantSkeleton.inventory.add("supreme_milk");

        Enemy minotaur = new Enemy("minotaur", 5, 1);
        minotaur.inventory.add("door_key");

        Enemy dragon = new Enemy("dragon", 8, 1);
        dragon.inventory.add("exit_key");


        // Initialize Room
        this.grid = new Room[5][5];

        grid[0][0] = new Room(" ");
        grid[0][0].enemies.add(bat);

        grid[0][1] = new Room(" ");
        grid[0][2] = new Room(" ");
        grid[0][3] = new Room(" ");
        grid[0][4] = new Room(" ");

        grid[1][0] = new Room(" ");
        grid[1][1] = new Room("w");
        grid[1][2] = new Room("l", "door_key");
        grid[1][3] = new Room("w");

        grid[1][4] = new Room("m");
        grid[1][4].enemies.add(minotaur);

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
        grid[3][4].enemies.add(dragon);

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
        Main.display.console.log("player", input, "spam");

        boolean success = routeCommand(input);
        if (!success) return; // Let the player retype the command on failure

        // After Command Actions
        Room currentRoom = grid[yPos][xPos];
        ArrayList<Enemy> enemies = currentRoom.enemies;

        if(!enemies.isEmpty() && !inBattle) {
            inBattle = true;
            Main.display.console.log("system", "you encounter some enemies", "info");
        } else {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                Main.player.takeDamage(enemy.damage);
            }

            // End Game if player is dead
            if (Main.player.health == 0) {
                Main.display.console.log("system", "you have died", "error");
                gameOver = true;
            }
        }

        // Check if the win condition is met
        if(xPos == 4 && yPos == 4) {
            Main.display.console.log("system", "you have finally escaped the dungeon", "info");
            gameOver = true;
        }
    }

    private boolean routeCommand(String input) {
        String[] args = input.split(" ", 2);

        switch (args[0]) {
            case "move":
                if(args.length == 1) {
                    Main.display.console.log("system", "direction not provided", "error");
                    return false;
                }
                return move(args[1]);
            case "quit":
                gameOver = true;
                return true;
            case "attack":
                if(args.length == 1) {
                    Main.display.console.log("system", "target not provided", "error");
                    return false;
                }
                return attack(grid[yPos][xPos].enemies, args[1]);
            case "use":
                if(args.length == 1) {
                    Main.display.console.log("system", "item not provided", "error");
                    return false;
                }
                return Main.player.useItem(args[1]);
            default:
                Main.display.console.log("system", "invalid command", "error");
                return false;
        }
    }

    private boolean move(String direction) {
        if(inBattle) {
            Main.display.console.log("system", "you cannot flee from battle", "error");
            return false;
        }

        int offsetX = 0;
        int offsetY = 0;
        switch (direction) {
            case "e":
                offsetX += 1;
                break;
            case "w":
                offsetX -= 1;
                break;
            case "n":
                offsetY -= 1;
                break;
            case "s":
                offsetY += 1;
                break;
            default:
                Main.display.console.log("system", "invalid direction", "error");
                return false;
        }

        // Make sure the player is in bounds
        if (xPos + offsetX > 4 || xPos + offsetX < 0) {
            Main.display.console.log("system", "error out of bounds", "error");
            return false;
        }

        if (yPos + offsetY > 4 || yPos + offsetY < 0) {
            Main.display.console.log("system", "error out of bounds", "error");
            return false;
        }

        Room currentRoom = grid[yPos + offsetY][xPos + offsetX];
        switch (currentRoom.code) {
            case "w":
                Main.display.console.log("system", "there is a wall there", "error");
                return false;
            case "l":
                // Try to unlock the room
                currentRoom.unlock(Main.player.inventory);
                if(currentRoom.isLocked()) {
                    Main.display.console.log("system", "that room is currently locked", "warning");
                    return false;
                }
                Main.display.console.log("system", "you unlock the room", "info");
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
            Main.display.console.log("system", "invalid target", "error");
            return false;
        }


        target.takeDamage(Main.player.damage);

        Main.display.console.log(
                "system",
                String.format("the %s has taken %d damage", target.name, Main.player.damage),
                "info"
        );

        if (target.health == 0) {
            Main.player.inventory.addAll(target.inventory);
            enemies.remove(target);
            Main.display.console.log("system", String.format("the %s has died", targetName), "info");

            // If the current room is a boss room we mark the room as cleared
            grid[yPos][xPos].code = " ";
        }

        if (enemies.isEmpty()) {
            inBattle = false;
        }
        return true;
    }
}
