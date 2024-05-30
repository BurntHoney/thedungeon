import java.util.ArrayList;

public class Component {

    private String title;

    // Dimension Constraints
    private int minWidth = -1;
    private int maxWidth = -1;
    private int minHeight = -1;
    private int maxHeight = -1;

    private boolean fixedHeight = false;
    private boolean fixedWidth = false;
    private boolean hasBorder = false;

    // Row Component Modifiers
    private boolean isRowComponent = false;
    private boolean isColumnComponent = false;
    private ArrayList<Component> children = new ArrayList<Component>(0);

    // Display Buffer
    private ArrayList<String> buffer = new ArrayList<String>();

    /**
     * set the title of the component
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * set the minimum with of the component
     * @param width
     */
    public void setMinWidth(int width) {
        this.minWidth = width;
    }

    /**
     * set the maximum width of the component
     * @param width
     */
    public void setMaxWidth(int width) {
        this.maxHeight = width;
    }

    /**
     * set the minimum height of the component
     * @param height
     */
    public void setMinHeight(int height) {
        this.minHeight = height;
    }

    /**
     * set the maximum height of the component
     * @param height
     */
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    /**
     * set the width of the component to never change
     */
    public void setFixedWidth() {
        this.fixedWidth = true;
    }

    /**
     * set the height of the component to never change
     */
    public void setFixedHeight() {
        this.fixedHeight = true;
    }

    /**
     * surround the component with a border
     */
    public void setBorder() {
        this.hasBorder = true;
    }

    /**
     * Allow for the component to render multiple component's within a column
     */
    public void setColumnComponent() {
        this.isColumnComponent = true;
        this.isRowComponent = false;
    }

    /**
     * Allow for the component to render multiple components within a row
     */
    public void setRowComponent() {
        this.isRowComponent = true;
        this.isColumnComponent = false;
    }

    /**
     * The component to be rendered within a row or column
     * @param child
     */
    public void addChild(Component child) {
        this.children.add(child);
    }

    /**
     * remove all the children from the component's. Sometimes usefull for refreshing the component
     */
    public void clearChildren() {
        this.children.clear();
        this.clear();
    }

    /**
     * The text to be rendered within a component
     * @param line
     */
    public void writeBuffer(String line) {
        buffer.add(line);
    }

    /**
     * Write multiple lines of text
     * @param lines
     */
    public void batchWriteBuffer(ArrayList<String> lines) {
        buffer.addAll(lines);
    }

    /**
     * erases the content's within a buffer. usefull for refreshing a component
     */
    public void clear() {
        this.buffer.clear();
    }

    /**
     * Construct's the component with the give width and height
     * @param width the width of the component
     * @param height the height of the component
     * @return ArrayList<String> the constructed component
     */
    public ArrayList<String> draw(int width, int height) {
        // The border takes up 2 rows and 2 character's so we subtract it
        if (this.hasBorder) {
            width -= 2;
            height -= 2;
        }

        if (!this.children.isEmpty() && this.isColumnComponent) {
            buffer.clear();
            drawColumnComponent(width, height);
        }
        if (!this.children.isEmpty() && this.isRowComponent) {
            buffer.clear();
            drawRowComponent(width, height);
        }
        return drawComponent(width, height);
    }

    /**
     * Draw's each individual component and organizes it into a column
     * @param width
     * @param height
     */
    private void drawColumnComponent(int width, int height) {
        // Find the number of component's with free space
        int flexibleComponents = 0;
        int flexibleSpace = height;

        for (Component child : this.children) {
            Dimension childDimension = child.computeDimension();
            if (!child.fixedHeight) flexibleComponents += 1;
            flexibleSpace -= childDimension.minHeight;
        }

        // Prevent division by 0 errors
        if (flexibleComponents == 0) flexibleComponents = 1;

        int padding = flexibleSpace / flexibleComponents;
        int extra = flexibleSpace % flexibleComponents;

        for (Component child : this.children) {
            Dimension childDimension = child.computeDimension();

            // Calculate the best width for the component
            int componentWidth;
            if (childDimension.maxWidth != -1) {
                if (childDimension.maxWidth <= width) componentWidth =
                    childDimension.maxWidth;
                else if (childDimension.maxWidth > width) componentWidth =
                    width;
                else componentWidth = childDimension.minWidth;
            } else componentWidth = width;

            // Calculate the best height for the component
            int componentHeight = childDimension.minHeight;
            if (!childDimension.fixedHeight) {
                componentHeight += padding;
                if (flexibleComponents <= extra) componentHeight++;
                flexibleComponents--;
            }
            this.buffer.addAll(child.draw(componentWidth, componentHeight));
        }
    }

    /**
     * Draw's each individual component and organizes it into a row
     * @param width
     * @param height
     */
    private void drawRowComponent(int width, int height) {
        // Find the number of component's with free space
        int flexibleComponents = 0;
        int flexibleSpace = width;
        for (Component child : this.children) {
            Dimension childDimension = child.computeDimension();
            if (!child.fixedWidth) flexibleComponents += 1;
            flexibleSpace -= childDimension.minWidth;
        }

        // Prevent division by 0 errors
        if (flexibleComponents == 0) flexibleComponents = 1;

        int padding = flexibleSpace / flexibleComponents;
        int extra = flexibleSpace % flexibleComponents;

        ArrayList<ArrayList<String>> componentBuffers = new ArrayList<>();
        for (Component child : this.children) {
            Dimension childDimension = child.computeDimension();
            int componentWidth = childDimension.minWidth;

            if (!childDimension.fixedWidth) {
                componentWidth += padding;
                if (flexibleComponents < extra) componentWidth++;
                flexibleComponents--;
            }

            int componentHeight = childDimension.minHeight;
            if (childDimension.maxHeight == -1) componentHeight = height;
            else if (childDimension.maxHeight < height) componentHeight =
                childDimension.maxHeight;

            ArrayList<String> tempBuffer = new ArrayList<>(height);
            tempBuffer.addAll(child.draw(componentWidth, componentHeight));

            // Pad the end of the string
            String blankLine = " ".repeat(componentWidth);
            for (int i = tempBuffer.size(); i < height; i++) tempBuffer.add(
                blankLine
            );

            componentBuffers.add(tempBuffer);
        }

        // Merge each of the lines together
        for (ArrayList<String> componentBuffer : componentBuffers) {
            if (buffer.isEmpty()) {
                buffer.addAll(componentBuffer);
                continue;
            }
            for (int i = 0; i < buffer.size(); i++) {
                buffer.set(i, buffer.get(i) + componentBuffer.get(i));
            }
        }
    }

    /**
     * Draw each component and make sure the content's fit inside of it
     * @param width
     * @param height
     * @return
     */
    private ArrayList<String> drawComponent(int width, int height) {
        ArrayList<String> drawBuffer = new ArrayList<>(height);

        // Clip and format the buffer
        String formatString;
        if (this.hasBorder) formatString = "│%-" + width + "s│";
        else formatString = "%-" + width + "s";

        for (int i = 0; i < this.buffer.size(); i++) {
            String line = this.buffer.get(i);
            if (line == null) line = "";
            int lineLength = line.length();
            if (lineLength > width) {
                int partitionAmount = lineLength / width;
                if (lineLength % width != 0) partitionAmount++;

                for (int j = 0; j < partitionAmount; j++) {
                    try {
                        drawBuffer.add(
                            formatString.formatted(
                                line.substring(j * width, (j + 1) * width)
                            )
                        );
                    } catch (StringIndexOutOfBoundsException e) {
                        String something = formatString.formatted(
                            line.substring(j * width)
                        );
                        drawBuffer.add(something);
                    }
                }
            } else drawBuffer.add(formatString.formatted(line));
        }

        // Pad the buffer if it is too small or clip the height if it is too big
        int bufferSize = drawBuffer.size();
        if (bufferSize < height) {
            String blankLine;
            if (this.hasBorder) blankLine = "│" + " ".repeat(width) + "│";
            else blankLine = " ".repeat(width);

            for (int i = 0; i < height - bufferSize; i++) drawBuffer.add(
                blankLine
            );
        } else {
            for (
                int i = 0;
                i < bufferSize - height;
                i++
            ) drawBuffer.removeFirst();
        }

        // Add the header and footer for the border
        if (this.hasBorder) {
            if (this.title != null) {
                int padding = width - title.length();

                int leftPadding = padding / 2;
                int rightPadding = padding / 2;

                if (padding % 2 != 0) leftPadding += 1;

                drawBuffer.addFirst(
                    "┌" +
                    "─".repeat(leftPadding) +
                    title +
                    "─".repeat(rightPadding) +
                    "┐"
                );
            } else {
                drawBuffer.addFirst("┌" + "─".repeat(width) + "┐");
            }
            drawBuffer.add("└" + "─".repeat(width) + "┘");
        }

        return drawBuffer;
    }

    /**
     * computeDimension helps determine the optimal dimensions for a component to be
     * returned
     *
     * @return Dimension
     */
    public Dimension computeDimension() {
        Dimension dimension = new Dimension();

        // Copy the current component dimensions
        dimension.fixedWidth = this.fixedWidth;
        dimension.fixedHeight = this.fixedHeight;
        dimension.minWidth = this.minWidth;
        dimension.minHeight = this.minHeight;
        dimension.maxWidth = this.maxWidth;
        dimension.maxHeight = this.maxHeight;

        // Calculate the minimum width as minimum width should never be -1
        if (this.minWidth == -1) {
            dimension.minWidth = 0;
            if (this.isRowComponent) {
                for (Component component : this.children) {
                    Dimension componentDimension = component.computeDimension();
                    dimension.minWidth += componentDimension.minWidth;
                }
            } else if (this.isColumnComponent) {
                for (Component component : this.children) {
                    Dimension componentDimension = component.computeDimension();

                    if (
                        dimension.minWidth < componentDimension.minWidth
                    ) dimension.minWidth = componentDimension.minWidth;
                }
            } else {
                if (this.title != null) dimension.minWidth =
                    this.title.length();

                for (String bufferLine : this.buffer) {
                    int lineLength = bufferLine.length();

                    if (lineLength > dimension.minWidth) dimension.minWidth =
                        lineLength;
                }
            }

            if (
                dimension.maxWidth != -1 &&
                dimension.minWidth > dimension.maxWidth
            ) dimension.minWidth = dimension.maxWidth;
        }

        if (dimension.minHeight == -1) {
            dimension.minHeight = 0;
            if (this.isRowComponent) {
                for (Component child : this.children) {
                    Dimension childDimension = child.computeDimension();

                    if (
                        dimension.minHeight < childDimension.minHeight
                    ) dimension.minHeight = childDimension.minHeight;
                }
            } else if (this.isColumnComponent) {
                for (Component component : this.children) {
                    Dimension componentDimension = component.computeDimension();
                    dimension.minHeight += componentDimension.minHeight;
                }
            } else {
                dimension.minHeight = this.buffer.size();
            }

            if (
                dimension.maxHeight != -1 &&
                dimension.minHeight > dimension.maxHeight
            ) dimension.minHeight = dimension.maxHeight;
        }

        if (this.hasBorder) {
            dimension.minWidth += 2;
            dimension.minHeight += 2;
        }

        if (dimension.fixedHeight) dimension.maxHeight = dimension.minHeight;

        if (dimension.fixedWidth) dimension.maxWidth = dimension.minWidth;

        return dimension;
    }
}
