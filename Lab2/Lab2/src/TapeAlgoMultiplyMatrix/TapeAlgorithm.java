package TapeAlgoMultiplyMatrix;

import Types.Matrix;

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


        for (int i = 0; i < matrix1.getCountRows(); i++) {
            for (int j = 0; j < matrix2.getCountColumns(); j++) {
                TaskTapeAlgorithm task = new TaskTapeAlgorithm(matrix1.getRow(i), matrix2.getColumn(j));
                callables.add(task);
            }
        }
        try{
            var features = executor.invokeAll(callables);
            executor.shutdown();

            for (int i = 0; i < resultMatrix.getCountRows(); i++) {
                for (int j = 0; j < resultMatrix.getCountColumns(); j++) {
                    var feature = features.get(i * resultMatrix.getCountColumns() + j);
                    resultMatrix.setElement(i, j, feature.get());
                }
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return resultMatrix;
    }
}
