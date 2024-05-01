import java.util.ArrayList;
import java.util.Scanner;

public class Display {
    private final int MAX_BUFFER_SIZE = 26;
    private final Scanner input = new Scanner(System.in);

    private final ArrayList<String> buffer = new ArrayList<>(MAX_BUFFER_SIZE);

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
        System.out.println("┌──────────────────────────────────────────────────────TheDungeon───────────────────────────────────────────────────────┐");

        for (int i = 0; i < DISPLAY_HEIGHT; i++)
            System.out.println("│" + infoColumn.get(i) + consoleColumn.get(i) + roomColumn.get(i) + "│");

        System.out.println("└───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
    }

    private ArrayList<String> renderGrid() {
        ArrayList<String> mapCodes = new ArrayList<>();

        for (int y = 0; y < 5; y++)
            for (int x = 0; x < 5; x++)
                mapCodes.add(Main.game.grid[y][x].code);

        mapCodes.set(Main.game.yPos * 5 + Main.game.xPos, "x");

        ArrayList<String> lines = new ArrayList<>();

        lines.add("┌─────┬─────┬─Map─┬─────┬─────┐");
        lines.add(String.format(
                "│  %s  │  %s  │  %s  │  %s  │  %s  │",
                mapCodes.get(0), mapCodes.get(1), mapCodes.get(2), mapCodes.get(3), mapCodes.get(4)
        ));
        lines.add("├─────┼─────┼─────┼─────┼─────┤");
        lines.add(String.format(
                "│  %s  │  %s  │  %s  │  %s  │  %s  │",
                mapCodes.get(5), mapCodes.get(6), mapCodes.get(7), mapCodes.get(8), mapCodes.get(9)
        ));
        lines.add("├─────┼─────┼─────┼─────┼─────┤");
        lines.add(String.format(
                "│  %s  │  %s  │  %s  │  %s  │  %s  │",
                mapCodes.get(10), mapCodes.get(11), mapCodes.get(12), mapCodes.get(13), mapCodes.get(14)
        ));
        lines.add("├─────┼─────┼─────┼─────┼─────┤");
        lines.add(String.format(
                "│  %s  │  %s  │  %s  │  %s  │  %s  │",
                mapCodes.get(15), mapCodes.get(16), mapCodes.get(17), mapCodes.get(18), mapCodes.get(19)
        ));
        lines.add("├─────┼─────┼─────┼─────┼─────┤");
        lines.add(String.format(
                "│  %s  │  %s  │  %s  │  %s  │  %s  │",
                mapCodes.get(20), mapCodes.get(21), mapCodes.get(22), mapCodes.get(23), mapCodes.get(24)
        ));
        lines.add("└─────┴─────┴─────┴─────┴─────┘");

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
        lines.addFirst("┌───────────Commands──────────┐");
        lines.addLast("└─────────────────────────────┘");
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
            lines.add(String.format("│%-29s│", " - " + items.get(i) + " x" + count.get(i)));
        }

        // Padding
        for (int y = lines.size(); y < 8; y++) {
            lines.add(String.format("│%-29s│", ""));
        }

        lines.addFirst("┌──────────Inventory──────────┐");
        lines.addLast("└─────────────────────────────┘");
        return lines;
    }

    private ArrayList<String> renderConsole() {
        ArrayList<String> lines = new ArrayList<>(buffer);
        lines.replaceAll(s -> String.format("│%-56s│", s));

        // Pad the end of the lines
        for (int i = lines.size(); i < MAX_BUFFER_SIZE; i++) lines.add(String.format("│%-56s│", ""));

        lines.addFirst("┌────────────────────────Console─────────────────────────┐");
        lines.addLast("└────────────────────────────────────────────────────────┘");
        return lines;
    }

    private ArrayList<String> renderRoom() {
        ArrayList<String> lines = new ArrayList<>();

        lines.add(String.format("│%-28s│", "player: "));
        lines.add(String.format("│%-28s│", " - health: " + Main.player.health));
        lines.add(String.format("│%-28s│", " - damage: " + Main.player.damage));

        ArrayList<Enemy> enemies = Main.game.grid[Main.game.yPos][Main.game.xPos].enemies;
        for (int i = 0; i < enemies.size(); i++) {
            lines.add(String.format("│%-28s│", ""));
            lines.add(String.format("│%-28s│", enemies.get(i).name + ":"));
            lines.add(String.format("│%-28s│", " - health: " + enemies.get(i).health));
        }

        // Pad the end of the lines
        for (int i = lines.size(); i < MAX_BUFFER_SIZE; i++)
            lines.add(String.format("│%-28s│", ""));

        lines.addFirst("┌─────────────Room───────────┐");
        lines.addLast("└────────────────────────────┘");
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
