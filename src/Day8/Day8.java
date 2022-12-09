package Day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day8 {


    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "Day8/inputTest.txt");
        var lines = Files.readAllLines(input);

        Forest x = Forest.ForestFromInput(lines);
        System.out.println(x);
        x.buildVisible();

        System.out.println(Arrays.stream(x.matrix())
                .flatMap(value -> Arrays
                        .stream(value)
                        .peek(System.out::println)
                        .map(Tree::isHidden))
                .filter(value -> value)
                .count());
    }
}
