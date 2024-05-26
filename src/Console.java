import java.util.ArrayList;

public class Console {

    static final String CONSOLE_HEADER = "┌" + "─".repeat(101) + "┐";
    static final String CONSOLE_FOOTER = "└" + "─".repeat(101) + "┘";
    static final String CONSOLE_PADDING = "│" + " ".repeat(101) + "│";

    private final int bufferSize;
    private final ArrayList<Record> buffer;

    Console(int bufferSize) {
        this.bufferSize = bufferSize;
        this.buffer = new ArrayList<>(bufferSize);
    }

    public void log(String source, String message, String level) {
        this.buffer.add(new Record(source, message, level));
        if (this.buffer.size() >= this.bufferSize) {
            this.buffer.removeFirst();
        }
    }

    private String convertLogLevel(String level) {
        switch (level) {
            case "info":
                return TerminalColor.GREEN;
            case "error":
                return TerminalColor.RED;
            case "warning":
                return TerminalColor.YELLOW;
            case "spam":
                return TerminalColor.CYAN;
            default:
                return TerminalColor.WHITE;
        }
    }

    public ArrayList<String> render() {
        ArrayList<String> lines = new ArrayList<>(this.bufferSize);

        lines.add(CONSOLE_HEADER);
        for (Record record : this.buffer) {
            lines.add(
                    "│" +
                            convertLogLevel(record.level) +
                            String.format(
                                    "%-101s",
                                    "[" + record.source + "]: " + record.message)
                            +
                            TerminalColor.RESET +
                            "│");
        }

        // Pad console
        for (int i = this.buffer.size(); i < this.bufferSize; i++) {
            lines.add(CONSOLE_PADDING);
        }
        lines.add(CONSOLE_FOOTER);

        return lines;
    }
}
