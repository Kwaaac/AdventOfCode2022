package Day8;

import org.w3c.dom.ls.LSOutput;

public class Tree {
    private boolean hiddenLeft;
    private boolean hiddenRight;
    private boolean hiddenUp;
    private boolean hiddenDown;

    private final int height;

    public Tree(int height) {
        this.height = height;
    }

    public boolean isHidden() {
        return hiddenLeft && hiddenUp && hiddenRight && hiddenDown;
    }

    public boolean isHiddenLeft() {
        return hiddenLeft;
    }

    public void setHiddenLeft(boolean hiddenLeft) {
        this.hiddenLeft = hiddenLeft;
    }

    public boolean isHiddenRight() {
        return hiddenRight;
    }

    public void setHiddenRight(boolean hiddenRight) {
        this.hiddenRight = hiddenRight;
    }

    public boolean isHiddenUp() {
        return hiddenUp;
    }

    public void setHiddenUp(boolean hiddenUp) {
        this.hiddenUp = hiddenUp;
    }

    public boolean isHiddenDown() {
        return hiddenDown;
    }

    public void setHiddenDown(boolean hiddenDown) {
        this.hiddenDown = hiddenDown;
    }

    public int height() {
        return height;
    }

    @Override
    public String toString() {
        return height + "";
    }
}