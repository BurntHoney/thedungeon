/**
 * Shows the inventory to the user
 */
public class InventoryComponent {

    Component inventoryComponent;

    InventoryComponent() {
        this.inventoryComponent = new Component();
        this.inventoryComponent.setTitle("Inventory");
        this.inventoryComponent.setBorder();
    }

    /**
     * Retrieves the underlying component
     * @return Component
     */
    public Component getComponent() {
        this.inventoryComponent.clear();
        for (String item : Main.player.inventory) {
            this.inventoryComponent.writeBuffer(" - " + item);
        }

        return this.inventoryComponent;
    }
}
