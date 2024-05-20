public class Main {
    public static Display display = new Display();
    public static Game game = new Game();
    public static Player player = new Player();

    public static void main(String[] args) {
        Component map = new Component();
        map.setTitle("map");
        map.setBorder(true);

        map.writeBuffer("command");
        map.writeBuffer("more");
        map.writeBuffer("something");

        Component inventory = new Component();
        inventory.setTitle("inventory is the best");
        inventory.setBorder(true);
        inventory.writeBuffer("potion x2");
        inventory.writeBuffer("bone x3");

        Component inventory2 = new Component();
        inventory2.setTitle("inventory is the west");
        inventory2.setBorder(true);
        inventory2.setFixedDimension(false, true);
        inventory2.writeBuffer("potion x2");
        inventory2.writeBuffer("bone x3");
        inventory2.writeBuffer("line");

        ColumnComponent column = new ColumnComponent(new Component[] { map, inventory, inventory2 });
        column.setTitle("column1");
        column.setBorders(true);

        int[] dimensions = column.calculateDimensions();
        System.out.printf("%d %d %d %d\n", dimensions[0], dimensions[1], dimensions[2], dimensions[3]);
        for (String line : column.constructComponent(100, 40)) {
            System.out.println(line);
        }

        // Main.display.console.log("system", "What is your name?", "info");
        // Main.display.display();

        // String playerName = display.readLine("Name: ");
        // Main.display.console.log("player", playerName, "spam");
        // Main.display.console.log("system", String.format("welcome to the dungeon %s",
        // playerName), "info");

        // // Game Loop
        // while (!game.getGameOver()) {
        // display.display();
        // game.gameLoop();
        // }

        // display.close();
    }
}
