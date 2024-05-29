public class InventoryComponent {
    Component inventoryComponent;

    InventoryComponent() {
        this.inventoryComponent = new Component();
        this.inventoryComponent.setTitle("Inventory");
        this.inventoryComponent.setBorder();
    }

    public Component getComponent() {
        this.inventoryComponent.clear();
        for (Item item : Main.player.inventory) {
            this.inventoryComponent.writeBuffer(item.name);
        }

        return this.inventoryComponent;
    }
}
