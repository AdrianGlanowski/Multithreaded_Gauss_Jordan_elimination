import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {

        String filename = "100x100";
        FileManager fileManager = new FileManager();
        double[][] matrix = fileManager.importMatrix("examples/input/%s.txt".formatted(filename));

        GaussJordanElimination gaussJordanElimination = new GaussJordanElimination();
        long start = System.nanoTime();
        try {
            gaussJordanElimination.solve(matrix);
        } catch (InterruptedException ignore) {
        }
        long end = System.nanoTime();
        System.out.println("TIME [s]:");
        System.out.println((end-start)/1e9);

        fileManager.exportMatrix(matrix, "examples/output/%s.txt".formatted(filename));

    }
}

