package FoxAlgo;

import TypeMatrix.Matrix;
import TypeMatrix.StandardOperation;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FoxAlgorithm {
    final int countThread;

    public FoxAlgorithm(int countThread){
        this.countThread = countThread;
    }

    public Matrix multiply(Matrix matrix1, Matrix matrix2) {
        int sizeMatrixM = (int) Math.sqrt(countThread - 1) + 1;
        Matrix[][] matrixM1 = StandardOperation.matrixToMatrixMatrices(matrix1, sizeMatrixM);
        Matrix[][] matrixM2 = StandardOperation.matrixToMatrixMatrices(matrix2, sizeMatrixM);

        int sizeInternalM = matrixM1[0][0].getCountColumns();
        Matrix[][] resultMatrixM = new Matrix[sizeMatrixM][sizeMatrixM];
        for (int i = 0; i < sizeMatrixM; i++) {
            for (int j = 0; j < sizeMatrixM; j++) {
                resultMatrixM[i][j] = new Matrix(sizeInternalM, sizeInternalM);
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(countThread);
        for (int k = 0; k < sizeMatrixM; k++) {
            ArrayList<Future<Matrix>> futures = new ArrayList<Future<Matrix>>();
            for (int i = 0; i < sizeMatrixM; i++) {
                for (int j = 0; j < sizeMatrixM; j++) {
                    TaskFoxAlgorithm task = new TaskFoxAlgorithm(
                            matrixM1[i][(i + k) % sizeMatrixM],
                            matrixM2[(i + k) % sizeMatrixM][j],
                            resultMatrixM[i][j]);
                    futures.add(executor.submit(task));
                }
            }

            for (int i = 0; i < sizeMatrixM; i++) {
                for (int j = 0; j < sizeMatrixM; j++) {
                    try {
                        resultMatrixM[i][j] = futures.get(i * sizeMatrixM + j).get();
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        executor.shutdown();

        return StandardOperation.matrixMatricesToMatrix(
                resultMatrixM, matrix1.getCountRows(), matrix2.getCountColumns());
    }
}
