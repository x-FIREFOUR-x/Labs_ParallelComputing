package FoxAlgoForkJoin;

import TypeMatrix.StandardOperation;
import TypeMatrix.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkTaskFoxAlgorithm extends RecursiveTask<Matrix> {

    private Matrix matrix1;
    private Matrix matrix2;

    private final int limitSizeMatrices = 100;
    private final int sizeMatrixM = 2;

    public ForkTaskFoxAlgorithm(Matrix matrix1, Matrix matrix2)
    {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
    }

    @Override
    public Matrix compute() {
        if(matrix1.getCountRows() <= limitSizeMatrices){
            return StandardOperation.multiply(matrix1, matrix2);
        }

        Matrix[][] matrixM1 = StandardOperation.matrixToMatrixMatrices(matrix1, sizeMatrixM);
        Matrix[][] matrixM2 = StandardOperation.matrixToMatrixMatrices(matrix2, sizeMatrixM);

        int sizeInternalM = matrixM1[0][0].getCountColumns();
        Matrix[][] resultMatrixM = new Matrix[sizeMatrixM][sizeMatrixM];
        for (int i = 0; i < sizeMatrixM; i++) {
            for (int j = 0; j < sizeMatrixM; j++) {
                resultMatrixM[i][j] = new Matrix(sizeInternalM, sizeInternalM);
            }
        }


        for (int k = 0; k < sizeMatrixM; k++) {
            ArrayList<ForkTaskFoxAlgorithm> tasks = new ArrayList<>();
            List<Matrix> calculatedSubBlocks = new ArrayList<>();
            for (int i = 0; i < sizeMatrixM; i++) {
                for (int j = 0; j < sizeMatrixM; j++) {
                    var task = new ForkTaskFoxAlgorithm(
                            matrixM1[i][(i + k) % sizeMatrixM],
                            matrixM2[(i + k) % sizeMatrixM][j]
                    );
                    tasks.add(task);
                    task.fork();
                }
            }

            for (var task : tasks) {
                var subMatrix = task.join();
                calculatedSubBlocks.add(subMatrix);
            }

            for (int i = 0; i < sizeMatrixM; i++) {
                for (int j = 0; j < sizeMatrixM; j++) {
                    try {
                        resultMatrixM[i][j] = resultMatrixM[i][j].add(calculatedSubBlocks.get(i * sizeMatrixM + j));
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        return StandardOperation.matrixMatricesToMatrix(
                resultMatrixM, matrix1.getCountRows(), matrix2.getCountColumns());
    }
}
