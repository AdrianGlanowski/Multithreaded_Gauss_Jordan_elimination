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

        for (int sourceRow = 0; sourceRow < matrix.length; ++sourceRow) {
            // Find the row with the largest absolute value in the current column
            int maxRow = sourceRow;
            double maxValue = Math.abs(matrix[sourceRow][sourceRow]);

            for (int swapRow = sourceRow + 1; swapRow < matrix.length; ++swapRow) {
                double candidate = Math.abs(matrix[swapRow][sourceRow]);
                if (candidate > maxValue) {
                    maxValue = candidate;
                    maxRow = swapRow;
                }
            }

            // Swap only if the max row is different from current
            if (maxRow != sourceRow) {
                double[] temp = matrix[maxRow];
                matrix[maxRow] = matrix[sourceRow];
                matrix[sourceRow] = temp;
            }
        }

        for (int sourceRow=0; sourceRow<matrix.length; ++sourceRow){
            List<Callable<Void>> A_tasks = new ArrayList<>(List.of());
            List<Callable<Void>> B_tasks = new ArrayList<>(List.of());
            List<Callable<Void>> C_tasks = new ArrayList<>(List.of());
            for (int targetRow=0; targetRow<matrix.length; ++targetRow){
                if (sourceRow==targetRow)
                    continue;

                //A
                A_tasks.add(new FindFactor(matrix, eliminationFactors, normalizationFactors, sourceRow, targetRow));

                //B
                if (targetRow<sourceRow){
                    B_tasks.add(new MultiplyElement(matrix, eliminationFactors, normalizationFactors, sourceRow, targetRow, targetRow));
                }

                //skoro go zeruje, to nie musze mnozyc
                for (int column=sourceRow+1; column<matrix[0].length; ++column){
                    B_tasks.add(new MultiplyElement(matrix, eliminationFactors, normalizationFactors, sourceRow, column, targetRow));
                }

                //C
                for (int column=sourceRow; column<matrix[0].length; ++column){
                    C_tasks.add(new SubtractElement(matrix, eliminationFactors, normalizationFactors, sourceRow, column, targetRow));
                }
            }

            runCurrentTasks(A_tasks);
            runCurrentTasks(B_tasks);
            runCurrentTasks(C_tasks);
        }
        //D
        List<Callable<Void>> D_tasks = new ArrayList<>(List.of());
        for (int row=0; row<matrix.length; ++row){
            D_tasks.add(new NormalizationFactor(matrix, eliminationFactors, normalizationFactors, row));
        }
        runCurrentTasks(D_tasks);

        //E
        List<Callable<Void>> E_tasks = new ArrayList<>(List.of());
        for (int row=0; row<matrix.length; ++row) {
            E_tasks.add(new NormalizeElement(matrix, eliminationFactors, normalizationFactors, row, row));
            E_tasks.add(new NormalizeElement(matrix, eliminationFactors, normalizationFactors, row, matrix.length));
        }
        runCurrentTasks(E_tasks);
        executorService.shutdown();
    }

    private void runCurrentTasks(List<Callable<Void>> tasks) throws InterruptedException {
        executorService.invokeAll(tasks);

        tasks.clear();
    }
}



