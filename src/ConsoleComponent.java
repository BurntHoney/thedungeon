class ConsoleComponent {

    Component component;

    ConsoleComponent() {
        this.component = new Component();
        this.component.setTitle("Console");
        this.component.setBorder();
    }

    public Component getComponent() {
        return component;
    }

    public void log(String source, String message) {
        this.component.writeBuffer(String.format("[%s] %s", source, message));
    }
}
