/**
 * The main display responsible to grouping all the components and showing it to the user
 */
public class Window {

    Component windowComponent;

    public static MapComponent mapComponent = new MapComponent();
    public static CommandsComponent commandsComponent = new CommandsComponent();
    public static InventoryComponent inventoryComponent =
        new InventoryComponent();
    public static ConsoleComponent consoleComponent = new ConsoleComponent();
    public static RoomComponent roomComponent = new RoomComponent();

    private int displayWidth = 150;
    private int displayHeight = 35;

    public Window() {
        this.windowComponent = new Component();
        this.windowComponent.setTitle("TheDungeon");
        this.windowComponent.setFixedWidth();
        this.windowComponent.setFixedHeight();
        this.windowComponent.setMinWidth(this.displayWidth);
        this.windowComponent.setMinHeight(this.displayHeight);
        this.windowComponent.setBorder();
        this.windowComponent.setRowComponent();
    }

    public void setDisplayHeight(int height) {
        if (height < 35) height = 35;
        this.displayHeight = height;
    }

    public void setDisplayWidth(int width) {
        if (width < 150) width = 150;
        this.displayWidth = width;
    }

    /**
     * Redraw's the entire display for the user to see
     */
    public void draw() {
        Component infoColumn = new Component();
        infoColumn.setBorder();
        infoColumn.setColumnComponent();
        infoColumn.setFixedWidth();
        infoColumn.addChild(mapComponent.getComponent());
        infoColumn.addChild(commandsComponent.getComponent());
        infoColumn.addChild(inventoryComponent.getComponent());

        Component gameColumn = new Component();
        gameColumn.setColumnComponent();
        gameColumn.addChild(roomComponent.getComponent());
        gameColumn.addChild(consoleComponent.getComponent());

        this.windowComponent.clearChildren();
        this.windowComponent.addChild(infoColumn);
        this.windowComponent.addChild(gameColumn);

        // Clear's the screen giving the illusion of a still window
        System.out.println("\033[H\033[2J");

        // Render the component
        for (String line : this.windowComponent.draw(
                this.displayWidth,
                this.displayHeight
            )) {
            System.out.println(line);
        }
    }
}
