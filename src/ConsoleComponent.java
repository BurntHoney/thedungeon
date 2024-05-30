class ConsoleComponent {

    Component component;

    ConsoleComponent() {
        this.component = new Component();
        this.component.setTitle("Console");
        this.component.setBorder();
    }

    /**
     * Retrieves the underlying component
     * @return Component
     */
    public Component getComponent() {
        return component;
    }

    /**
     * Creates a log entry which is formatted by the source and the message
     * @param source
     * @param message
     */
    public void log(String source, String message) {
        this.component.writeBuffer(String.format("[%s] %s", source, message));
    }
}
