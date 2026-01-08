import operations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GaussJordanElimination {
    private final ExecutorService executorService;

    public GaussJordanElimination(){
        System.out.printf("Cores available: %d\n".formatted(Runtime.getRuntime().availableProcessors()));
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }


    public final void solve(double[][] matrix) throws InterruptedException {
        Double[][] eliminationFactors = new Double[matrix.length][matrix.length];
        Double[] normalizationFactors = new Double[matrix.length];

        //pivoting for stability
        for (int sourceRow = 0; sourceRow < matrix.length; ++sourceRow) {
            //find the row with the largest absolute value in the current column (looking only forward)
            int maxRow = sourceRow;
            double maxValue = Math.abs(matrix[sourceRow][sourceRow]);

            for (int swapRow = sourceRow + 1; swapRow < matrix.length; ++swapRow) {
                double candidate = Math.abs(matrix[swapRow][sourceRow]);
                if (candidate > maxValue) {
                    maxValue = candidate;
                    maxRow = swapRow;
                }
            }

            //swap only if the max row is different from current
            if (maxRow != sourceRow) {
                double[] temp = matrix[maxRow];
                matrix[maxRow] = matrix[sourceRow];
                matrix[sourceRow] = temp;
            }
        }

        //for every source row
        for (int sourceRow=0; sourceRow<matrix.length; ++sourceRow){
            List<Callable<Void>> A_tasks = new ArrayList<>(List.of());
            List<Callable<Void>> B_tasks = new ArrayList<>(List.of());
            List<Callable<Void>> C_tasks = new ArrayList<>(List.of());
            //target row needs to be adjusted
            for (int targetRow=0; targetRow<matrix.length; ++targetRow){
                if (sourceRow==targetRow)
                    continue;

                //A - finding elimination factor
                A_tasks.add(new FindFactor(matrix, eliminationFactors, normalizationFactors, sourceRow, targetRow));

                //B - multiplication by said factor; diagonals don't need C operation as it would be subtracting zeros
                if (targetRow<sourceRow){
                    B_tasks.add(new MultiplyElement(matrix, eliminationFactors, normalizationFactors, sourceRow, targetRow, targetRow));
                }

                //B - multiplication by said factor; all the other elements; here column=sourceRow+1 as there is no point in multiplying element that will be 0
                for (int column=sourceRow+1; column<matrix[0].length; ++column){
                    B_tasks.add(new MultiplyElement(matrix, eliminationFactors, normalizationFactors, sourceRow, column, targetRow));
                }

                //C - subtracting elements; takes care of not multiplied element
                for (int column=sourceRow; column<matrix[0].length; ++column){
                    C_tasks.add(new SubtractElement(matrix, eliminationFactors, normalizationFactors, sourceRow, column, targetRow));
                }
            }
            //we apply said operations
            runCurrentTasks(A_tasks);
            runCurrentTasks(B_tasks);
            runCurrentTasks(C_tasks);
        }
        //at the end normalization is needed
        //D - finding elimination factor
        List<Callable<Void>> D_tasks = new ArrayList<>(List.of());
        for (int row=0; row<matrix.length; ++row){
            D_tasks.add(new NormalizationFactor(matrix, eliminationFactors, normalizationFactors, row));
        }
        runCurrentTasks(D_tasks);

        //E - multiplying by said factor; only on diagonals and result column
        List<Callable<Void>> E_tasks = new ArrayList<>(List.of());
        for (int row=0; row<matrix.length; ++row) {
            E_tasks.add(new NormalizeElement(matrix, eliminationFactors, normalizationFactors, row, row));
            E_tasks.add(new NormalizeElement(matrix, eliminationFactors, normalizationFactors, row, matrix.length));
        }
        runCurrentTasks(E_tasks);

        //finished
        executorService.shutdown();
    }

    private void runCurrentTasks(List<Callable<Void>> tasks) throws InterruptedException {
        //runs all the tasks inside the list and waits for them to finish (invokeAll - https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html), then clears said list
        executorService.invokeAll(tasks);
        tasks.clear();
    }
}



