import java.util.ArrayList;

public class RowComponent {
    Component[] children;
    RowComponent[] rowChildren;
    ColumnComponent[] columnChildren;

    String title;
    boolean hasBorder = false;
    Dimension dimension = new Dimension();

    RowComponent(Component[] children) {
        this.children = children;
    }

    RowComponent(RowComponent[] children) {
        this.rowChildren = children;
    }

    RowComponent(ColumnComponent[] children) {
        this.columnChildren = children;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBorders(Boolean hasBorder) {
        this.hasBorder = hasBorder;
    }

    public void setFixedWidth(boolean fixed) {
        this.dimension.fixedWidth = fixed;
    }

    public void setFixedHeight(boolean fixed) {
        this.dimension.fixedHeight = fixed;
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
                dimensions[0] += childDimensions[0];
                if (dimensions[2] < childDimensions[2]) {
                    dimensions[2] = childDimensions[2];
                }
            }
        }

        if (this.columnChildren != null) {
            for (ColumnComponent child : this.columnChildren) {
                int[] childDimensions = child.calculateDimensions();
                dimensions[0] += childDimensions[0];
                if (dimensions[2] < childDimensions[2]) {
                    dimensions[2] = childDimensions[2];
                }
            }
        }

        if (this.children != null) {
            for (Component child : this.children) {
                int[] childDimensions = child.calculateDimensions();
                dimensions[0] += childDimensions[0];
                if (dimensions[2] < childDimensions[2]) {
                    dimensions[2] = childDimensions[2];
                }
            }
        }

        if (this.hasBorder) {
            dimensions[0] += 2;
            dimensions[2] += 2;
        }
        return dimensions;
    }

    public ArrayList<String> constructComponent(int width, int height) {
        Component component = new Component();
        if (this.title != null)
            component.setTitle(this.title);
        component.setBorder(this.hasBorder);

        int flexibleComponents = 0;
        int freeSpace = width;
        if (this.hasBorder)
            freeSpace -= 2;
        for (Component child : this.children) {
            int[] dimensions = child.calculateDimensions();
            if (dimensions[1] == -1)
                flexibleComponents += 1;
            freeSpace -= dimensions[0];
        }
        if (flexibleComponents == 0)
            flexibleComponents = 1;

        int padding = freeSpace / flexibleComponents;
        int extra = freeSpace % flexibleComponents;

        int innerHeight = height;
        if (this.hasBorder)
            innerHeight -= 2;

        ArrayList<ArrayList<String>> components = new ArrayList<ArrayList<String>>();
        for (Component child : this.children) {
            ArrayList<String> temp = new ArrayList<String>();

            int[] childDimensions = child.calculateDimensions();
            int componentWidth = childDimensions[0];

            if (childDimensions[1] == -1) {
                componentWidth += padding;
                if (flexibleComponents < extra) {
                    componentWidth += 1;
                }
                flexibleComponents -= 1;
            }

            int componentHeight = childDimensions[2];
            if (this.hasBorder)
                componentHeight -= 2;

            if (childDimensions[3] == -1) {
                componentHeight = innerHeight;
            }

            temp.addAll(child.constructComponent(componentWidth, componentHeight));

            String componentPadding = " ".repeat(componentWidth);

            for (int i = temp.size(); i < innerHeight; i++) {
                temp.add(componentPadding);
            }
            components.add(temp);
        }

        // TODO: Collapse Components
        ArrayList<String> buffer = new ArrayList<String>(innerHeight);

        for (ArrayList<String> componentBuffer : components) {
            if (buffer.isEmpty()) {
                buffer.addAll(componentBuffer);
                continue;
            }
            for (int i = 0; i < buffer.size(); i++) {
                buffer.set(i, buffer.get(i) + componentBuffer.get(i));
            }
        }

        component.batchWriteBuffer(buffer);

        return component.constructComponent(width, height);
    }
}
