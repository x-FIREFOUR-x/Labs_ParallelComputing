package FoxAlgo;

import TapeAlgoMultiplyMatrix.TaskTapeAlgorithm;
import Types.Matrix;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
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
        Matrix[][] matrixM1 = MatrixToMatrixMatrices(matrix1, sizeMatrixM);
        Matrix[][] matrixM2 = MatrixToMatrixMatrices(matrix2, sizeMatrixM);

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

        return MatrixMatricesToMatrix(resultMatrixM, matrix1.getCountRows(), matrix2.getCountColumns());
    }

    private Matrix[][] MatrixToMatrixMatrices(Matrix matrix, int sizeMatrixM){
        Matrix[][] matrixMatrices = new Matrix[sizeMatrixM][sizeMatrixM];
        int sizeInternal = (matrix.getCountColumns() + sizeMatrixM - 1) / sizeMatrixM;

        for (int i = 0; i < sizeMatrixM; i++) {
            for (int j = 0; j < sizeMatrixM; j++) {
                matrixMatrices[i][j] = new Matrix(sizeInternal, sizeInternal);

                for (int k = 0; k < sizeInternal; k++) {
                    for (int l = 0; l < sizeInternal; l++) {
                        if (i * sizeInternal + k >= matrix.getCountRows()
                                || j * sizeInternal + l >= matrix.getCountColumns()){
                            matrixMatrices[i][j].setElement(k, l, 0);
                        }
                        else{
                            int element = matrix.getElement(i * sizeInternal + k, j * sizeInternal + l);
                            matrixMatrices[i][j].setElement(k, l, element);
                        }
                    }
                }
            }
        }
        return matrixMatrices;
    }

    private Matrix MatrixMatricesToMatrix(Matrix[][] matrixM, int heightMatrix, int widthMatrix) {
        Matrix resultMatrix = new Matrix(heightMatrix, widthMatrix);

        for (int i = 0; i < matrixM.length; i++) {
            for (int j = 0; j < matrixM[i].length; j++) {

                for (int k = 0; k < matrixM[i][j].getCountRows(); k++) {
                    for (int l = 0; l < matrixM[i][j].getCountColumns(); l++) {
                        if (i * matrixM[i][j].getCountRows() + k < heightMatrix
                                && j * matrixM[i][j].getCountColumns() + l < widthMatrix) {
                            resultMatrix.setElement(
                                    i * matrixM[i][j].getCountRows() + k,
                                    j * matrixM[i][j].getCountColumns() + l,
                                    matrixM[i][j].getElement(k, l));
                        }
                    }
                }
            }
        }

        return resultMatrix;
    }
}
