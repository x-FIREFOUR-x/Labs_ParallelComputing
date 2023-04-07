package TapeAlgoMultiplyMatrix;

import Types.Matrix;

import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.concurrent.*;

public class StripeAlgorithm {
    final int countThread;

    public StripeAlgorithm(int countThread){
        this.countThread = countThread;
    }

    public Matrix multiply(Matrix matrix1, Matrix matrix2){
        Matrix resultMatrix = new Matrix(matrix1.getCountRows(), matrix2.getCountColumns());
        Matrix transposedMatrix2 = matrix2.clone();
        transposedMatrix2.transpose();

        ExecutorService executor = Executors.newFixedThreadPool(countThread);
        ArrayList<TaskStipeAlgorithm> callables = new ArrayList<TaskStipeAlgorithm>();
        ArrayList<Future<Integer>> futures = new ArrayList<Future<Integer>>();

        for (int i = 0; i < matrix1.getCountRows(); i++) {
            for (int j = 0; j < matrix2.getCountColumns(); j++) {
                int idxRow = (j + i) % matrix1.getCountRows();
                TaskStipeAlgorithm task = new TaskStipeAlgorithm(matrix1.getRow(idxRow), transposedMatrix2.getRow(j));
                callables.add(task);
            }

            try{
                futures.addAll(executor.invokeAll(callables));
                callables.clear();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        executor.shutdown();

        try{
            for (int i = 0; i < resultMatrix.getCountRows(); i++) {
                for (int j = 0; j < resultMatrix.getCountColumns(); j++) {
                    var future = futures.get(i * resultMatrix.getCountColumns() + j);
                    int idxRow = (j + i) % matrix1.getCountRows();
                    resultMatrix.setElement(idxRow, j, future.get());
                }
            }
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return resultMatrix;
    }
}
