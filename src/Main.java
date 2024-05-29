import java.util.ArrayList;

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

        Component component = new Component();
        component.setBorder();
        component.setColumnComponent();
        component.addChild(mapComponent.getComponent());
        component.addChild(commandsComponent.getComponent());
        component.addChild(inventoryComponent.getComponent());

        Dimension mapDimension = component.computeDimension();
        System.out.println(mapDimension.minHeight);
        System.out.println(mapDimension.minWidth);

        for (String line : component.draw(mapDimension.minWidth, mapDimension.minHeight)) {
            System.out.println(line);
        }
    }
}
