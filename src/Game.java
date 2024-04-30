import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private boolean gameOver;

    private Player player;
    private int xPos;
    private int yPos;

    private ArrayList<String> display = new ArrayList<>();
    private int displayMaxLines = 26;

    private Room[][] grid;

    Game(Player player) {
        display.add("[system] What is your name?");
        this.player = player;

        this.gameOver = false;
        this.xPos = 0;
        this.yPos = 2;

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

    public void drawGrid(Scanner scanner) {
        // │ ─ ┌ ┐ └ ┘ ┬ ┴ ├ ┤ ┼

        // Clear Screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Render Components
        String[] map = displayMap();
        String[] inventory = displayInventory();
        String[] commands = displayCommands();

        ArrayList<String> column = new ArrayList<>(map.length + inventory.length + commands.length);
        column.addAll(List.of(map));
        column.addAll(List.of(commands));
        column.addAll(List.of(inventory));

        String[] story = displayStory();
        String[] room = displayRoom();

        // Display Components
        System.out.println("┌─────────────────────────────────────────────────────The Dungeon───────────────────────────────────────────────────────┐");

        for (int i = 0; i < column.size(); i++) {
            System.out.print('│');
            System.out.print(column.get(i));
            System.out.print(story[i]);
            System.out.print(room[i]);
            System.out.println('│');
        }

        System.out.println("└───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");

        /*
        System.out.println("┌─────────────────────────────────────────────────────The Dungeon───────────────────────────────────────────────────────┐");
        System.out.println("│┌─────┬─────┬─Map─┬─────┬─────┐┌────────────────────────────────────────────────────────┐┌────────────Stats───────────┐│");
        System.out.println("││     │     │     │     │     ││ [system] Welcome to the dungeon Rajeev                 ││ Player:                    ││");
        System.out.println("│├─────┼─────┼─────┼─────┼─────┤│ [player] move up                                       ││  - Health: 10/10           ││");
        System.out.println("││     │  w  │  l  │  w  │  m  ││ [system] you encounter a bat                           ││  - Damage: 10              ││");
        System.out.println("│├─────┼─────┼─────┼─────┼─────┤│ [player] attack bat                                    ││                            ││");
        System.out.println("││  x  │  w  │     │  w  │  w  ││ [system] bat took 10 dmg                               ││ Room:                      ││");
        System.out.println("│├─────┼─────┼─────┼─────┼─────┤│ [system] the bat has died                              ││  - Bat: 0/10               ││");
        System.out.println("││  w  │  w  │     │     │  b  ││                                                        ││                            ││");
        System.out.println("│├─────┼─────┼─────┼─────┼─────┤│                                                        ││                            ││");
        System.out.println("││  l  │     │     │  w  │  l  ││                                                        ││                            ││");
        System.out.println("│└─────┴─────┴─────┴─────┴─────┘│                                                        ││                            ││");
        System.out.println("│┌───────────Commands──────────┐│                                                        ││                            ││");
        System.out.println("││move <direction>             ││                                                        ││                            ││");
        System.out.println("││use  <item>                  ││                                                        ││                            ││");
        System.out.println("││loot <target>                ││                                                        ││                            ││");
        System.out.println("││attack <target>              ││                                                        ││                            ││");
        System.out.println("││quit                         ││                                                        ││                            ││");
        System.out.println("│└─────────────────────────────┘│                                                        ││                            ││");
        System.out.println("│┌──────────Inventory──────────┐│                                                        ││                            ││");
        System.out.println("││ - basic potion 2x           ││                                                        ││                            ││");
        System.out.println("││                             ││                                                        ││                            ││");
        System.out.println("││                             ││                                                        ││                            ││");
        System.out.println("││                             ││                                                        ││                            ││");
        System.out.println("││                             ││                                                        ││                            ││");
        System.out.println("││                             ││                                                        ││                            ││");
        System.out.println("││                             ││                                                        ││                            ││");
        System.out.println("││                             ││                                                        ││                            ││");
        System.out.println("│└─────────────────────────────┘└────────────────────────────────────────────────────────┘└────────────────────────────┘│");
        System.out.println("└───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        */
    }

    public String[] displayMap(){
        // Make it easier to map the position
        String[] mapCodes = new String[25];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mapCodes[j + i * 5 ] = (grid[i][j].code);
            }
        }

        // Player pos
        mapCodes[yPos*5+xPos] = "x";

        // Construct the map
        String[] map = new String[11];
        map[0] = "┌─────┬─────┬─Map─┬─────┬─────┐";
        map[1] = String.format("│  %s  │  %s  │  %s  │  %s  │  %s  │", mapCodes[0], mapCodes[1], mapCodes[2], mapCodes[3], mapCodes[4]);
        map[2] = "├─────┼─────┼─────┼─────┼─────┤";
        map[3] = String.format("│  %s  │  %s  │  %s  │  %s  │  %s  │", mapCodes[5], mapCodes[6], mapCodes[7], mapCodes[8], mapCodes[9]);
        map[4] = "├─────┼─────┼─────┼─────┼─────┤";
        map[5] = String.format("│  %s  │  %s  │  %s  │  %s  │  %s  │", mapCodes[10], mapCodes[11], mapCodes[12], mapCodes[13], mapCodes[14]);
        map[6] = "├─────┼─────┼─────┼─────┼─────┤";
        map[7] = String.format("│  %s  │  %s  │  %s  │  %s  │  %s  │", mapCodes[15], mapCodes[16], mapCodes[17], mapCodes[18], mapCodes[19]);
        map[8] = "├─────┼─────┼─────┼─────┼─────┤";
        map[9] = String.format("│  %s  │  %s  │  %s  │  %s  │  %s  │", mapCodes[20], mapCodes[21], mapCodes[22], mapCodes[23], mapCodes[24]);
        map[10] = "└─────┴─────┴─────┴─────┴─────┘";
        return map;
    }

    public String[] displayStory(){
        String[] story = new String[28];
        List<String> history;
        if (display.size() > displayMaxLines){
            history = display.subList(0, display.size()-1);
        } else {
            history = new ArrayList<>(display);
            for(int i = display.size(); i < displayMaxLines; i++) {
                history.add("");
            }
        }

        story[0] = "┌────────────────────────────────────────────────────────┐";
        story[27] = "└────────────────────────────────────────────────────────┘";

        for (int i = 0; i < displayMaxLines; i++) {
            story[i+1] = String.format("│%-56s│", history.get(i));
        }

        return story;
    }

    public String[] displayCommands(){
        String[] commands = new String[7];
        commands[0] = "┌───────────Commands──────────┐";
        commands[6] = "└─────────────────────────────┘";

        commands[1] = String.format("│%-29s│","move <direction>");
        commands[2] = String.format("│%-29s│","use <item>");
        commands[3] = String.format("│%-29s│","loot <target>");
        commands[4] = String.format("│%-29s│","attack <target>");
        commands[5] = String.format("│%-29s│","quit");

        return commands;
    }

    public String[] displayInventory(){
        String[] inventory = new String[10];
        inventory[0] = "┌──────────Inventory──────────┐";
        inventory[9] = "└─────────────────────────────┘";

        for (int i = 1; i < player.inventory.size(); i++) {
            inventory[i] = String.format("│ - %-26s│", player.inventory.get(i));
        }
        if(player.inventory.size() < 8){
            for(int i = player.inventory.size(); i < 8; i++){
                inventory[i + 1] = String.format("│%-29s│", "");
            }
        }

        return inventory;
    }

    public String[] displayRoom(){
        String[] room = new String[28];
        room[0] = "┌─────────────Room───────────┐";
        room[27] = "└────────────────────────────┘";

        room[1] = String.format("│%-28s│", "Player:");
        room[2] = String.format("│%-28s│", " - Health: " + player.getHealth());
        room[3] = String.format("│%-28s│", " - Damage: " + player.getDamage());

        Enemy[] enemies = grid[yPos][xPos].getEnemies();
        for (int i = 0; i < enemies.length; i++){
            room[3 + (i * 4)] = String.format("│%-28s│","");
            room[3 + (i * 4 + 1)] = String.format("│%-28s│", enemies[i].getName() + ":");
            room[3 + (i * 4 + 2)] = String.format("│%-28s│", " - Health: " + enemies[i].getHealth());
            room[3 + (i * 4 + 3)] = String.format("│%-28s│", " - Damage: " + enemies[i].getDamage());
        }

        // Padding
        for(int i = 3 + enemies.length; i < room.length; i++){
            if (room[i] == null){
                room[i] = String.format("│%-28s│","");
            }
        }

        return room;
    }

    public void gameLoop(Scanner scanner){
        System.out.print("Command: ");
        String input = scanner.nextLine();
        display.add("[player] " +input);
        String[] args = input.split(" ");
        String command = args[0];
        switch (command) {
            case "quit":
                this.gameOver = true;
                break;
            case "move":
                String direction = args[1];
                this.move(direction);
                break;
        }
    }

    public void move(String direction){
        System.out.println("moving " + direction);
        switch (direction) {
            case "n":
                this.yPos -= 1;
                if (this.yPos < 0) this.yPos = 0;
                if (this.yPos >= 4) this.yPos = 4;
                break;
            case "s":
                this.yPos += 1;
                if (this.yPos < 0) this.yPos = 0;
                if (this.yPos >= 4) this.yPos = 4;
                break;
            case "e":
                this.xPos += 1;
                if (this.xPos < 0) this.xPos = 0;
                if (this.xPos >= 4) this.xPos = 4;
                break;
            case "w":
                this.xPos -= 1;
                if (this.xPos < 0) this.xPos = 0;
                if (this.xPos >= 4) this.xPos = 4;
                break;
            default:
                System.out.println("invalid direction");
        }
    }
}
