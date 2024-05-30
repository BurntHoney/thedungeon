import java.util.ArrayList;

/**
 * Dispalys the minimap and the legend togther so the player always knows where they are in the game
 */
public class MapComponent {

    Component legendComponent;
    Component minimapComponent;
    Component mapComponent;

    MapComponent() {
        // Initialize Legend Component with
        this.legendComponent = new Component();
        this.legendComponent.setTitle("Legend");
        this.legendComponent.setBorder();

        this.legendComponent.writeBuffer(" x = player");
        this.legendComponent.writeBuffer(" w = wall");
        this.legendComponent.writeBuffer(" l = locked");
        this.legendComponent.writeBuffer(" - = hidden");
        this.legendComponent.writeBuffer(" m = miniboss");
        this.legendComponent.writeBuffer(" b = boss");

        // Initialize Map Component
        this.minimapComponent = new Component();
        this.minimapComponent.setFixedWidth();
        this.minimapComponent.setFixedHeight();

        // Initialize the parent component
        this.mapComponent = new Component();
        this.mapComponent.setFixedWidth();
        this.mapComponent.setFixedHeight();
        this.mapComponent.setRowComponent();
        this.mapComponent.addChild(this.minimapComponent);
        this.mapComponent.addChild(this.legendComponent);
    }

    /**
     * Retrieves the underlying component
     * @return Component
     */
    public Component getComponent() {
        this.minimapComponent.clear();

        // update map component with the player position
        ArrayList<String> mapCodes = new ArrayList<>(25);

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                mapCodes.add(Main.dungeon.rooms[y][x].getCode());
            }
        }

        mapCodes.set(Main.dungeon.yPos * 5 + Main.dungeon.xPos, "x");

        this.minimapComponent.writeBuffer("┌─────┬─────┬─map─┬─────┬─────┐");

        this.minimapComponent.writeBuffer(
                String.format(
                    "│  %s  │  %s  │  %s  │  %s  │  %s  │",
                    mapCodes.get(0),
                    mapCodes.get(1),
                    mapCodes.get(2),
                    mapCodes.get(3),
                    mapCodes.get(4)
                )
            );

        this.minimapComponent.writeBuffer("├─────┼─────┼─────┼─────┼─────┤");
        this.minimapComponent.writeBuffer(
                String.format(
                    "│  %s  │  %s  │  %s  │  %s  │  %s  │",
                    mapCodes.get(5),
                    mapCodes.get(6),
                    mapCodes.get(7),
                    mapCodes.get(8),
                    mapCodes.get(9)
                )
            );

        this.minimapComponent.writeBuffer("├─────┼─────┼─────┼─────┼─────┤");
        this.minimapComponent.writeBuffer(
                String.format(
                    "│  %s  │  %s  │  %s  │  %s  │  %s  │",
                    mapCodes.get(10),
                    mapCodes.get(11),
                    mapCodes.get(12),
                    mapCodes.get(13),
                    mapCodes.get(14)
                )
            );

        this.minimapComponent.writeBuffer("├─────┼─────┼─────┼─────┼─────┤");
        this.minimapComponent.writeBuffer(
                String.format(
                    "│  %s  │  %s  │  %s  │  %s  │  %s  │",
                    mapCodes.get(15),
                    mapCodes.get(16),
                    mapCodes.get(17),
                    mapCodes.get(18),
                    mapCodes.get(19)
                )
            );

        this.minimapComponent.writeBuffer("├─────┼─────┼─────┼─────┼─────┤");
        this.minimapComponent.writeBuffer(
                String.format(
                    "│  %s  │  %s  │  %s  │  %s  │  %s  │",
                    mapCodes.get(20),
                    mapCodes.get(21),
                    mapCodes.get(22),
                    mapCodes.get(23),
                    mapCodes.get(24)
                )
            );

        this.minimapComponent.writeBuffer("└─────┴─────┴─────┴─────┴─────┘");

        return this.mapComponent;
    }
}
