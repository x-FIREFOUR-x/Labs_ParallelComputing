package Types;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Matrix {
    ArrayList<ArrayList<Integer>> data;

    public Matrix(ArrayList<ArrayList<Integer>> matrix) {
        data = matrix;
    }
    public Matrix(int countRows, int countColumns){
        data = new ArrayList<>();
        for (int i = 0; i < countRows; i++)
        {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < countColumns; j++) {
                row.add(0);
            }
            data.add(row);
        }
    }
    public Matrix(int countRows, int countColumns, int randomMin, int randomMax){
        this(countRows, countColumns);

        Random random = new Random();
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < countColumns; j++) {
                int numb = random.nextInt() % (randomMax - randomMin + 1) + randomMin;
                data.get(i).set(j, numb);
            }
        }
    }

    public int getCountRows() {
        return data.size();
    }
    public int getCountColumns() {
        if (getCountRows() == 0)
            return 0;
        else
            return data.get(0).size();
    }

    public int getElement(int idxRow, int idxColumn)
    {
        return data.get(idxRow).get(idxColumn);
    }
    public void setElement(int idxRow, int idxColumn, int element) {
        data.get(idxRow).set(idxColumn, element);
    }

    public ArrayList<Integer> getRow(int index){
        return data.get(index);
    }
    public void setRow(int index, ArrayList<Integer> row){
        data.set(index, row);
    }

    public ArrayList<Integer> getColumn(int index){
        ArrayList<Integer> column = new ArrayList<>();
        for (int i = 0; i < getCountRows(); i++) {
            column.add(data.get(i).get(index));
        }
        return column;
    }

    public ArrayList<ArrayList<Integer>> getColumns(){
        ArrayList<ArrayList<Integer>> columns = new ArrayList<>();
        for (int i = 0; i < getCountColumns(); i++) {
            columns.add(getColumn(i));
        }
        return columns;
    }

    public boolean Equal(Matrix matrix){
        if (this.getCountColumns() != matrix.getCountRows() || this.getCountRows() != matrix.getCountColumns())
            return false;

        for (int i = 0; i < matrix.getCountRows(); i++) {
            for (int j = 0; j < matrix.getCountColumns(); j++) {
                if (!Objects.equals(this.data.get(i).get(j), matrix.data.get(i).get(j)))
                    return false;
            }
        }

        return true;
    }
}
