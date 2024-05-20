public class RowComponent {
    Component[] children;
    RowComponent[] rowChildren;
    ColumnComponent[] columnChildren;

    RowComponent(Component[] children) {
    }

    RowComponent(RowComponent[] children) {
    }

    RowComponent(ColumnComponent[] children) {
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
}
