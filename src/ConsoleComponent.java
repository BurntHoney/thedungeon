
class ConsoleComponent {
    Component component;

    ConsoleComponent() {
        this.component = new Component();
        this.component.setTitle("Console");
        this.component.setBorder(true);
    }

    public Component getComponent() {
        return component;
    }

    public void log(String level, String source, String message) {
        this.component.writeBuffer(
                String.format("%s[%s] %s" + TerminalColor.RESET,
                        convertLevelToColor(level),
                        source,
                        message));
    }

    private String convertLevelToColor(String level) {
        switch (level) {
            case "error":
                return TerminalColor.RED;
            case "info":
                return TerminalColor.GREEN;
            case "warn":
                return TerminalColor.YELLOW;
            case "story":
                return TerminalColor.BLUE;
            default:
                return TerminalColor.WHITE;
        }
    }
}
