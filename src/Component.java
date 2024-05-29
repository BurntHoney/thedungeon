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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMinWidth(int width) {
        this.minWidth = width;
    }

    public void setMaxWidth(int width) {
        this.maxHeight = width;
    }

    public void setMinHeight(int height) {
        this.minHeight = height;
    }

    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    public void setFixedWidth() {
        this.fixedWidth = true;
    }

    public void setFixedHeight() {
        this.fixedHeight = true;
    }

    public void setBorder() {
        this.hasBorder = true;
    }

    // TODO: Delete this
    public boolean hasBorder() {
        return this.hasBorder;
    }

    public void setColumnComponent() {
        this.isColumnComponent = true;
        this.isRowComponent = false;
    }

    public void setRowComponent() {
        this.isRowComponent = true;
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

    public ArrayList<String> draw(int width, int height) {
        if (this.isColumnComponent) {
            buffer.clear();
            drawColumnComponent(width - 2, height - 2);
        }
        if (this.isRowComponent) {
            buffer.clear();
            drawRowComponent(width - 2, height - 2);
        }
        return drawComponent(width, height);
    }

    private void drawColumnComponent(int width, int height) {
        // Find the number of component's with free space
        int flexibleComponents = 0;
        int flexibleSpace = height;

        for (Component child : this.children) {
            Dimension childDimension = child.computeDimension();
            if (child.maxHeight == -1)
                flexibleComponents += 1;
            flexibleSpace -= childDimension.minHeight;
        }

        // Prevent division by 0 errors
        if (flexibleComponents == 0)
            flexibleComponents = 1;

        int padding = flexibleSpace / flexibleComponents;
        int extra = flexibleSpace % flexibleComponents;

        for (Component child : this.children) {
            Dimension childDimension = child.computeDimension();

            // Calculate the best width for the component
            int componentWidth;
            if (childDimension.maxWidth != -1) {
                if (childDimension.maxWidth <= width)
                    componentWidth = childDimension.maxWidth;
                else if (childDimension.maxWidth > width)
                    componentWidth = width;
                else
                    componentWidth = childDimension.minWidth;
            } else
                componentWidth = width;

            // Calculate the best height for the component
            int componentHeight = childDimension.minHeight;
            if (childDimension.maxHeight == -1) {
                componentHeight += padding;
                if (flexibleComponents <= extra)
                    componentHeight++;
                flexibleComponents--;
            }
            this.buffer.addAll(child.draw(componentWidth, componentHeight));
        }
    }

    private ArrayList<String> drawRowComponent(int width, int height) {
        ArrayList<String> drawBuffer = new ArrayList<>(height);
        return drawBuffer;
    }

    private ArrayList<String> drawComponent(int width, int height) {
        ArrayList<String> drawBuffer = new ArrayList<>(height);

        if (this.hasBorder) {
            width -= 2;
            height -= 2;
        }

        // Clip and format the buffer
        String formatString;
        if (this.hasBorder)
            formatString = "│%-" + width + "s│";
        else
            formatString = "%-" + width + "s";

        for (int i = 0; i < this.buffer.size(); i++) {
            String line = this.buffer.get(i);
            int lineLength = line.length();
            if (lineLength > width) {
                int partitionAmount = lineLength / width;
                if (lineLength % width != 0)
                    partitionAmount++;

                for (int j = 0; j < partitionAmount; j++) {
                    try {
                        drawBuffer.add(formatString.formatted(line.substring(j * width, (j + 1) * width)));
                    } catch (StringIndexOutOfBoundsException e) {
                        drawBuffer.add(formatString.formatted(line.substring(j * width)));
                    }
                }
            } else
                drawBuffer.add(formatString.formatted(line));
        }

        // Pad the buffer if it is too small or clip the height if it is too big
        int bufferSize = drawBuffer.size();
        if (bufferSize < height) {
            String blankLine;
            if (this.hasBorder)
                blankLine = "│" + " ".repeat(width) + "│";
            else
                blankLine = " ".repeat(width);

            for (int i = 0; i < height - bufferSize; i++)
                drawBuffer.add(blankLine);
        } else {
            for (int i = 0; i < bufferSize - height; i++)
                drawBuffer.removeFirst();
        }

        // Add the header and footer for the border
        if (this.hasBorder) {
            if (this.title != null) {
                int padding = width - title.length();

                int leftPadding = padding / 2;
                int rightPadding = padding / 2;

                if (padding % 2 != 0)
                    leftPadding += 1;

                drawBuffer.addFirst("┌" + "─".repeat(leftPadding) + title + "─".repeat(rightPadding) + "┐");
            } else {
                drawBuffer.addFirst("┌" + "─".repeat(width) + "┐");
            }
            drawBuffer.add("└" + "─".repeat(width) + "┘");
        }

        return drawBuffer;
    }

    /**
     * computeDimension helps determine the dimensions for a component to be
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
            if (this.isRowComponent) {
                for (Component component : this.children) {
                    Dimension componentDimension = component.computeDimension();
                    dimension.minWidth += componentDimension.minWidth;
                }
            } else if (this.isColumnComponent) {
                for (Component component : this.children) {
                    Dimension componentDimension = component.computeDimension();

                    if (dimension.minWidth < componentDimension.minWidth)
                        dimension.minWidth = componentDimension.minWidth;
                }
            } else {
                if (this.title != null)
                    dimension.minWidth = this.title.length();

                for (String bufferLine : this.buffer) {
                    int lineLength = bufferLine.length();
                    if (lineLength < dimension.minWidth)
                        dimension.minWidth = lineLength;
                }
            }

            if (dimension.maxWidth != -1 && dimension.minWidth > dimension.maxWidth)
                dimension.minWidth = dimension.maxWidth;
        }

        if (dimension.minHeight == -1) {
            if (this.isRowComponent) {
                for (Component component : this.children) {
                    Dimension componentDimension = component.computeDimension();

                    if (dimension.minHeight < componentDimension.minHeight)
                        dimension.minHeight = componentDimension.minHeight;
                }
            } else if (this.isColumnComponent) {
                for (Component component : this.children) {
                    Dimension componentDimension = component.computeDimension();
                    dimension.minHeight += componentDimension.minHeight;
                }
            } else {
                dimension.minHeight = this.buffer.size();
            }

            if (dimension.maxHeight != -1 && dimension.minHeight > dimension.maxHeight)
                dimension.minHeight = dimension.maxHeight;
        }

        if (dimension.fixedHeight)
            dimension.maxHeight = dimension.minHeight;

        if (dimension.fixedWidth)
            dimension.maxWidth = dimension.minWidth;

        return dimension;
    }
}
