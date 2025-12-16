package operations;

public class NormalizeElement extends AbstractOperation{
    /*
        Operation E[i][j] represents multiplying element by normalization factor.
        M[i][j] = factor*M[i][j]
    */

    private final int column;
    public NormalizeElement(double[][] matrix, Double[][] eliminationFactors, Double[] normalizationFactors, int sourceRow, int column){
        super(matrix, eliminationFactors, normalizationFactors);
        this.sourceRow = sourceRow;
        this.column = column;
        this.operationSymbol = 'E';
    }

    @Override
    public Void call() {
        matrix[sourceRow][column] *= normalizationFactors[sourceRow];
        return null;
    }

    @Override
    public String toString(){
        return "%c(%d, %d)".formatted(operationSymbol, sourceRow, column);
    }
}
