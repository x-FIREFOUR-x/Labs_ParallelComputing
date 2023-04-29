package TypeMatrix;

public class StandardOperation {
    public static Matrix multiply(Matrix matrix1, Matrix matrix2){
        Matrix matrix = new Matrix(matrix1.getCountRows(), matrix2.getCountColumns());
        for (int i = 0; i < matrix1.getCountRows(); i++) {
            for (int j = 0; j < matrix2.getCountColumns(); j++) {
                int element = 0;
                for (int k = 0; k < matrix2.getCountRows(); k++) {
                    element += matrix1.getElement(i, k) * matrix2.getElement(k, j);
                }
                matrix.setElement(i, j, element);
            }
        }
        return matrix;
    }

    public static Matrix[][] matrixToMatrixMatrices(Matrix matrix, int sizeMatrixM){
        Matrix[][] matrixMatrices = new Matrix[sizeMatrixM][sizeMatrixM];
        int sizeInternal = (int) ((matrix.getCountColumns() - 1) / sizeMatrixM) + 1;

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

    public static Matrix matrixMatricesToMatrix(Matrix[][] matrixM, int heightMatrix, int widthMatrix) {
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
