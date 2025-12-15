import java.io.*;
import java.util.Arrays;

public class FileManager {
    
    public double[][] importMatrix(String fileName) throws IOException {
        File file = new File(fileName);

        BufferedReader reader = new BufferedReader(new FileReader(file));
        int size = Integer.parseInt(reader.readLine());
        double[][] matrix = new double[size][size+1];

        for (int row = 0; row < size; row++) {
            double[] values = parseRow(reader);
            System.arraycopy(values, 0, matrix[row], 0, size);
        }
        double[] values = parseRow(reader);

        for (int row=0; row<size; ++row) {
            matrix[row][size] = values[row];
        }

        return matrix;
    }

    private double[] parseRow(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        return Arrays.stream(line.split(" "))
            .mapToDouble(Double::parseDouble)
            .toArray();
    }

    public void exportMatrix(double[][] matrix, String fileName) throws IOException {
        int size = matrix.length;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            // Write matrix size
            writer.write(Integer.toString(size));
            writer.newLine();

            // Write coefficient matrix
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    writer.write(Double.toString(matrix[row][col]));
                    if (col < size - 1) {
                        writer.write(" ");
                    }
                }
                writer.newLine();
            }

            // Write RHS vector
            for (int row = 0; row < size; row++) {
                writer.write(Double.toString(matrix[row][size]));
                if (row < size - 1) {
                    writer.write(" ");
                }
            }
            writer.newLine();
        }
    }

}
