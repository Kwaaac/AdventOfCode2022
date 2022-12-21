package day_13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Day13 {
    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day_13/input.txt");
        try (var lines = Files.lines(input)) {

            var paquets = lines.filter(line -> line.startsWith("[")).toArray(String[]::new);
            var pairs = new ArrayList<Pair>();
            
            for (int i = 0; i < paquets.length; i += 2) {
                var left = listParser(new StringIterator(paquets[i]));
                var right = listParser(new StringIterator(paquets[i + 1]));
                pairs.add(new Pair(left, right, i / 2 + 1));
            }

            System.out.println(pairs.stream()
                    .filter(Pair::rightOrder)
                    .mapToInt(Pair::index)
                    .sum());
        }
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