public class CommandsComponent {

    Component commandsComponent;

    CommandsComponent() {
        this.commandsComponent = new Component();
        this.commandsComponent.setTitle("Commands");
        this.commandsComponent.setBorder();
        this.commandsComponent.setFixedHeight();

        this.commandsComponent.writeBuffer("move <direction>");
        this.commandsComponent.writeBuffer("attack <target>");
        this.commandsComponent.writeBuffer("use <item>");
        this.commandsComponent.writeBuffer("resize_width <width>");
        this.commandsComponent.writeBuffer("resize_height <height>");
        this.commandsComponent.writeBuffer("quit");
    }

    /**
     * Retrieves the underlying component
     * @return Component
     */
    public Component getComponent() {
        return this.commandsComponent;
    }
}
