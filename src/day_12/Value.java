package day_12;

public sealed interface Value permits PacketInt, PacketList {
    default boolean isList() {
        return false;
    }
    int size();

    int deepSize();

    int stringSize();

    static int compareOrder(Value left, Value right) {
        return switch (left) {
            case PacketInt intLeft -> switch (right) {
                case PacketInt intRight -> intLeft.compareOrder(intRight);
                case PacketList listRight -> compareOrder(intLeft.asList(), listRight);
            };

            case PacketList listLeft -> switch (right) {
                case PacketInt intRight -> compareOrder(listLeft, intRight.asList());
                case PacketList listRight -> listLeft.compareOrder(listRight);
            };
        };
    }
}
