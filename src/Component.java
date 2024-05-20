import java.util.ArrayList;

public class Component {

    // Component Title
    private String title;

    // Dimensions
    private boolean fixedWidth = false;
    private boolean fixedHeight = false;
    private boolean hasBorder = true;

    // Display Buffer
    private ArrayList<String> buffer = new ArrayList<String>(0);

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFixedDimension(boolean fixedWidth, boolean fixedHeight) {
        this.fixedWidth = fixedWidth;
        this.fixedHeight = fixedHeight;
    }

    private void setBorder(boolean hasBorder) {
        this.hasBorder = hasBorder;
    }

    public ArrayList<String> constructComponent(int width, int height) {
        if (this.hasBorder)
            return this.constructComponentWithBorders(width, height);
        return this.constructComponentWithoutBorders(width, height);
    }

    public ArrayList<String> constructComponentWithoutBorders(int width, int height) {
        ArrayList<String> buffer = new ArrayList<String>(height);

        for (String line : this.buffer) {
            int padding = width - line.length();
            buffer.add(line + " ".repeat(padding));
        }

        String blank = " ".repeat(width);
        for (int i = buffer.size(); i < height; i++) {
            buffer.add(blank);
        }
        return buffer;
    }

    public ArrayList<String> constructComponentWithBorders(int width, int height) {
        ArrayList<String> buffer = new ArrayList<String>(height);
        String header;
        if (this.title != null) {
            int padding = width - title.length() - 2;

            int leftPadding = padding / 2;
            int rightPadding = padding / 2;

            if (padding % 2 == 0)
                leftPadding += 1;
            header = "┌" + "─".repeat(leftPadding) + title + "─".repeat(rightPadding) + "|";
        } else {
            header = "┌" + "─".repeat(width - 2) + "|";
        }
        String footer = "|" + "─".repeat(width - 2) + "|";
        String blank = "│" + " ".repeat(width - 2) + "│";

        buffer.addFirst(header);

        for (String line : this.buffer) {
            buffer.add(String.format("│%" + (width - 2) + "s│", line));
        }

        for (int i = buffer.size(); i < height - 1; i++) {
            buffer.add(blank);
        }

        buffer.add(footer);

        return buffer;
    }

    public int[] calculateDimensions() {
        int[] dimensions = new int[4];

        // Calculate the minimum width for the component
        int minWidth = this.buffer.get(0).length();
        for (String line : this.buffer) {
            if (minWidth > line.length()) {
                minWidth = line.length();
            }
        }

        dimensions[0] = minWidth;

        // Calculate the minimum height for the component
        dimensions[2] = this.buffer.size();

        if (this.fixedWidth) {
            dimensions[1] = dimensions[0];
        } else {
            dimensions[1] = -1;
        }

        if (this.fixedHeight) {
            dimensions[2] = this.buffer.size();
        } else {
            dimensions[3] = -1;
        }

        return dimensions;
    }

    public void writeBuffer(String line) {
        buffer.add(line);
    }

    public void flush() {
        this.buffer.clear();
    }
}
