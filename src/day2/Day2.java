package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day2 {

    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day2", "input1.txt");

        try (var lines = Files.lines(input)) {
            var result = lines.map(Match::factoFromLine)
                    .mapToInt(Match::fightSmart)
                    .sum();

            System.out.println(result);
        }

    }

    private record Match(int o, int p) {

        public static Match factoFromLine(String line) {
            int o = line.charAt(0);
            int p = line.charAt(2);
            return new Match(o, p);
        }

        public int fight() {
            return p - 87 + ((p - o - 1) % 3) * 3;
        }

        public int fightSmart() {
            return (o + p - 1) % 3 + 1 + ((p - 1) % 3) * 3;
        }
    }
}
