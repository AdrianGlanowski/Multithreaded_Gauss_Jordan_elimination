package operations;

public class FindFactor extends AbstractOperation {
    /*
        Operation A[i][k] represents finding factor that will be needed while subtracting row i from row k.
        factor = M[i][i]/M[k][i]
    */
    private final int targetRow;

    public FindFactor(double[][] matrix, double[][] eliminationFactors, double[] normalizationFactors, int sourceRow, int targetRow){
        super(matrix, eliminationFactors, normalizationFactors);
        this.sourceRow = sourceRow;
        this.targetRow = targetRow;
        this.operationSymbol = 'A';
    }

    @Override
    public void run() {
        eliminationFactors[sourceRow][targetRow] = matrix[sourceRow][sourceRow]/matrix[targetRow][sourceRow];
    }

    @Override
    public String toString(){
        return "%c(%d, %d)".formatted(operationSymbol, sourceRow, targetRow);
    }
}
