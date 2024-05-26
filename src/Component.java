import java.util.ArrayList;

public class Component {

    // Component Title
    private String title;

    // Dimensions
    private int maxHeight = -1;
    private int maxWidth = -1;
    private boolean fixedWidth = false;
    private boolean fixedHeight = false;
    private boolean hasBorder = false;

    // Display Buffer
    private ArrayList<String> buffer = new ArrayList<String>(0);

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFixedDimension(boolean fixedWidth, boolean fixedHeight) {
        this.fixedWidth = fixedWidth;
        this.fixedHeight = fixedHeight;
    }

    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    public void setBorder(boolean hasBorder) {
        this.hasBorder = hasBorder;
    }

    public ArrayList<String> constructComponent(int width, int height) {
        if (this.hasBorder)
            return this.constructComponentWithBorders(width, height);
        return this.constructComponentWithoutBorders(width, height);
    }

    private ArrayList<String> constructComponentWithoutBorders(int width, int height) {
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

    private ArrayList<String> constructComponentWithBorders(int width, int height) {
        width -= 2;
        height -= 2;

        ArrayList<String> buffer = new ArrayList<String>(height);
        String header;
        if (this.title != null) {
            int padding = width - title.length();

            int leftPadding = padding / 2;
            int rightPadding = padding / 2;

            if (padding % 2 != 0)
                leftPadding += 1;
            header = "┌" + "─".repeat(leftPadding) + title + "─".repeat(rightPadding) + "┐";
        } else {
            header = "┌" + "─".repeat(width) + "┐";
        }
        String footer = "└" + "─".repeat(width) + "┘";
        String blank = "│" + " ".repeat(width) + "│";

        for (String line : this.buffer) {
            buffer.add(String.format("│%-" + width + "s│", line));
        }

        for (int i = buffer.size(); i < height; i++) {
            buffer.add(blank);
        }

        buffer.addFirst(header);
        buffer.add(footer);

        return buffer;
    }

    public int[] calculateDimensions() {
        int[] dimensions = new int[4];

        // Calculate the minimum width for the component
        int minWidth;
        if (this.title != null) {
            minWidth = this.title.length();
        } else {
            minWidth = this.buffer.get(0).length();
        }
        for (String line : this.buffer) {
            if (minWidth < line.length()) {
                minWidth = line.length();
            }
        }

        dimensions[0] = minWidth;

        if (this.fixedWidth) {
            dimensions[1] = dimensions[0];
        } else {
            dimensions[1] = -1;
        }

        dimensions[2] = this.buffer.size();
        if (this.fixedHeight) {
            dimensions[3] = this.buffer.size();
        } else {
            dimensions[3] = -1;
        }

        if (this.hasBorder) {
            dimensions[0] += 2;
            dimensions[2] += 2;

            if (this.fixedWidth)
                dimensions[1] += 2;

            if (this.fixedHeight)
                dimensions[3] += 2;

        }

        return dimensions;
    }

    public void writeBuffer(String line) {
        buffer.add(line);
    }

    public void batchWriteBuffer(ArrayList<String> lines) {
        buffer.addAll(lines);
    }

    public void flush() {
        this.buffer.clear();
    }
}
