/**
 *Displays the room to the user
 */
public class RoomComponent {

    Component roomComponent;
    CharacterComponent playerComponent;
    Component enemyComponent;

    RoomComponent() {
        this.roomComponent = new Component();
        this.roomComponent.setTitle("Room");
        this.roomComponent.setBorder();
        this.roomComponent.setRowComponent();
        this.roomComponent.setMinHeight(15);
        this.roomComponent.setFixedHeight();

        this.enemyComponent = new Component();
        this.enemyComponent.setTitle("Enemies");
        this.enemyComponent.setBorder();
        this.enemyComponent.setRowComponent();
    }

    /**
     * Retrieves the underlying component
     * @return Component
     */
    public Component getComponent() {
        this.roomComponent.clearChildren();
        this.enemyComponent.clearChildren();

        this.playerComponent = new CharacterComponent(Main.player, true);

        Room room = Main.dungeon.rooms[Main.dungeon.yPos][Main.dungeon.xPos];

        if (!room.enemies.isEmpty()) {
            for (Character enemy : room.enemies) {
                CharacterComponent enemyComponent = new CharacterComponent(
                    enemy,
                    false
                );
                this.enemyComponent.addChild(enemyComponent.getComponent());
            }
        }

        this.roomComponent.addChild(playerComponent.getComponent());
        this.roomComponent.addChild(this.enemyComponent);

        return this.roomComponent;
    }
}
