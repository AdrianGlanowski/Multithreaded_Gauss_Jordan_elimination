package operations;

public class FindFactor extends AbstractOperation {
    /*
        Operation A[i][k] represents finding factor that will be needed while subtracting row i from row k.
        factor = M[i][i]/M[k][i]
    */
    private final int targetRow;

    public FindFactor(double[][] matrix, Double[][] eliminationFactors, Double[] normalizationFactors, int sourceRow, int targetRow){
        super(matrix, eliminationFactors, normalizationFactors);
        this.sourceRow = sourceRow;
        this.targetRow = targetRow;
        this.operationSymbol = 'A';
    }

    @Override
    public Void call() {
        //if target element is 0, then there is no need for multiplying, null is also a reason to use Double instead of double
        if (Math.abs(matrix[targetRow][sourceRow]) < 1e-32)
            eliminationFactors[sourceRow][targetRow] = null;
        else
            eliminationFactors[sourceRow][targetRow] = matrix[sourceRow][sourceRow]/matrix[targetRow][sourceRow];
        return null;
    }

    @Override
    public String toString(){
        return "%c(%d, %d)".formatted(operationSymbol, sourceRow, targetRow);
    }
}
