package operations;

public class MultiplyElement extends AbstractOperation{
    /*
       Operation B[i][j][k] represents multiplying element M[k][i] by already calculated factor in corresponding FindFactorOperation.
       M[k][j] *= A[i][j]
   */

    private final int column;
    private final int targetRow;


    public MultiplyElement(double[][] matrix, Double[][] eliminationFactors, Double[] normalizationFactors, int sourceRow, int column, int targetRow){
        super(matrix, eliminationFactors, normalizationFactors);
        this.sourceRow = sourceRow;
        this.column = column;
        this.targetRow = targetRow;
        this.operationSymbol = 'B';
    }

    @Override
    public Void call() {
        if (eliminationFactors[sourceRow][targetRow] == null)
            return null;
        else
            matrix[targetRow][column] *= eliminationFactors[sourceRow][targetRow];
        return null;
    }

    @Override
    public String toString(){
        return "%c(%d, %d, %d)".formatted(operationSymbol, sourceRow, column, targetRow);
    }
}
