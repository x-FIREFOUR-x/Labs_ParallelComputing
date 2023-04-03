package Types;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Matrix {
    int[][] data;

    int countRows;
    int countColumns;

    public Matrix(int countRows, int countColumns){
        this.countRows = countRows;
        this.countColumns = countColumns;
        data = new int[countRows][countColumns];
    }
    public Matrix(int countRows, int countColumns, int randomMin, int randomMax){
        this(countRows, countColumns);

        Random random = new Random();
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < countColumns; j++) {
                int numb = random.nextInt() % (randomMax - randomMin + 1) + randomMin;
                data[i][j] = numb;
            }
        }
    }

    public int getCountRows() {
        return countRows;
    }
    public int getCountColumns() {
        return  countColumns;
    }

    public int getElement(int idxRow, int idxColumn)
    {
        return data[idxRow][idxColumn];
    }
    public void setElement(int idxRow, int idxColumn, int element) {
        data[idxRow][idxColumn] = element;
    }

    public int[] getRow(int index){
        return data[index];
    }
    public void setRow(int index, int[] row){
        data[index] = row;
    }

    public int[] getColumn(int index){
        int[] column = new int[countColumns];
        for (int i = 0; i < getCountRows(); i++) {
            column[i] = (data[i][index]);
        }
        return column;
    }

    public boolean Equal(Matrix matrix){
        if (this.getCountColumns() != matrix.getCountRows() || this.getCountRows() != matrix.getCountColumns())
            return false;

        for (int i = 0; i < matrix.getCountRows(); i++) {
            for (int j = 0; j < matrix.getCountColumns(); j++) {
                if (!Objects.equals(this.data[i][j], matrix.getElement(i, j)))
                    return false;
            }
        }

        return true;
    }
}
