package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day8 {
    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day8/input.txt");
        var lines = Files.readAllLines(input);

        Forest x = Forest.ForestFromInput(lines);
        System.out.println(x);
        /*
         x.buildVisible();


         var x1 = Arrays.deepToString(Arrays.stream(x.matrix()).map(line -> Arrays.stream(line).map(Tree::isHidden).toArray()).toArray()).replaceAll("], \\[", "], \n\\[");
         System.out.println(x1.substring(1, x1.length()-1).replaceAll("e, ", "e,\t"));

        System.out.println(Arrays.stream(x.matrix())
                .flatMap(value -> Arrays
                        .stream(value)
                        .map(Tree::isHidden))
                .filter(value -> !value)
                .count());
         */
        System.out.println();

        x = x.buildScenicView();
        var x1 = Arrays.deepToString(Arrays.stream(x.matrix()).map(line -> Arrays.stream(line).map(Tree::height).toArray()).toArray()).replaceAll("], \\[", "], \n\\[");
        System.out.println(x1);
        System.out.println(Arrays.stream(x.matrix())
                .flatMapToInt(line -> Arrays.stream(line)
                        .mapToInt(Tree::height))
                .max().orElseThrow());
    }
}
