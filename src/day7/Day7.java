package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Day7 {
    public static void main(String[] args) throws IOException {
        var input = Path.of("src", "day7/input.txt");
        var lines = Files.readAllLines(input);
        Directory tree = createTree(lines.subList(1, lines.size()));
        int size_ =tree.size();
        System.out.println(Directory.sumOfDirectoryWhereSizeTop100_000());

    int max = 70_000_000;
    int target = 30_000_000;

    System.out.println(Directory.sizeOfDirectoryToDelete(target - (max - size_)));
    }

    private static INode InodeFactory(String[] line, Directory current) {
        String name = line[1];
        try {
            int size = Integer.parseInt(line[0]);
            return new File(name, size);
        } catch (NumberFormatException e) {
            return new Directory(name, current);
        }
    }

    private static Directory createTree(List<String> lines) {
        Directory start = new Directory("/", null);
        Directory current = start;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            // lines[0] == '$'
            String[] command = line.split(" ");
            if (command[0].equals("$")) {
                if (command[1].equals("ls")) {
                    int j = 1;
                    line = lines.get(i + j);
                    while (line.charAt(0) != '$' && i + j < lines.size()) {
                        current.add(InodeFactory(line.split(" "), current));

                        j++;
                        if (i + j < lines.size()) {
                            line = lines.get(i + j);
                        }
                    }

                    i += j - 1;
                } else {
                    if (command[2].equals("/")) {
                        current = start;
                        continue;
                    }
                    current = current.cd(command[2]);
                }
            }
        }
        return start;
    }

    private interface INode {
        int size();

        String name();

        default boolean isDir() {
            return false;
        }
    }

    private static class Directory implements INode {
        private final String name;
        private final Directory prev;
        private final HashSet<INode> setDir = new HashSet<>();

        private static final List<INode> allDir = new ArrayList<>();


        public static List<INode> getAllDir() {
            return allDir;
        }

        public static int sumOfDirectoryWhereSizeTop100_000(){
            return allDir.stream().mapToInt(INode::size).filter(size -> size < 100_000).sum();
        }

        public static int sizeOfDirectoryToDelete(int target){
            return allDir.stream().mapToInt(INode::size).peek(System.out::println).filter(size -> size >= target).min().orElseThrow();
        }

        public Directory(String name, Directory prev) {
            this.name = name;
            this.prev = prev;
        }

        @Override
        public int size() {
            int size = setDir.stream().mapToInt(INode::size).sum();
            // System.out.println("Directory " + name + " size: " + size);
            return size;
        }

        public int sizeMost100_000() {
            return 0;
        }

        @Override
        public String name() {
            return name;
        }

        public boolean add(INode node) {
            Objects.requireNonNull(node);
            if(node.isDir()){
                allDir.add(node);
            }
            return setDir.add(node);
        }

        @Override
        public boolean isDir() {
            return true;
        }

        // Je pars du principe qu'on ne fait pas de cd sur un dossier qui n'existe pas
        public Directory cd(String name) {
            if (name.equals("..")) {
                return prev;
            }
            return (Directory) setDir.stream().filter(inode -> name.equals(inode.name())).findFirst().orElseThrow();
        }


        @Override
        public boolean equals(Object o) {
            return o instanceof Directory dir && dir.name.equals(name) && dir.setDir.equals(setDir);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, setDir);
        }
    }

    private record File(String name, int size) implements INode {
        @Override
        public int size() {
            // System.out.println("File " + name + " size: " + size);
            return size;
        }
    }
}
