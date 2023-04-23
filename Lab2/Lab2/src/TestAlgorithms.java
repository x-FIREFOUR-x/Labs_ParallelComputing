import FoxAlgo.FoxAlgorithm;
import StandardAlgoMultiplyMatrix.StandardAlgorithm;
import TapeAlgoMultiplyMatrix.StripeAlgorithm;
import Types.Matrix;

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

        StandardAlgorithm standardAlgorithm = new StandardAlgorithm();
        Matrix resultMatrix1 = standardAlgorithm.multiply(matrix1, matrix2);

        long endTime = System.currentTimeMillis();
        System.out.print("Time working standard algo: ");
        System.out.println(endTime - startTime);

        if(printedMatrices) {
            resultMatrix1.print();
        }


        // Stripe algorithm multiply matrices
        startTime = System.currentTimeMillis();

        StripeAlgorithm stripeAlgorithm = new StripeAlgorithm(countThread);
        Matrix resultMatrix2 = stripeAlgorithm.multiply(matrix1, matrix2);

        endTime = System.currentTimeMillis();
        System.out.print("Time working stripe algo: ");
        System.out.println(endTime - startTime);

        if(printedMatrices) {
            resultMatrix2.print();
        }


        // Fox algorithm multiply matrices
        startTime = System.currentTimeMillis();

        FoxAlgorithm foxAlgorithm = new FoxAlgorithm(countThread);
        Matrix resultMatrix3 = foxAlgorithm.multiply(matrix1, matrix2);

        endTime = System.currentTimeMillis();
        System.out.print("Time working fox algo: ");
        System.out.println(endTime - startTime);

        if(printedMatrices) {
            resultMatrix3.print();
        }


        boolean equal = resultMatrix3.Equal(resultMatrix2);
        //boolean equal2 = resultMatrix1.Equal(resultMatrix3);
    }
}
