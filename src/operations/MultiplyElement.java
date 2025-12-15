package operations;

public class MultiplyElement extends AbstractOperation{
    /*
       Operation B[i][j][k] represents multiplying element M[k][i] by already calculated factor in corresponding FindFactorOperation.
       M[k][j] *= A[i][j]
   */

    private final int column;
    private final int targetRow;


    public MultiplyElement(double[][] matrix, double[][] eliminationFactors, double[] normalizationFactors, int sourceRow, int column, int targetRow){
        super(matrix, eliminationFactors, normalizationFactors);
        this.sourceRow = sourceRow;
        this.column = column;
        this.targetRow = targetRow;
        this.operationSymbol = 'B';
    }

    @Override
    public void run() {
        matrix[targetRow][column] *= eliminationFactors[sourceRow][targetRow];
    }

    @Override
    public String toString(){
        return "%c(%d, %d, %d)".formatted(operationSymbol, sourceRow, column, targetRow);
    }
}
