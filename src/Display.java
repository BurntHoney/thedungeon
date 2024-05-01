import java.util.ArrayList;
import java.util.Scanner;

public class Display {
    private final int MAX_BUFFER_SIZE = 26;
    private final Scanner input = new Scanner(System.in);

    private final ArrayList<String> buffer = new ArrayList<>(MAX_BUFFER_SIZE);

    final String DUNGEON_HEADER = "┌" + "─".repeat(87) + "TheDungeon" + "─".repeat(88) +"┐";;
    final String DUNGEON_FOOTER = "└" + "─".repeat(185) + "┘";

    final String MAP_HEADER = "┌─────┬─────┬─Map─┬─────┬─────┐";
    final String MAP_CENTER = "├─────┼─────┼─────┼─────┼─────┤";
    final String MAP_FOOTER = "└─────┴─────┴─────┴─────┴─────┘";

    final String COMMANDS_HEADER  = "┌───────────Commands──────────┐";
    final String COMMANDS_FOOTER  = "└─────────────────────────────┘";

    final String INVENTORY_HEADER  = "┌──────────Inventory──────────┐";
    final String INVENTORY_PADDING = "│                             │";
    final String INVENTORY_FOOTER  = "└─────────────────────────────┘";

    // Too large so we generate it at the start and leave it
    final String CONSOLE_HEADER = "┌" + "─".repeat(121) + "┐";
    final String CONSOLE_FOOTER = "└" + "─".repeat(121) + "┘";
    final String CONSOLE_PADDING = "│" + " ".repeat(121) + "│";

    final String ROOM_HEADER  = "┌─────────────Room────────────┐";
    final String ROOM_PADDING = "│                             │";
    final String ROOM_FOOTER  = "└─────────────────────────────┘";

    public void display() {
        // clear terminal: source: https://www.javatpoint.com/how-to-clear-screen-in-java
        System.out.print("\033[H\033[2J");
        System.out.flush();

        final int DISPLAY_HEIGHT = 28;

        // Group Components By Columns
        ArrayList<String> infoColumn = new ArrayList<>();
        infoColumn.addAll(renderGrid());
        infoColumn.addAll(renderCommands());
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

        for (int y = 0; y < 5; y++) {
            lines.add(String.format(
                    "│  %s  │  %s  │  %s  │  %s  │  %s  │",
                    mapCodes.get(5 * y),
                    mapCodes.get(5 * y + 1),
                    mapCodes.get(5 * y + 2),
                    mapCodes.get(5 * y + 3),
                    mapCodes.get(5 * y + 4)
            ));
            lines.add(MAP_CENTER);
        }

        // Get rid of the last map center
        lines.removeLast();
        lines.addFirst(MAP_HEADER);
        lines.addLast(MAP_FOOTER);

        return lines;
    }

    private ArrayList<String> renderCommands() {
        ArrayList<String> lines = new ArrayList<>();

        lines.add(String.format("│%-29s│", "move <direction>"));
        lines.add(String.format("│%-29s│", "use <item>"));
        lines.add(String.format("│%-29s│", "loot <target>"));
        lines.add(String.format("│%-29s│", "attack <target>"));
        lines.add(String.format("│%-29s│", "quit"));

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
            lines.add(String.format("│ - %-26s│", items.get(i) + " x" + count.get(i)));
        }

        // Padding
        for (int i = lines.size(); i < 8; i++)
            lines.add(INVENTORY_PADDING);

        lines.addFirst(INVENTORY_HEADER);
        lines.addLast(INVENTORY_FOOTER);
        return lines;
    }

    private ArrayList<String> renderConsole() {
        ArrayList<String> lines = new ArrayList<>(buffer);
        lines.replaceAll(s -> String.format("│%-121s│", s));

        // Pad the end of the lines
        for (int i = lines.size(); i < MAX_BUFFER_SIZE; i++)
            lines.add(CONSOLE_PADDING);

        lines.addFirst(CONSOLE_HEADER);
        lines.addLast(CONSOLE_FOOTER);
        return lines;
    }

    private ArrayList<String> renderRoom() {
        ArrayList<String> lines = new ArrayList<>();

        lines.add(String.format("│%-29s│", "player: "));
        lines.add(String.format("│%-29s│", " - health: " + Main.player.health));
        lines.add(String.format("│%-29s│", " - damage: " + Main.player.damage));

        ArrayList<Enemy> enemies = Main.game.grid[Main.game.yPos][Main.game.xPos].enemies;
        for (int i = 0; i < enemies.size(); i++) {
            lines.add(ROOM_PADDING);
            lines.add(String.format("│%-29s│", enemies.get(i).name + ":"));
            lines.add(String.format("│%-29s│", " - health: " + enemies.get(i).health));
        }

        // Pad the end of the lines
        for (int i = lines.size(); i < MAX_BUFFER_SIZE; i++)
            lines.add(ROOM_PADDING);

        lines.addFirst(ROOM_HEADER);
        lines.addLast(ROOM_FOOTER);
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
}
