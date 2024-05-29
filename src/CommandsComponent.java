public class CommandsComponent {
    Component commandsComponent;

    CommandsComponent() {
        this.commandsComponent = new Component();
        this.commandsComponent.setTitle("Commands");
        this.commandsComponent.setBorder(true);
        this.commandsComponent.setFixedHeight(true);

        this.commandsComponent.writeBuffer("move <direction>");
        this.commandsComponent.writeBuffer("attack <target>");
        this.commandsComponent.writeBuffer("use <item>");
        this.commandsComponent.writeBuffer("quit");
    }

    public Component getComponent() {
        return this.commandsComponent;
    }
}
