package day_13;

import java.util.List;

public record PacketList(List<Value> list) implements Value {

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    public int size() {
        return list.size();
    }

    public int deepSize() {
        return list.stream().mapToInt(Value::deepSize).sum();
    }

    @Override
    public int stringSize() {
        return list.stream().mapToInt(Value::stringSize).sum() + 2;
    }

    public boolean isRightOrder(PacketList right) {
        return compareOrder(right) < 0;
    }

    public int compareOrder(PacketList right) {
        int flag = 0;

        int leftSize = size();
        int rightSize = right.size();
        int minSize = Math.min(leftSize, rightSize);

        for (int i = 0; i < minSize && flag == 0; i++) {
            flag = Value.compareOrder(list.get(i), right.list.get(i));
        }

        if (flag == 0) {
            return Integer.compare(leftSize, rightSize);
        }

        return flag;
    }
}
