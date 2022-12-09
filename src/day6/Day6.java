package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class Day6 {
    public static void main(String[] args) throws IOException {

        System.out.println((int) '$');

        var input = Path.of("src", "day6/input.txt");
        var lines = Files.readAllLines(input).stream().map(str -> str.chars().toArray()).toList();

        var set = new HashSet<Integer>();
        // var marker = 4; // Puzzle 1
        var marker = 14;

        for (var line : lines) { // Mostly for the test input
            set.clear();
            int count = 0;

            for (int i = 0; i < line.length - marker; i++, count++) {
                for (int j = 0; j < marker; j++) {

                    int val = line[i+j];
                    var inserted = set.add(val);

                    if (!inserted) {
                        set.clear();
                        break;
                    }

                    if (set.size() == marker) {
                        System.out.println(count + j + 1);
                        i = line.length;
                    }
                }
            }
        }
    }
}
