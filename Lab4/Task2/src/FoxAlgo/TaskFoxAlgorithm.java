package FoxAlgo;

import TypeMatrix.StandardOperation;
import TypeMatrix.Matrix;

import java.util.concurrent.Callable;

public class TaskFoxAlgorithm implements Callable<Matrix> {
    private Matrix matrix1;
    private Matrix matrix2;
    private Matrix resMatrix;

    private final StandardOperation multiplier = new StandardOperation();


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
