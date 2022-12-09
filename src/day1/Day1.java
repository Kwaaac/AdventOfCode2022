package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {

    private static void firstPuzzle(Stream<String> lines) {

    }

    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day1/inputTest.txt");
        try (var lines = Files.lines(input)) {
             var collect = lines
                    .map(Truc::parseLine)
                    .collect(Collectors.groupingBy(Truc::group))
                    .values()
                    .stream()
                    .mapToInt(trucs -> trucs.stream().mapToInt(Truc::num).sum())
                    // .max() - puzzle 1;
                    .map(i -> -i).sorted().limit(3).sum() * -1;
            System.out.println(collect);
        }
    }

    private record Truc(int num, int group) {
        private static int pouet = 0;

        private Truc(int num, int group) {
            this.num = num;
            if (num == 0) {
                pouet++;
            }
            this.group = group;
        }

        private Truc(int num) {
            this(num, pouet);
        }

        public static Truc parseLine(String s) {
            int i;
            try {
                i = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                i = 0;
            }

            return new Truc(i);
        }
    }
}
