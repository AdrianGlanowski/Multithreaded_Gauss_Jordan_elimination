package operations;

public class SubtractElement extends AbstractOperation{
    /*
        Operation C[i][j][k] represents subtracting j column in i row from j column in k row.
        M[j][k] -= M[i][k]
    */
    private final int column;
    private final int targetRow;

    public SubtractElement(double[][] matrix, double[][] eliminationFactors, double[] normalizationFactors, int sourceRow, int column, int targetRow){
        super(matrix, eliminationFactors, normalizationFactors);
        this.sourceRow = sourceRow;
        this.column = column;
        this.targetRow = targetRow;
        this.operationSymbol = 'C';
    }

    @Override
    public void run() {
        matrix[targetRow][column] -= matrix[sourceRow][column];
    }

    @Override
    public String toString(){
        return "%c(%d, %d, %d)".formatted(operationSymbol, sourceRow, column, targetRow);
    }
}
