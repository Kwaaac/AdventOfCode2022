package Day8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public record Forest(Tree[][] matrix) {

    public static Forest ForestFromInput(List<String> lines) {
        int m = lines.size();

        Tree[][] matrix = new Tree[m][m];

        for (int i = 0; i < m; i++) {
            var line = lines.get(i).toCharArray();
            for (int j = 0; j < m; j++) {
                int height = Integer.parseInt(String.valueOf(line[j]));
                matrix[i][j] = new Tree(height);
            }
        }

        return new Forest(matrix);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(matrix).replaceAll("], \\[", "],\n[");
    }

    public void updateVisible(int x, int y) {

        // right
        if (IntStream.range(y + 1, matrix.length).anyMatch(j -> matrix[x][y].height() <= matrix[x][j].height()))
            matrix[x][y] = Tree.TreeFactoryHiddenDirection(matrix[x][y], Tree.Direction.RIGHT);

        // left
        for (int j = y - 1; j >= 0; j--) {
            if (!matrix[x][y].hiddenLeft() && matrix[x][y].height() <= matrix[x][j].height()) {
                matrix[x][y] = Tree.TreeFactoryHiddenDirection(matrix[x][y], Tree.Direction.LEFT);
            }
        }

        // down
        if (IntStream.range(x + 1, matrix.length).anyMatch(i -> matrix[x][y].height() <= matrix[i][y].height()))
            matrix[x][y] = Tree.TreeFactoryHiddenDirection(matrix[x][y], Tree.Direction.DOWN);


        // up
        for (int i = x - 1; i >= 0; i--) {
            if (!matrix[x][y].hiddenUp() && matrix[x][y].height() <= matrix[i][y].height()) {
                matrix[x][y] = Tree.TreeFactoryHiddenDirection(matrix[x][y], Tree.Direction.UP);
            }
        }
    }

    public Tree updateViewDistance(int x, int y) {
        if (x == 0 || x == matrix.length - 1 || y == 0 || y == matrix.length - 1) {
            return new Tree(0);

        }


        int right = 0;
        for (int j = y + 1; j < matrix.length; j++) {
            right++;
            if (matrix[x][y].height() <= matrix[x][j].height()) {
                break;
            }
        }

        int down = 0;
        for (int i = x + 1; i < matrix.length; i++) {
            down++;
            if (matrix[x][y].height() <= matrix[i][y].height()) {
                break;
            }
        }

        int left = 0;
        for (int j = y - 1; j >= 0; j--) {
            left++;
            if (matrix[x][y].height() <= matrix[x][j].height()) {
                break;
            }
        }

        int up = 0;
        for (int i = x - 1; i >= 0; i--) {
            up++;
            if (matrix[x][y].height() <= matrix[i][y].height()) {
                break;
            }
        }

        return new Tree(left * right * up * down);
    }

    public Forest buildScenicView() {
        Tree[][] res = new Tree[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                res[i][j] = updateViewDistance(i, j);
            }
        }

        return new Forest(res);
    }

    public void buildVisible() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (!(i == 0 || i == matrix.length - 1 || j == 0 || j == matrix.length - 1))
                    updateVisible(i, j);
            }
        }
    }
}