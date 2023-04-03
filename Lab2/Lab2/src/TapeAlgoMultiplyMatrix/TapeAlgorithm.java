package TapeAlgoMultiplyMatrix;

import Types.Matrix;

import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.concurrent.*;

public class TapeAlgorithm {
    final int countThread;

    public TapeAlgorithm(int countThread){
        this.countThread = countThread;
    }

    public Matrix multiply(Matrix matrix1, Matrix matrix2){
        Matrix resultMatrix = new Matrix(matrix1.getCountRows(), matrix2.getCountColumns());

        ExecutorService executor = Executors.newFixedThreadPool(countThread);
        ArrayList<TaskTapeAlgorithm> callables = new ArrayList<TaskTapeAlgorithm>();
        ArrayList<Future<Integer>> futures = new ArrayList<Future<Integer>>();

        for (int i = 0; i < matrix1.getCountRows(); i++) {
            for (int j = 0; j < matrix2.getCountColumns(); j++) {
                TaskTapeAlgorithm task = new TaskTapeAlgorithm(matrix1.getRow(i), matrix2.getColumn(j));
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
                    resultMatrix.setElement(i, j, future.get());
                }
            }
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return resultMatrix;
    }
}
