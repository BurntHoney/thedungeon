
public class Main {
    public static Dungeon dungeon = new Dungeon();

    public static Character player = new Character();

    // Enemies
    public static Character dragon;
    public static Character minotaur;

    // Component's
    public static MapComponent mapComponent = new MapComponent();
    public static CommandsComponent commandsComponent = new CommandsComponent();
    public static InventoryComponent inventoryComponent = new InventoryComponent();
    public static ConsoleComponent consoleComponent = new ConsoleComponent();

    public static void main(String[] args) {

        // player2 = new Character();
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

        player.inventory.add(new Item("health potion"));
        player.inventory.add(new Item("health potion"));

        Component window = new Component();
        window.setTitle("TheDungeon");
        window.setFixedWidth();
        window.setFixedHeight();
        window.setMinWidth(150);
        window.setMinHeight(30);
        window.setBorder();
        window.setRowComponent();

        Component component = new Component();
        component.setBorder();
        component.setColumnComponent();
        component.setFixedWidth();
        component.addChild(mapComponent.getComponent());
        component.addChild(commandsComponent.getComponent());
        component.addChild(inventoryComponent.getComponent());

        window.addChild(component);
        window.addChild(consoleComponent.getComponent());
        consoleComponent.log("info", "system", "what is your name prisoner?");
        consoleComponent.log("debug", "player", "the name's burnt honey");

        Dimension windowDimension = window.computeDimension();

        for (String line : window.draw(windowDimension.minWidth, windowDimension.minHeight)) {
            System.out.println(line);
        }
    }
}
