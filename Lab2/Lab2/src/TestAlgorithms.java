import FoxAlgo.FoxAlgorithm;
import StandardAlgoMultiplyMatrix.StandardAlgorithm;
import TapeAlgoMultiplyMatrix.TapeAlgorithm;
import Types.Matrix;

public class TestAlgorithms {

    public void runTest(int sizeMatrix, int countThread){
        System.out.print("Size matrix: ");
        System.out.println(sizeMatrix);
        System.out.print("count Thread: ");
        System.out.println(countThread);

        Matrix matrix1 = new Matrix(sizeMatrix, sizeMatrix, 0, 1000);
        Matrix matrix2 = new Matrix(sizeMatrix, sizeMatrix, 0, 1000);


        // Not parallel algorithm multiply matrices
        long startTime = System.currentTimeMillis();

        StandardAlgorithm standardAlgorithm = new StandardAlgorithm();
        Matrix resultMatrix1 = standardAlgorithm.multiply(matrix1, matrix2);

        long endTime = System.currentTimeMillis();
        System.out.print("Time working standard algo: ");
        System.out.println(endTime - startTime);


        // Tape algorithm multiply matrices
        startTime = System.currentTimeMillis();

        TapeAlgorithm tapeAlgorithm = new TapeAlgorithm(countThread);
        Matrix resultMatrix2 = tapeAlgorithm.multiply(matrix1, matrix2);

        endTime = System.currentTimeMillis();
        System.out.print("Time working tape algo: ");
        System.out.println(endTime - startTime);


        //
        startTime = System.currentTimeMillis();

        FoxAlgorithm foxAlgorithm = new FoxAlgorithm(countThread);
        Matrix resultMatrix3 = foxAlgorithm.multiply(matrix1, matrix2);

        endTime = System.currentTimeMillis();
        System.out.print("Time working tape algo: ");
        System.out.println(endTime - startTime);


        boolean equal = resultMatrix1.Equal(resultMatrix2);
        boolean equal2 = resultMatrix1.Equal(resultMatrix3);
    }
}
