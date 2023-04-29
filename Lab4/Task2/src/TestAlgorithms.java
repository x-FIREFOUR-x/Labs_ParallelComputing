import FoxAlgo.FoxAlgorithm;
import FoxAlgoForkJoin.ForkJoinFoxAlgorithm;
import TypeMatrix.StandardOperation;
import TypeMatrix.Matrix;

public class TestAlgorithms {

    public void runTest(int sizeMatrix, int countThread, boolean printedMatrices){
        System.out.print("Size matrix: ");
        System.out.println(sizeMatrix);
        System.out.print("count Thread: ");
        System.out.println(countThread);

        Matrix matrix1 = new Matrix(sizeMatrix, sizeMatrix, 0, 10);

        Matrix matrix2 = new Matrix(sizeMatrix, sizeMatrix, 0, 10);
        if(printedMatrices){
            matrix1.print();
            matrix2.print();
        }

        // Not parallel algorithm multiply matrices
        long startTime = System.currentTimeMillis();

        Matrix resultMatrix1 = StandardOperation.multiply(matrix1, matrix2);

        long endTime = System.currentTimeMillis();
        System.out.print("Time working standard algo: ");
        System.out.println(endTime - startTime);

        if(printedMatrices) {
            resultMatrix1.print();
        }

        // Fox algorithm multiply matrices
        startTime = System.currentTimeMillis();

        FoxAlgorithm foxAlgorithm = new FoxAlgorithm(countThread);
        Matrix resultMatrix2 = foxAlgorithm.multiply(matrix1, matrix2);

        endTime = System.currentTimeMillis();
        System.out.print("Time working fox algo: ");
        System.out.println(endTime - startTime);

        if(printedMatrices) {
            resultMatrix2.print();
        }

        // Fox algorithm multiply matrices forkjoin
        startTime = System.currentTimeMillis();

        ForkJoinFoxAlgorithm fjfoxAlgorithm = new ForkJoinFoxAlgorithm(countThread);
        Matrix resultMatrix3 = fjfoxAlgorithm.multiply(matrix1, matrix2);

        endTime = System.currentTimeMillis();
        System.out.print("Time working fox algo: ");
        System.out.println(endTime - startTime);

        if(printedMatrices) {
            resultMatrix3.print();
        }

        System.out.println(resultMatrix1.Equal(resultMatrix2));
        System.out.println(resultMatrix1.Equal(resultMatrix3));
    }
}
