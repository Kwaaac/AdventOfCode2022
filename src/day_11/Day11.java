package day_11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;

public class Day11 {
    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day_11/inputTest.txt");
        var lines = Files.readAllLines(input);
        var nbrMonkeys = (int) lines.stream().filter(String::isEmpty).count() + 1;
        Monkey.initMonkeys(nbrMonkeys);

        for (int i = 0, start = 0; i < nbrMonkeys; i++, start += 7) {
            var line = subListClosed(lines, start, start + 7);
            var monkey = Monkey.monkeyFactory(line);
            Monkey.addMonkey(monkey, i);
        }

        for (int i = 0; i < 20; i++) {
            System.out.println("Round " + (i + 1));
            Monkey.round();
        }

        for (int i = 0; i < Monkey.inspection.length; i++) {
            System.out.println("Monkey "+ i +" inspected items " + Monkey.inspection[i]+ " times.");
        }

        Arrays.sort(Monkey.inspection);

        System.out.println(Monkey.inspection[nbrMonkeys - 1] * Monkey.inspection[nbrMonkeys - 2]);
    }

    public static <T> List<T> subListClosed(List<T> list, int fromIndex, int toIndex) {
        if (toIndex > list.size()) {
            return list.subList(fromIndex, list.size());
        }

        return list.subList(fromIndex, toIndex);
    }

    private record Monkey(ArrayDeque<Long> items, LongFunction<Long> operation, LongConsumer throwing) {

        private static Monkey[] monkeys;
        private static int[] inspection;

        private static final boolean DEBUG = false;

        public static void initMonkeys(int size) {
            monkeys = new Monkey[size];
            inspection = new int[size];
        }

        public static void addMonkey(Monkey monkey, int index) {
            monkeys[index] = monkey;
        }

        public static void round() {
            for (int i = 0; i < monkeys.length; i++) {
                if (DEBUG) System.out.println("Monkey " + i + ":");
                Monkey monkey = monkeys[i];
                monkey.inspect(i);
            }

            for (int i = 0; i < monkeys.length; i++) {
                System.out.println("\tMonkey " + i + ":" + monkeys[i].items);
            }

        }

        private static Long longOrElse(String value, Long defaultValue) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        public static Monkey monkeyFactory(List<String> lines) {
            // Item stack
            String[] strItems = lines.get(1).split(": ")[1].split(", ");
            ArrayDeque<Long> items = new ArrayDeque<>();
            for (var item : strItems) {
                items.add(Long.parseLong(item));
            }

            // Operation
            var ops = lines.get(2).split("= old ")[1].split(" ");
            LongFunction<Long> operation = switch (ops[0]) {
                case "*" -> (item) -> {
                    var newItem = item * longOrElse(ops[1], item);
                    if (DEBUG) System.out.println("\t\tWorry level is multiplied by " + ops[1] + " to " + newItem);
                    return newItem;
                };
                case "+" -> (item) -> {
                    var newItem = item + longOrElse(ops[1], item);
                    if (DEBUG) System.out.println("\t\tWorry level is increases by " + ops[1] + " to " + newItem);
                    return newItem;
                };
                default -> throw new AssertionError("Unknown operation");
            };

            // Throwing action
            long divisor = Long.parseLong(lines.get(3).split("by ")[1]);
            int throwTrue = Integer.parseInt(lines.get(4).split("monkey ")[1]);
            int throwFalse = Integer.parseInt(lines.get(5).split("monkey ")[1]);

            LongConsumer throwing = (item) -> {
                //if (item > supermodulo) item /= supermodulo;
                item /= 3;
                if (DEBUG)
                    System.out.println("\t\tMonkey gets bored with item. Worry level is divided by 3 to " + item);
                if (item % divisor == 0) {
                    if (DEBUG) {
                        System.out.println("\t\tCurrent worry level is divisible by " + divisor);
                        System.out.println("\t\tItem with worry level " + item + " is thrown to monkey " + throwTrue);
                    }
                    monkeys[throwTrue].addItem(item);
                } else {
                    if (DEBUG) {
                        System.out.println("\t\tCurrent worry level is not divisible by " + divisor);
                        System.out.println("\t\tItem with worry level " + item + " is thrown to monkey " + throwFalse);
                    }
                    monkeys[throwFalse].addItem(item);
                }
            };

            return new Monkey(items, operation, throwing);
        }

        public void inspect(int nbrMonkey) {
            inspection[nbrMonkey] += items.size();
            for (var item : items) {
                if (DEBUG) System.out.println("\tMonkey inspects an item with a worry level of " + item);
                item = operation().apply(item);
                throwing.accept(item);
            }
            items.clear();
        }

        public void addItem(long item) {
            items.add(item);
        }
    }
}
