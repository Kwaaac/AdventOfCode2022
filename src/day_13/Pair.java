package day_13;

public record Pair(PacketList left, PacketList right, int index) {
    public boolean rightOrder() {
        return left.isRightOrder(right);
    }
}
