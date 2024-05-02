import java.util.ArrayList;
import java.util.Scanner;

public class Display {
    private final int MAX_BUFFER_SIZE = 42;
    private final Scanner input = new Scanner(System.in);

    private final ArrayList<String> buffer = new ArrayList<>(MAX_BUFFER_SIZE);

    static final String DUNGEON_HEADER = "┌" + "─".repeat(87) + "TheDungeon" + "─".repeat(88) +"┐";;
    static final String DUNGEON_FOOTER = "└" + "─".repeat(185) + "┘";

    static final String COMMANDS_HEADER  = "┌─────────────────────Commands────────────────────┐";
    static final String COMMANDS_FOOTER  = "└─────────────────────────────────────────────────┘";

    static final String STATS_HEADER  = "┌────────────────────────Stats────────────────────┐";
    static final String STATS_FOOTER  = "└─────────────────────────────────────────────────┘";

    static final String INVENTORY_HEADER  = "┌─────────────────────Inventory───────────────────┐";
    static final String INVENTORY_PADDING = "│                                                 │";
    static final String INVENTORY_FOOTER  = "└─────────────────────────────────────────────────┘";

    // Too large so we generate it at the start and leave it
    static final String CONSOLE_HEADER = "┌" + "─".repeat(101) + "┐";
    static final String CONSOLE_FOOTER = "└" + "─".repeat(101) + "┘";
    static final String CONSOLE_PADDING = "│" + " ".repeat(101) + "│";

    static final String ROOM_HEADER  = "┌─────────────Room────────────┐";
    static final String ROOM_PADDING = "│                             │";
    static final String ROOM_FOOTER  = "└─────────────────────────────┘";

    public void display() {
        // clear terminal: source: https://www.javatpoint.com/how-to-clear-screen-in-java
        System.out.print("\033[H\033[2J");
        System.out.flush();

        final int DISPLAY_HEIGHT = 44;

        // Group Components By Columns
        ArrayList<String> infoColumn = new ArrayList<>();
        infoColumn.addAll(renderGrid());
        infoColumn.addAll(renderCommands());
        infoColumn.addAll(renderStats());
        infoColumn.addAll(renderInventory());

        ArrayList<String> consoleColumn = renderConsole();
        ArrayList<String> roomColumn = renderRoom();

        // Print Component's Line By Line
        System.out.println(DUNGEON_HEADER);

        for (int i = 0; i < DISPLAY_HEIGHT; i++)
            System.out.println("│" + infoColumn.get(i) + consoleColumn.get(i) + roomColumn.get(i) + "│");

        System.out.println(DUNGEON_FOOTER);
    }

    private ArrayList<String> renderGrid() {
        ArrayList<String> mapCodes = new ArrayList<>(25);

        // Make it easier to place the player
        for (int y = 0; y < 5; y++)
            for (int x = 0; x < 5; x++)
                mapCodes.add(Main.game.grid[y][x].code);

        mapCodes.set(Main.game.yPos * 5 + Main.game.xPos, "x");

        ArrayList<String> lines = new ArrayList<>();

        lines.add("┌────────────────────────Map──────────────────────┐");
        lines.add("│┌─────┬─────┬─────┬─────┬─────┐┌─────Legend─────┐│");

        lines.add(String.format("││  %s  │  %s  │  %s  │  %s  │  %s  ││ x = player     ││",
                mapCodes.get(0),
                mapCodes.get(1),
                mapCodes.get(2),
                mapCodes.get(3),
                mapCodes.get(4)
        ));

        lines.add("│├─────┼─────┼─────┼─────┼─────┤│ w = wall       ││");

        lines.add(String.format("││  %s  │  %s  │  %s  │  %s  │  %s  ││ l = locked     ││",
                mapCodes.get(5),
                mapCodes.get(6),
                mapCodes.get(7),
                mapCodes.get(8),
                mapCodes.get(9)
        ));
        lines.add("│├─────┼─────┼─────┼─────┼─────┤│ t = treasure   ││");
        lines.add(String.format("││  %s  │  %s  │  %s  │  %s  │  %s  ││ m = mini boss  ││",
                mapCodes.get(10),
                mapCodes.get(11),
                mapCodes.get(12),
                mapCodes.get(13),
                mapCodes.get(14)
        ));

        lines.add("│├─────┼─────┼─────┼─────┼─────┤│ b = boss       ││");

        lines.add(String.format("││  %s  │  %s  │  %s  │  %s  │  %s  ││                ││",
                mapCodes.get(15),
                mapCodes.get(16),
                mapCodes.get(17),
                mapCodes.get(18),
                mapCodes.get(19)
        ));

        lines.add("│├─────┼─────┼─────┼─────┼─────┤│                ││");

        lines.add(String.format("││  %s  │  %s  │  %s  │  %s  │  %s  ││                ││",
                mapCodes.get(20),
                mapCodes.get(21),
                mapCodes.get(22),
                mapCodes.get(23),
                mapCodes.get(24)
        ));

        lines.add("│└─────┴─────┴─────┴─────┴─────┘└────────────────┘│");
        lines.add("└─────────────────────────────────────────────────┘");
//
//        for (int y = 0; y < 5; y++) {
//            lines.add(String.format(
//                    "│  %s  │  %s  │  %s  │  %s  │  %s  │",
//                    mapCodes.get(5 * y),
//                    mapCodes.get(5 * y + 1),
//                    mapCodes.get(5 * y + 2),
//                    mapCodes.get(5 * y + 3),
//                    mapCodes.get(5 * y + 4)
//            ));
//            lines.add(MAP_CENTER);
//        }
//
//        // Get rid of the last map center
//        lines.removeLast();
//        lines.addFirst(MAP_HEADER);
//        lines.addLast(MAP_FOOTER);

        return lines;
    }

    private ArrayList<String> renderCommands() {
        ArrayList<String> lines = new ArrayList<>();

        lines.add(String.format("│%-49s│", "move <direction>"));
        lines.add(String.format("│%-49s│", "use <item>"));
        lines.add(String.format("│%-49s│", "loot <target>"));
        lines.add(String.format("│%-49s│", "attack <target>"));
        lines.add(String.format("│%-49s│", "quit"));

        // Header and Footer
        lines.addFirst(COMMANDS_HEADER);
        lines.addLast(COMMANDS_FOOTER);
        return lines;
    }

    private ArrayList<String> renderInventory() {
        ArrayList<String> items = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();

        for (int i = 0; i < Main.player.inventory.size(); i++) {
            String item = Main.player.inventory.get(i);
            if (items.contains(item)){
                int index = items.indexOf(item);
                count.set(index, count.get(index) + 1);
                continue;
            }
            items.add(item);
            count.add(1);
        }

        ArrayList<String> lines = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            lines.add(String.format("│ - %-46s│", items.get(i) + " x" + count.get(i)));
        }


        // Padding
        for (int i = lines.size(); i < 19; i++)
            lines.add(INVENTORY_PADDING);

        lines.addFirst(INVENTORY_HEADER);
        lines.addLast(INVENTORY_FOOTER);
        return lines;
    }

    private ArrayList<String> renderConsole() {
        ArrayList<String> lines = new ArrayList<>(buffer);
        lines.replaceAll(s -> String.format("│%-101s│", s));

        // Pad the end of the lines
        for (int i = lines.size(); i < MAX_BUFFER_SIZE; i++)
            lines.add(CONSOLE_PADDING);

        lines.addFirst(CONSOLE_HEADER);
        lines.addLast(CONSOLE_FOOTER);
        return lines;
    }

    private ArrayList<String> renderRoom() {
        ArrayList<String> lines = new ArrayList<>();

        ArrayList<Enemy> enemies = Main.game.grid[Main.game.yPos][Main.game.xPos].enemies;
        for (int i = 0; i < enemies.size(); i++) {
            lines.add(String.format("│%-29s│", enemies.get(i).name + ": " + generateBar(enemies.get(i).health, enemies.get(i).maxHealth) + " " +  enemies.get(i).health));
            lines.add(ROOM_PADDING);
        }

        // Pad the end of the lines
        for (int i = lines.size(); i < MAX_BUFFER_SIZE; i++)
            lines.add(ROOM_PADDING);

        lines.addFirst(ROOM_HEADER);
        lines.addLast(ROOM_FOOTER);
        return lines;
    }

    private ArrayList<String> renderStats(){
        ArrayList<String> lines = new ArrayList<>();

        lines.add(String.format("│Health: %-41s│",
                generateBar(Main.player.health, Main.player.maxHealth) + " " + Main.player.health
        ));
        lines.addFirst(STATS_HEADER);
        lines.addLast(STATS_FOOTER);
        return lines;
    }


    public void printLine(String source, String text) {
        buffer.add(String.format("[%s]: %s", source, text));

        if (buffer.size() > MAX_BUFFER_SIZE) {
            buffer.removeFirst();
        }

        display();
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    public void close() {
        this.input.close();
    }

    private String generateBar(int value, int max){
        final int BAR_LENGTH = 15;
        int fill = Math.round((float) BAR_LENGTH * (Main.player.health) /Main.player.maxHealth);
        int blank = BAR_LENGTH - fill;
        return "[" + "■".repeat(fill) + "□".repeat(blank) + "]";
    }
}
