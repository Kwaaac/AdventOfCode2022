package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day5/input.txt");
        var lines = Files.readAllLines(input);
        int split = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isEmpty()) {
                split = i;
            }
        }

        var stock = lines.subList(0, split);
        var instructions = lines.subList(split + 1, lines.size());

        var stack = new StackCrates(Integer.parseInt(String.valueOf(stock.get(stock.size() - 1).charAt(stock.get(0).length() - 2))));
        stack.fillStackCrate(stock);

        instructions.stream()
                .map(value -> value.replaceAll("[^\\d ]", "").strip().split("  "))
                .forEach(tab -> stack.moveStackCrate9001(Integer.parseInt(tab[0]), Integer.parseInt(tab[1]) - 1, Integer.parseInt(tab[2]) - 1));

        System.out.println(stack.getTopStacks());
    }

    private record StackCrates(int size) {
        private static final ArrayList<ArrayDeque<String>> stacks = new ArrayList<>();

        private StackCrates(int size) {
            this.size = size;
            for (int i = 0; i < size; i++) {
                stacks.add(new ArrayDeque<>());
            }
        }

        public void fillStackCrate(List<String> lines) {
            for (int i = 0; i < lines.size() - 1; i++) {
                String line = lines.get(i);
                for (int j = 1, stack = 0; j <= line.length() - 1; j += 4, stack++) {
                    var crate = String.valueOf(line.charAt(j));
                    if (!crate.isBlank()) {
                        stacks.get(stack).addFirst(String.valueOf(line.charAt(j)));
                    }
                }
            }
        }

        public void moveStackCrate(int moves, int src, int dst) {
            var stack_dst = stacks.get(dst);
            var stack_src = stacks.get(src);
            for (int i = 0; i < moves; i++) {
                stack_dst.add(Objects.requireNonNull(stack_src.pollLast()));
            }
        }

        public void moveStackCrate9001(int moves, int src, int dst) {
            var stck = new ArrayDeque<String>();
            var stack_dst = stacks.get(dst);
            var stack_src = stacks.get(src);

            for (int i = 0; i < moves; i++) {
                stck.add(Objects.requireNonNull(stack_src.pollLast()));
            }

            for (int i = 0; i < moves; i++) {
                stack_dst.add(Objects.requireNonNull(stck.pollLast()));
            }
        }

        public String getTopStacks() {
            return stacks.stream().map(Deque::peekLast).collect(Collectors.joining());
        }

        @Override
        public String toString() {
            return "size=" + size + " :: " + stacks;
        }
    }
}
