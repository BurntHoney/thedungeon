import java.util.ArrayList;

public class ColumnComponent {
    Component[] component;

    Component[] children;
    ColumnComponent[] columnChildren;
    RowComponent[] rowChildren;

    String title;
    Boolean hasBorder;

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

    int[] calculateDimensions() {
        int[] dimensions = new int[4];
        dimensions[0] = 0;
        dimensions[2] = 0;

        if (this.rowChildren != null) {
            for (RowComponent child : this.rowChildren) {
                int[] childDimensions = child.calculateDimensions();
                dimensions[0] += childDimensions[0];
                dimensions[2] += childDimensions[0];

                if (dimensions[1] != -1 && childDimensions[1] == -1) {
                    dimensions[1] = -1;
                }

                if (dimensions[1] != -1 || childDimensions[1] != -1) {
                    dimensions[1] += childDimensions[1];
                }

                if (dimensions[3] != -1 && childDimensions[3] == -1) {
                    dimensions[3] = -1;
                }

                if (dimensions[3] != -1 || childDimensions[3] != -1) {
                    dimensions[3] += childDimensions[1];
                }
            }
            return dimensions;
        }

        if (this.columnChildren != null) {
            for (ColumnComponent child : this.columnChildren) {
                int[] childDimensions = child.calculateDimensions();
                dimensions[0] += childDimensions[0];
                dimensions[2] += childDimensions[0];

                if (dimensions[1] != -1 && childDimensions[1] == -1) {
                    dimensions[1] = -1;
                }

                if (dimensions[1] != -1 || childDimensions[1] != -1) {
                    dimensions[1] += childDimensions[1];
                }

                if (dimensions[3] != -1 && childDimensions[3] == -1) {
                    dimensions[3] = -1;
                }

                if (dimensions[3] != -1 || childDimensions[3] != -1) {
                    dimensions[3] += childDimensions[1];
                }
            }
            return dimensions;
        }

        for (Component child : this.children) {
            int[] childDimensions = child.calculateDimensions();
            dimensions[0] += childDimensions[0];
            dimensions[2] += childDimensions[0];

            if (dimensions[1] != -1 && childDimensions[1] == -1) {
                dimensions[1] = -1;
            }

            if (dimensions[1] != -1 || childDimensions[1] != -1) {
                dimensions[1] += childDimensions[1];
            }

            if (dimensions[3] != -1 && childDimensions[3] == -1) {
                dimensions[3] = -1;
            }

            if (dimensions[3] != -1 || childDimensions[3] != -1) {
                dimensions[3] += childDimensions[1];
            }
        }
        return dimensions;
    }

    public ArrayList<String> constructComponent(int width, int height) {
        Component component = new Component();

        int flexibleComponents = 0;
        for (Component child : this.children) {
            int[] dimensions = child.calculateDimensions();
            if (dimensions[3] == -1)
                flexibleComponents += 1;

        }

        return component.constructComponent(width, height);
    }

}
