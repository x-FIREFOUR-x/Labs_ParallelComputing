package TapeAlgoMultiplyMatrix;

import java.util.concurrent.Callable;

public class TaskTapeAlgorithm implements Callable<Integer> {
    private int[] row;
    private int[] column;

    TaskTapeAlgorithm(int[] rowMatrix1, int[] columnMatrix2)
    {
        row = rowMatrix1;
        column = columnMatrix2;
    }

    @Override
    public Integer call(){
        int element = 0;
        for(int j = 0; j < row.length; j++)
        {
            element += row[j] * column[j];
        }
        return element;
    }
}
