package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day4 {
    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day4/input.txt");
        try (var lines = Files.lines(input)) {
            System.out.println(lines.map(Pair::PairFromInput).mapToInt(Pair::contain).sum());
            System.out.println(lines.map(Pair::PairFromInput).mapToInt(Pair::overlap).sum());
        }
    }

    private record Range(int start, int end) {

        public static Range RangeFromInput(String input) {
            var str = input.split("-");
            int s = Integer.parseInt(str[0]);
            int e = Integer.parseInt(str[1]);
            return new Range(s, e);
        }
    }

    private record Pair(Range first, Range second) {
        public static Pair PairFromInput(String input) {
            var str = input.split(",");
            return new Pair(Range.RangeFromInput(str[0]), Range.RangeFromInput(str[1]));
        }

        // Puzzle 1
        public int contain() {
            if (first.start() <= second.start() && first.end() >= second.end() || second.start() <= first.start() && second.end() >= first.end()) {
                return 1;
            }

            return 0;
        }

        // Puzzle 2
        public int overlap() {
            var range = IntStream.rangeClosed(second.start(), second.end()).boxed().toList();
            return IntStream.rangeClosed(first().start, first().end).anyMatch(range::contains) ? 1 : 0;
        }
    }
}
