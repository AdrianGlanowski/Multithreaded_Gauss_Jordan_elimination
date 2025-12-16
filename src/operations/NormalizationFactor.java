package operations;

public class NormalizationFactor extends AbstractOperation{
    /*
        Operation D[i] represents finding factor that will be needed for normalizing for i.
        factor = 1/M[i][i]
    */


    public NormalizationFactor(double[][] matrix, double[][] eliminationFactors, double[] normalizationFactors, int sourceRow){
        super(matrix, eliminationFactors, normalizationFactors);
        this.sourceRow = sourceRow;
        this.operationSymbol = 'D';
    }

    @Override
    public Void call() {
        normalizationFactors[sourceRow] = 1/matrix[sourceRow][sourceRow];
        return null;
    }

    @Override
    public String toString(){
        return "%c(%d)".formatted(operationSymbol, sourceRow);
    }
}
