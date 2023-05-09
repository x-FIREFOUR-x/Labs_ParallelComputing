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

        long totalTime = 0;
        int countTest = 5;

        // Fox algorithm multiply matrices
        totalTime = 0;
        Matrix resultMatrix2 = null;
        for (int i = 0; i < countTest; i++) {
            startTime = System.currentTimeMillis();
            FoxAlgorithm foxAlgorithm = new FoxAlgorithm(countThread);
            resultMatrix2 = foxAlgorithm.multiply(matrix1, matrix2);
            endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;
        }


        System.out.print("Time working fox algo (FixedThreadPool): ");
        System.out.println(totalTime / countTest);

        if(printedMatrices) {
            resultMatrix2.print();
        }

        // Fox algorithm multiply matrices forkjoin
        totalTime = 0;
        Matrix resultMatrix3 = null;
        for (int i = 0; i < countTest; i++) {
            startTime = System.currentTimeMillis();
            ForkJoinFoxAlgorithm fjfoxAlgorithm = new ForkJoinFoxAlgorithm(countThread);
            resultMatrix3 = fjfoxAlgorithm.multiply(matrix1, matrix2);
            endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;
        }

        System.out.print("Time working fox algo (ForkJoinPool): ");
        System.out.println(totalTime / countTest);

        if(printedMatrices) {
            resultMatrix3.print();
        }



        System.out.println(resultMatrix1.Equal(resultMatrix2));
        System.out.println(resultMatrix1.Equal(resultMatrix3));
    }
}
