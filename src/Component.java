import java.util.ArrayList;

/**
 * The Component class help's organise and determin the final output of a
 * component
 */
public class Component {
    private String title;

    private Dimension dimension = new Dimension();

    private int width = 0;
    private int minWidth = -1;
    private int maxWidth = -1;

    private int height = 0;
    private int minHeight = -1;
    private int maxheight = -1;

    private boolean hasBorder = false;
    private boolean fixedHeight = false;
    private boolean fixedWidth = false;

    //
    private boolean isRowComponenet = false;
    private boolean isColumnComponent = false;
    private ArrayList<Component> children = new ArrayList<Component>(0);

    // Display Buffer
    private ArrayList<String> buffer = new ArrayList<String>();

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWidth(int width) {
        this.dimension.width = width;
    }

    public void setHeight(int height) {
        this.dimension.height = height;
    }

    public void setMinWidth(int width) {
        this.dimension.minWidth = width;
    }

    public void setMaxWidth(int width) {
        this.dimension.maxHeight = width;
    }

    public void setMinHeight(int height) {
        this.dimension.minHeight = height;
    }

    public void setMaxHeight(int height) {
        this.dimension.maxHeight = height;
    }

    public void setFixedWidth() {
        this.dimension.fixedWidth = true;
    }

    public void setFixedHeight() {
        this.dimension.fixedHeight = true;
    }

    public void setBorder() {
        this.hasBorder = true;
    }

    public boolean hasBorder() {
        return this.hasBorder;
    }

    public void setColumnComponent() {
        this.isColumnComponent = true;
        this.isRowComponenet = false;
    }

    public void setRowComponent() {
        this.isRowComponenet = true;
        this.isColumnComponent = false;
    }

    public void addChild(Component child) {
        this.children.add(child);
    }

    public void rotateChildren() {
        this.children.add(this.children.removeFirst());
    }

    public void writeBuffer(String line) {
        buffer.add(line);
    }

    public void batchWriteBuffer(ArrayList<String> lines) {
        buffer.addAll(lines);
    }

    public void clear() {
        this.buffer.clear();
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

    public Dimension getDimensions() {
        return this.dimension;
    }

    public Dimension computeDimensions() {
        Dimension dimension = new Dimension();

        dimension.fixedWidth = this.dimension.fixedWidth;
        dimension.fixedHeight = this.dimension.fixedHeight;

        if (this.dimension.minWidth != -1)
            dimension.minWidth = this.dimension.minWidth;
        else {
            if (this.title != null)
                dimension.minWidth = this.title.length();
            else
                dimension.minWidth = 0;

            for (String bufferLine : this.buffer) {
                if (dimension.minWidth < bufferLine.length())
                    dimension.minWidth = bufferLine.length();
            }

            if (this.hasBorder)
                dimension.minWidth += 2;
        }

        if (this.dimension.minHeight != -1)
            dimension.minHeight = this.dimension.minHeight;
        else {
            dimension.minHeight = this.buffer.size();
            if (this.hasBorder)
                dimension.minHeight += 2;
        }

        if (this.dimension.maxWidth != -1)
            dimension.maxWidth = this.dimension.maxWidth;

        if (this.dimension.maxHeight != -1)
            dimension.maxHeight = this.dimension.maxHeight;

        if (dimension.fixedWidth)
            dimension.maxWidth = dimension.minWidth;

        if (dimension.fixedHeight)
            dimension.maxHeight = dimension.minWidth;

        if (dimension.maxWidth != -1 && dimension.minWidth > dimension.maxWidth)
            dimension.minWidth = dimension.maxWidth;

        if (dimension.maxHeight != -1 && dimension.minHeight > dimension.maxHeight)
            dimension.minHeight = dimension.maxHeight;

        return this.dimension;
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

        if (this.dimension.fixedWidth) {
            dimensions[1] = dimensions[0];
        } else {
            dimensions[1] = -1;
        }

        dimensions[2] = this.buffer.size();
        if (this.dimension.fixedHeight) {
            dimensions[3] = this.buffer.size();
        } else {
            dimensions[3] = -1;
        }

        if (this.hasBorder) {
            dimensions[0] += 2;
            dimensions[2] += 2;

            if (this.dimension.fixedWidth)
                dimensions[1] += 2;

            if (this.dimension.fixedHeight)
                dimensions[3] += 2;

        }

        return dimensions;
    }

}
