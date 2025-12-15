package operations;

public abstract class AbstractOperation implements Runnable{
    protected int sourceRow;
    protected char operationSymbol;
    protected double[][] matrix;
    protected double[][] eliminationFactors;
    protected double[] normalizationFactors;

    AbstractOperation(double[][] matrix, double[][] eliminationFactors, double[] normalizationFactors){
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
