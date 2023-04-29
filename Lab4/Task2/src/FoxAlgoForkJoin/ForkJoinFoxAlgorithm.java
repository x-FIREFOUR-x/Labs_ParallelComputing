package FoxAlgoForkJoin;

import TypeMatrix.Matrix;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinFoxAlgorithm{
    private final ForkJoinPool forkJoinPool;

    public ForkJoinFoxAlgorithm(int countThread){
        this.forkJoinPool = new ForkJoinPool(countThread);
    }

    public Matrix multiply(Matrix matrix1, Matrix matrix2) {
        return forkJoinPool.invoke(new ForkTaskFoxAlgorithm(matrix1, matrix2));
    }


}
