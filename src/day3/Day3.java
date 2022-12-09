package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3<T> {

    private static int priority(char letter) {
        if (letter < 'a') {
            return letter - 38;
        }

        return letter - 96;
    }

    private static int giveItemPriority(String sac) {
        int mid = sac.length() / 2;
        var first = sac.substring(0, mid);
        var second = sac.substring(mid);

        for (var c : first.toCharArray()) {
            if (second.contains(c + "")) {
                return priority(c);
            }
        }
        return 0;
    }

    public static void first() throws IOException {
        var input = Path.of("src", "day3/input.txt");
        try (var lines = Files.lines(input)) {
            System.out.println(lines.mapToInt(Day3::giveItemPriority).sum());
        }
    }

    private static String giveItemSimilar2(String first, String second) {
        return first.chars().mapToObj(value -> (char) value + "").filter(second::contains).collect(Collectors.joining());
    }

    private static Stream<List<String>> groupLines(List<String> lines) {
        List<List<String>> groupedLines = new ArrayList<>();

        for (int j = 0; j < lines.size(); j += 3) {
            var bob = new ArrayList<String>();
            bob.add(lines.get(j));
            bob.add(lines.get(j + 1));
            bob.add(lines.get(j + 2));
            groupedLines.add(bob);
        }

        return groupedLines.stream();
    }

    private static int getBadgePriotity(List<String> lines) {
        return priority(lines.stream().reduce(lines.get(0), Day3::giveItemSimilar2).charAt(0));
    }

    public static void second() throws IOException {
        var input = Path.of("src", "day3/input.txt");
        var lines = Files.readAllLines(input);
        System.out.println(groupLines(lines).mapToInt(Day3::getBadgePriotity).peek(System.out::println).sum());
    }

    public static void main(String[] args) throws IOException {
        second();
    }
}
