package day_10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day10_2 {

    private static void printCRT(int cycle, int registry) {
        if ((cycle-1) % 40 == registry - 1 || (cycle-1) % 40 == registry || (cycle-1) % 40 == registry + 1) System.out.print("#");
        else System.out.print(".");

        if(cycle%40==0){
            System.out.println();
        }
    }

    public static int printCycle(int cycle, int registry) {
        printCRT(cycle, registry);
        if (cycle % 40 == 20) {
            // System.out.println(cycle + " * " + registry + " = " + cycle * registry);
            return registry * cycle;
        }

        return 0;
    }

    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day_10/input.txt");
        var lines = Files.readAllLines(input);

        int cycle = 1;
        int registry = 1;
        int res = 0;

        for (var line : lines) {
            res += printCycle(cycle, registry);

            var command = line.split(" ");
            if (command.length == 2) {
                cycle++;
                res += printCycle(cycle, registry);
                var instruction = command[0];
                var value = Integer.parseInt(command[1]);

                registry += value;
            }
            cycle++;
        }

        System.out.println("\nres = " + res);

    }
}
