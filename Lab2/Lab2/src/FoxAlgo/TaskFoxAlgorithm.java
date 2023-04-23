package FoxAlgo;

import StandardAlgoMultiplyMatrix.StandardAlgorithm;
import Types.Matrix;

import java.util.concurrent.Callable;

public class TaskFoxAlgorithm implements Callable<Matrix> {
    private Matrix matrix1;
    private Matrix matrix2;
    private Matrix resMatrix;

    private final StandardAlgorithm multiplier = new StandardAlgorithm();


    public TaskFoxAlgorithm(Matrix matrix1, Matrix matrix2, Matrix resMatrix){
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.resMatrix = resMatrix;
    }

    @Override
    public Matrix call(){
        return resMatrix.add(multiplier.multiply(matrix1, matrix2));
    }
}
