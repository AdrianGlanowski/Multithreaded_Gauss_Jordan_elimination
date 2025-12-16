import operations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GaussJordanElimination {
    private final ExecutorService executorService;

    public GaussJordanElimination(){
        System.out.printf("Cores available: %d\n".formatted(Runtime.getRuntime().availableProcessors()));
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }


    public final void solve(double[][] matrix) throws InterruptedException {
        double[][] eliminationFactors = new double[matrix.length][matrix.length];
        double[] normalizationFactors = new double[matrix.length];

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
                for (int column=sourceRow; column<matrix[0].length; ++column){
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

//    private void runCurrentTasks(List<Runnable> tasks) {
//        List<Future<?>> futures = new ArrayList<>();
//
//        for (Runnable task : tasks) {
//            futures.add(executorService.submit(task));
//        }
//
//        for (Future<?> f : futures) {
//            try {
//                f.get(); // waits correctly
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        tasks.clear();
//    }

    private void runCurrentTasks(List<Callable<Void>> tasks) throws InterruptedException {
        executorService.invokeAll(tasks);

        tasks.clear();
    }
}



