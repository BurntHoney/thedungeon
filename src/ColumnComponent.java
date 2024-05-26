import java.util.ArrayList;

public class ColumnComponent {
    Component[] children;
    ColumnComponent[] columnChildren;
    RowComponent[] rowChildren;

    String title;
    boolean hasBorder = false;
    boolean fixedWidth;
    boolean fixedHeight;

    ColumnComponent(Component[] children) {
        this.children = children;
    }

    ColumnComponent(ColumnComponent[] children) {
        this.columnChildren = children;
    }

    ColumnComponent(RowComponent[] children) {
        this.rowChildren = children;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBorders(Boolean hasBorder) {
        this.hasBorder = hasBorder;
    }

    public void setFixedDimension(boolean fixedWidth, boolean fixedHeight) {
        this.fixedWidth = fixedWidth;
        this.fixedHeight = fixedHeight;
    }

    int[] calculateDimensions() {
        int[] dimensions = new int[4];
        if (this.title != null) {
            dimensions[0] = this.title.length();
        } else {
            dimensions[0] = 0;
        }
        dimensions[2] = 0;

        if (this.rowChildren != null) {
            for (RowComponent child : this.rowChildren) {
                int[] childDimensions = child.calculateDimensions();
                if (dimensions[0] < childDimensions[0]) {
                    dimensions[0] = childDimensions[0];
                }
                dimensions[2] += childDimensions[2];
            }
        }

        if (this.columnChildren != null) {
            for (ColumnComponent child : this.columnChildren) {
                int[] childDimensions = child.calculateDimensions();
                if (dimensions[0] < childDimensions[0]) {
                    dimensions[0] = childDimensions[0];
                }
                dimensions[2] += childDimensions[2];
            }
        }

        if (this.children != null) {
            for (Component child : this.children) {
                int[] childDimensions = child.calculateDimensions();
                if (dimensions[0] < childDimensions[0]) {
                    dimensions[0] = childDimensions[0];
                }

                dimensions[2] += childDimensions[2];
            }
        }

        if (this.fixedWidth) {
            dimensions[1] = -1;
        } else {
            dimensions[1] = dimensions[0];
        }

        if (this.fixedHeight) {
            dimensions[3] = -1;
        } else {
            dimensions[3] = dimensions[2];
        }

        if (this.hasBorder) {
            dimensions[0] += 2;
            dimensions[2] += 2;

            if (!this.fixedWidth)
                dimensions[1] += 2;

            if (!this.fixedHeight)
                dimensions[3] += 2;

        }
        return dimensions;
    }

    public ArrayList<String> constructComponent(int width, int height) {
        Component component = new Component();
        if (this.title != null)
            component.setTitle(title);
        component.setBorder(true);
        component.setFixedDimension(this.fixedWidth, this.fixedHeight);

        int flexibleComponents = 0;
        int freeSpace = height;
        if (this.hasBorder)
            freeSpace -= 1;
        for (Component child : this.children) {
            int[] dimensions = child.calculateDimensions();
            if (dimensions[3] == -1)
                flexibleComponents += 1;
            freeSpace -= dimensions[2];
        }
        if (flexibleComponents == 0)
            flexibleComponents = 1;

        int padding = freeSpace / flexibleComponents;
        int extra = freeSpace % flexibleComponents;

        if (this.children != null) {
            for (Component child : this.children) {
                int[] dimension = child.calculateDimensions();
                int componentWidth = dimension[0];
                if (dimension[1] == -1) {
                    componentWidth = width;
                    if (this.hasBorder)
                        componentWidth -= 2;
                }

                int componentHeight = dimension[2];
                if (dimension[3] == -1) {
                    componentHeight += padding;
                    if (flexibleComponents <= extra) {
                        componentHeight += 1;
                    }
                    flexibleComponents -= 1;
                }
                component.batchWriteBuffer(child.constructComponent(componentWidth, componentHeight));
            }
        }

        if (this.columnChildren != null) {
            for (ColumnComponent child : this.columnChildren) {
                int[] dimension = child.calculateDimensions();
                int componentWidth = dimension[0];
                if (dimension[1] == -1) {
                    componentWidth = width - 2;
                }

                int componentHeight = dimension[2];
                if (dimension[3] == -1) {
                    componentHeight += padding;
                    if (flexibleComponents <= extra) {
                        componentHeight += 1;
                    }
                    flexibleComponents -= 1;
                }
                System.out.println(componentWidth);
                component.batchWriteBuffer(child.constructComponent(componentWidth, componentHeight));
            }
        }

        if (this.rowChildren != null) {
            for (RowComponent child : this.rowChildren) {
                int[] dimension = child.calculateDimensions();
                int componentWidth = dimension[0];
                if (dimension[1] == -1) {
                    componentWidth = width - 2;
                }

                int componentHeight = dimension[2];
                if (dimension[3] == -1) {
                    componentHeight += padding;
                    if (flexibleComponents <= extra) {
                        componentHeight += 1;
                    }
                    flexibleComponents -= 1;
                }
                System.out.println(componentWidth);
                component.batchWriteBuffer(child.constructComponent(componentWidth, componentHeight));
            }
        }

        return component.constructComponent(width, height);
    }

}
