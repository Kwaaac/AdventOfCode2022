package day_13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Day13 {
    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day_13/input.txt");
        var lines = Files.readAllLines(input);

        var pairs = new ArrayList<Pair>();

        for (int i = 0; i < lines.size() - 1; i += 3) {
            var left = listParser(new StringIterator(lines.get(i)));
            var right = listParser(new StringIterator(lines.get(i + 1)));
            pairs.add(new Pair(left, right, i / 3 + 1));
        }

        System.out.println(pairs.stream()
                .filter(Pair::rightOrder)
                .mapToInt(Pair::index)
                .sum());
    }

    public static PacketList listParser(StringIterator it) {
        var lst = new ArrayList<Value>();
        String value;
        while (it.hasNext()) {
            value = it.next();
            switch (value) {
                case "[" -> lst.add(listParser(it));
                case "]" -> {
                    return new PacketList(lst);
                }
                case "," -> {/*skip*/}
                default -> lst.add(new PacketInt(Integer.parseInt(value)));
            }
        }

        return new PacketList(lst);
    }
}