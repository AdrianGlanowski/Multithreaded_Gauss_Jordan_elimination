package operations;

import java.util.concurrent.Callable;

public abstract class AbstractOperation implements Callable<Void> {
    protected int sourceRow;
    protected char operationSymbol;
    protected double[][] matrix;
    protected Double[][] eliminationFactors;
    protected Double[] normalizationFactors;

    AbstractOperation(double[][] matrix, Double[][] eliminationFactors, Double[] normalizationFactors){
        this.matrix = matrix;
        this.eliminationFactors = eliminationFactors;
        this.normalizationFactors = normalizationFactors;
    }

    public static void printMatrix(double[][] matrix) {
        System.out.print("MATRIX START\n");
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println(); // newline after each row
        }
        System.out.print("MATRIX END\n");
    }
}
