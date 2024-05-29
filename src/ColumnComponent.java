import java.util.ArrayList;

/**
 * The class ColumnComponent is a class that build's upon the component class
 * with the purpose of grouping multiple column's within the structure of a
 * group
 */
public class ColumnComponent {
    Component component = new Component();
    Component[] children;

    String title;

    ColumnComponent(Component[] children) {
        this.children = children;
    }

    public void setTitle(String title) {
        component.setTitle(title);
    }

    public void setBorders() {
        this.component.setBorder();
    }

    public void setFixedWidth() {
        this.component.setFixedWidth();
    }

    public void setFixedHeight() {
        this.component.setFixedWidth();
    }

    // int[] calculateDimensions() {
    // int[] dimensions = new int[4];
    // if (this.title != null) {
    // dimensions[0] = this.title.length();
    // } else {
    // dimensions[0] = 0;
    // }
    // dimensions[2] = 0;

    // for (Component child : this.children) {
    // int[] childDimensions = child.calculateDimensions();
    // if (dimensions[0] < childDimensions[0]) {
    // dimensions[0] = childDimensions[0];
    // }

    // dimensions[2] += childDimensions[2];
    // }

    // if (this.component.getDimensions().fixedWidth) {
    // dimensions[1] = -1;
    // } else {
    // dimensions[1] = dimensions[0];
    // }

    // if (this.component.getDimensions().fixedHeight) {
    // dimensions[3] = -1;
    // } else {
    // dimensions[3] = dimensions[2];
    // }

    // if (this.component.hasBorder()) {
    // dimensions[0] += 2;
    // dimensions[2] += 2;

    // if (!this.component.getDimensions().fixedWidth)
    // dimensions[1] += 2;

    // if (!this.component.getDimensions().fixedHeight)
    // dimensions[3] += 2;

    // }
    // return dimensions;
    // }

    public ArrayList<String> constructComponent(int width, int height) {
        Component component = this.component;

        int flexibleComponents = 0;
        int freeSpace = height;
        if (this.component.hasBorder())
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

        for (Component child : this.children) {
            int[] dimension = child.calculateDimensions();
            int componentWidth = dimension[0];
            if (dimension[1] == -1) {
                componentWidth = width;
                if (this.component.hasBorder())
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

        return component.constructComponent(width, height);
    }

}
