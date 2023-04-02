import StandardAlgoMultiplyMatrix.StandardAlgorithm;
import TapeAlgoMultiplyMatrix.TapeAlgorithm;
import Types.Matrix;

public class Main {
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(5, 5, 0, 10);
        Matrix matrix2 = new Matrix(5, 5, 0, 10);

        StandardAlgorithm standardAlgorithm = new StandardAlgorithm();
        Matrix resultMatrix1 = standardAlgorithm.multiply(matrix1, matrix2);

        TapeAlgorithm tapeAlgorithm = new TapeAlgorithm(5);
        Matrix resultMatrix2 = tapeAlgorithm.multiply(matrix1, matrix2);

        boolean equal = resultMatrix1.Equal(resultMatrix2);

    }
}