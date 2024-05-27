
class ConsoleComponent {
    Component component;

    ConsoleComponent() {
        this.component = new Component();
        this.component.setTitle("Console");
        this.component.setBorder(true);
        this.component.setMinWidth(100);
        this.component.setMinHeight(25);
    }

    public Component getComponent() {
        return component;
    }

    public void log(String level, String source, String message) {
        this.component.writeBuffer(String.format("[%s] [%s] %s", level, source, message));
    }
}
