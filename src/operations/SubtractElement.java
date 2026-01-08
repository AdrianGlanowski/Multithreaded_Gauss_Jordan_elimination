package operations;

public class SubtractElement extends AbstractOperation{
    /*
        Operation C[i][j][k] represents subtracting j column in i row from j column in k row.
        M[j][k] -= M[i][k]
    */
    private final int column;
    private final int targetRow;

    public SubtractElement(double[][] matrix, Double[][] eliminationFactors, Double[] normalizationFactors, int sourceRow, int column, int targetRow){
        super(matrix, eliminationFactors, normalizationFactors);
        this.sourceRow = sourceRow;
        this.column = column;
        this.targetRow = targetRow;
        this.operationSymbol = 'C';
    }

    @Override
    public Void call() {
        //target element is 0 and there is no need for row multiplying
        if (eliminationFactors[sourceRow][targetRow] != null)
            if (column != sourceRow)
                matrix[targetRow][column] -= matrix[sourceRow][column];
            else //it is known for those elements to be 0 after subtracting
                matrix[targetRow][column] = 0; //stability
        return null;
    }

    @Override
    public String toString(){
        return "%c(%d, %d, %d)".formatted(operationSymbol, sourceRow, column, targetRow);
    }
}
