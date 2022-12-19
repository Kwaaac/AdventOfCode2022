package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class Day9 {
    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day9/input.txt");
        var lines = Files.readAllLines(input);

        var res = new HashSet<Position>();

        Position lastPosition = null;

        var head = new Position(0, 0);
        var tail = new Position(0, 0);
        res.add(new Position(0, 0));

        for (var line : lines) {
            var split = line.split(" ");
            var direction = split[0];
            int movements = Integer.parseInt(split[1]);

            for (int i = 0; i < movements; i++) {
                lastPosition = new Position(head);
                head = head.move(direction);
                var distance = Position.distance(head, tail);
                if (distance > 1) {
                    tail = new Position(lastPosition);
                    res.add(new Position(lastPosition));
                }
            }
        }

        System.out.println(res.size());
    }

    public record Position(int x, int y) {
        public Position(Position pos) {
            this(pos.x, pos.y);
        }

        public static double distance(Position head, Position tail) {
            return Math.floor(Math.sqrt((head.x - tail.x) * (head.x - tail.x) + (head.y - tail.y) * (head.y - tail.y)));
        }

        public Position move(String dir) {
            return switch (Direction.valueOf(dir)) {
                case L -> new Position(x, y - 1);
                case R -> new Position(x, y + 1);
                case U -> new Position(x - 1, y);
                case D -> new Position(x + 1, y);
            };
        }

        private enum Direction {
            L, R, U, D
        }
    }
}