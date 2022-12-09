package Day8;

import java.util.Arrays;
import java.util.List;

public record Forest(Tree[][] matrix) {
    public static boolean[][] visibles;

    public static Forest ForestFromInput(List<String> lines) {
        int m = lines.size();

        visibles = new boolean[m][m];
        for (int i = 0; i < m; i++) {
            Arrays.fill(visibles[i], true);
        }

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

    private void updateVisible(int x, int y) {
        Tree tree = matrix[x][y];

        //left and up
        for (int i = x - 1; i >= 0 || (tree.isHiddenLeft() && tree.isHiddenUp()); i--) {
            if (!tree.isHiddenLeft() && tree.height() < matrix[x][i].height()) {
                tree.setHiddenLeft(true);
            }

            if (!tree.isHiddenUp() && tree.height() < matrix[i][y].height()) {
                tree.setHiddenUp(true);
            }
        }

        //right and down
        for (int i = x + 1; i < matrix.length || (tree.isHiddenRight()) && tree.isHiddenDown(); i++) {
            if (!tree.isHiddenRight() && tree.height() < matrix[x][i].height()) {
                tree.setHiddenRight(true);
            }

            if (!tree.isHiddenDown() && tree.height() < matrix[i][y].height()) {
                tree.setHiddenDown(true);
            }
        }
    }

    public void buildVisible() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                updateVisible(i, j);
            }
        }

    }
}