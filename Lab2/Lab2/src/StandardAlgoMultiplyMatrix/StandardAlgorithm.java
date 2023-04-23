package StandardAlgoMultiplyMatrix;

import Types.Matrix;

public class StandardAlgorithm {
    public Matrix multiply(Matrix matrix1, Matrix matrix2){
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
}
