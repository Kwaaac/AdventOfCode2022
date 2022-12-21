package day_12;

public record Pair(PacketList left, PacketList right, int index) {
    public boolean rightOrder() {
        return left.isRightOrder(right);
    }
}
