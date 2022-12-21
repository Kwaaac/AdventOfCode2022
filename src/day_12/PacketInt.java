package day_12;

import java.util.List;

public record PacketInt(int value) implements Value {

    public PacketList asList() {
        return new PacketList(List.of(this));
    }

    @Override
    public int stringSize() {
        return size();
    }

    public int compareOrder(PacketInt right) {
        return Integer.compare(value, right.value);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public int deepSize() {
        return size();
    }
}
