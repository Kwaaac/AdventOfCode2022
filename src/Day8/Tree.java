package Day8;

public record Tree(boolean hiddenLeft,
                   boolean hiddenRight,
                   boolean hiddenUp,
                   boolean hiddenDown,
                   int height) {

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public Tree(int height) {
        this(false, false, false, false, height);
    }

    public static Tree TreeFactoryHiddenDirection(Tree t, Direction direction) {
        return switch (direction) {
            case LEFT -> new Tree(true, t.hiddenRight, t.hiddenUp, t.hiddenDown, t.height);
            case RIGHT -> new Tree(t.hiddenLeft, true, t.hiddenUp, t.hiddenDown, t.height);
            case UP -> new Tree(t.hiddenLeft, t.hiddenRight, true, t.hiddenDown, t.height);
            case DOWN -> new Tree(t.hiddenLeft, t.hiddenRight, t.hiddenUp, true, t.height);
        };
    }

    public boolean isHidden() {
        return hiddenLeft && hiddenUp && hiddenRight && hiddenDown;
    }

    @Override
    public String toString() {
        return height + "";
    }
}