package day_11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.List;
import java.util.function.*;

public class Day11 {
    private record Monkey(ArrayDeque<Integer> items, IntFunction<Integer> operation, IntConsumer throwing) {

        private static Monkey[] monkeys;
        private static int[] inspection;

        private static boolean DEBUG = true;

        public static void initMonkeys(int size) {
            monkeys = new Monkey[size];
            inspection = new int[size];
        }

        public static void addMonkey(Monkey monkey, int index) {
            monkeys[index] = monkey;
        }

        public void inspect(int nbrMonkey) {
            for (int i = 0; i < items.size(); i++) {
                int item = items.pollFirst();
                if (DEBUG) System.out.println("\tMonkey inspects an item with a worry level of " + item);
                item = operation().apply(item);
                throwing.accept(item);
                inspection[nbrMonkey]++;
            }
        }

        public void addItem(int item) {
            items.add(item);
        }

        public static void round() {
            for (int i = 0; i < monkeys.length; i++) {
                System.out.println("Monkey " + i + ":");
                Monkey monkey = monkeys[i];
                monkey.inspect(i);
            }
        }

        private static int intOrElse(int item, String value) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return item;
            }
        }

        public static Monkey monkeyFactory(List<String> lines) {
            // Item stack
            String[] strItems = lines.get(1).split(": ")[1].split(", ");
            ArrayDeque<Integer> items = new ArrayDeque<>();
            for (var item : strItems) {
                items.add(Integer.parseInt(item));
            }

            // Operation
            var ops = lines.get(2).split("= old ")[1].split(" ");
            IntFunction<Integer> operation = switch (ops[0]) {
                case "*" -> (item) -> {
                    int newItem = item * intOrElse(item, ops[1]);
                    if (DEBUG) System.out.println("\t\tWorry level is multiplied by " + ops[1] + " to " + newItem);
                    return newItem;
                };
                case "+" -> (item) -> {
                    int newItem = item + intOrElse(item, ops[1]);
                    if (DEBUG) System.out.println("\t\tWorry level is increases by " + ops[1] + " to " + newItem);
                    return newItem;
                };
                default -> throw new AssertionError("Unknown operation");
            };

            // Throwing action
            int divisor = Integer.parseInt(lines.get(3).split("by ")[1]);
            int throwTrue = Integer.parseInt(lines.get(4).split("monkey ")[1]);
            int throwFalse = Integer.parseInt(lines.get(5).split("monkey ")[1]);

            IntConsumer throwing = (item) -> {
                if (DEBUG)
                    System.out.println("\t\tMonkey gets bored with item. Worry level is divided by 3 to " + item / 3);
                if (item % divisor == 0) {
                    if (DEBUG) {
                        System.out.println("\t\tCurrent worry level is divisible by " + divisor);
                        System.out.println("\t\tItem with worry level " + item / 3 + " is thrown to monkey " + throwTrue);
                    }

                    monkeys[throwTrue].addItem(item / 3);
                } else {
                    if (DEBUG) {
                        System.out.println("\t\tCurrent worry level is not divisible by " + divisor);
                        System.out.println("\t\tItem with worry level " + item / divisor + " is thrown to monkey " + throwFalse);
                    }
                    monkeys[throwFalse].addItem(item / divisor);
                }
            };

            return new Monkey(items, operation, throwing);
        }
    }

    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day_11/inputTest.txt");
        var lines = Files.readAllLines(input);
        var nbrMonkeys = (int) lines.stream().filter(String::isEmpty).count();
        Monkey.initMonkeys(nbrMonkeys);

        for (int i = 0, start = 0; i < nbrMonkeys; i++, start += 7) {
            var line = lines.subList(start, start + 7);
            var monkey = Monkey.monkeyFactory(line);
            Monkey.addMonkey(monkey, i);
        }

        Monkey.round();
    }
}
