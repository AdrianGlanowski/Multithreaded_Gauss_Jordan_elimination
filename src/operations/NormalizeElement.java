package operations;

public class NormalizeElement extends AbstractOperation{
    /*
        Operation E[i][j] represents multiplying element by normalization factor.
        M[i][j] = factor*M[i][j]
    */

    private final int column;
    public NormalizeElement(double[][] matrix, double[][] eliminationFactors, double[] normalizationFactors, int sourceRow, int column){
        super(matrix, eliminationFactors, normalizationFactors);
        this.sourceRow = sourceRow;
        this.column = column;
        this.operationSymbol = 'E';
    }

    @Override
    public void run() {
        matrix[sourceRow][column] *= normalizationFactors[sourceRow];
    }

    @Override
    public String toString(){
        return "%c(%d, %d)".formatted(operationSymbol, sourceRow, column);
    }
}
