package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class Day9_2 {
    public static void printRope(Position[] rope){
        var l = 5;
        var c = 6;

        var matrix = new String[l][c];

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                matrix[i][j] = ".";
            }
        }

        for (int i = rope.length - 1; i >= 0; i--) {
            var pos = rope[i];
            if(i == 0){
                matrix[pos.x][pos.y] = "H";
            } else if( i == 9){
                matrix[pos.x][pos.y] = "T";
            } else {
                matrix[pos.x][pos.y] = String.valueOf(i);
            }
        }
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day9/inputTest.txt");
        var lines = Files.readAllLines(input);

        var res = new HashSet<Position>();
        var rope = new Position[11];
        var lastRope = new Position[11];

        Position lastPosition = null;

        // rope[0] == head
        // rope[10] == tail
        for (int i = 0; i < 11; i++) {
            rope[i] = new Position(4, 0);
            lastRope[i] = new Position(4, 0);
        }

        res.add(new Position(4, 0));
        printRope(rope);
        for (var line : lines) {
            var split = line.split(" ");
            var direction = split[0];
            int movements = Integer.parseInt(split[1]);

            for (int i = 0; i < movements; i++) {
                System.out.println("== " + direction + " " + i + " / " + movements + " ==");
                printRope(rope);
                lastRope[0] = new Position(rope[0]);
                rope[0] = rope[0].move(direction);
                int k = 1;
                for (; k < rope.length; k++) {
                    lastRope[k] = new Position(rope[k]);
                    var distance = Position.distance(rope[k-1], rope[k]);
                    if (distance > 1) {
                        rope[k] = new Position(lastRope[k-1]);
                        if (k == 9)
                            res.add(new Position(lastRope[k-1]));
                    }
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
