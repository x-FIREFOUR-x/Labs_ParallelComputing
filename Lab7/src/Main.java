import mpi.*;

import java.util.Arrays;

public class Main {
    private static final int COUNT_ROWS = 4;
    private static final int COUNT_COLUMN = 4;

    private static final int COUNT_TEST = 1;
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(COUNT_ROWS, COUNT_COLUMN, 0, 10);
        Matrix matrix2 = new Matrix(COUNT_ROWS, COUNT_COLUMN, 0, 10);

        System.out.println("ggg");

        long averageTime = 0;
        for (int i = 0; i < COUNT_TEST; i++) {
            long startTime = System.currentTimeMillis();
            CollectivePassingMultiplyMatrices.multiply(args, matrix1, matrix2);
            long endTime = System.currentTimeMillis();

            averageTime += endTime = startTime;
        }

        averageTime = averageTime / COUNT_TEST;
        System.out.println(averageTime);
    }
}