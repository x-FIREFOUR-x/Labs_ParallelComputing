package TapeAlgoMultiplyMatrix;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class TaskTapeAlgorithm implements Callable<Integer> {
    private ArrayList<Integer> row;
    private ArrayList<Integer> column;

    TaskTapeAlgorithm(ArrayList<Integer> rowMatrix1, ArrayList<Integer> columnMatrix2)
    {
        row = rowMatrix1;
        column = columnMatrix2;
    }

    @Override
    public Integer call(){
        int element = 0;
        for(int j = 0; j < row.size(); j++)
        {
            element += row.get(j) * column.get(j);
        }
        return element;
    }
}
