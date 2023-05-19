import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;
import java.util.Random;

public class Matrix {
    private int[][] data;

    private int countRows;
    private int countColumns;

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
                int numb = (random.nextInt(randomMax - randomMin + 1)) + randomMin;
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

    public Matrix clone()
    {
        Matrix clone = new Matrix(this.countRows, this.countColumns);
        for (int i = 0; i < clone.countRows; i++) {
            for (int j = 0; j < clone.countColumns; j++) {
                clone.setElement(i, j, this.data[i][j]);
            }
        }
        return clone;
    }

    public void print(){
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < countColumns; j++) {
                buf.append(data[i][j]).append(" ");
            }
            buf.append("\n");
        }
        buf.append("\n");
        System.out.println(buf);
    }

    public Matrix multiply(Matrix matrix2) {
        Matrix result = new Matrix(this.getCountRows(), matrix2.getCountColumns());
        for (int i = 0; i < this.getCountRows(); i++) {
            for (int j = 0; j < matrix2.getCountColumns(); j++) {
                int element = 0;
                for (int k = 0; k < matrix2.getCountRows(); k++) {
                    element += this.getElement(i, k) * matrix2.getElement(k, j);
                }
                result.setElement(i, j, element);
            }
        }
        return result;
    }

    public Matrix getSubMatrix(int indexStartRow, int indexEndRow, int countColumns)
    {
        Matrix subMatrix = new Matrix(indexEndRow - indexStartRow + 1, countColumns);
        for (int i = indexStartRow; i <= indexEndRow; i++) {
            for (int j = 0; j < countColumns; j++) {
                subMatrix.setElement(i - indexStartRow, j, data[i][j]);
            }
        }
        return subMatrix;
    }

    public void putSubMatrix(Matrix matrix, int indexStartRow, int indexEndRow, int countColumns)
    {
        for (int i = indexStartRow; i <= indexEndRow; i++) {
            for (int j = 0; j < countColumns; j++) {
                data[i][j] = matrix.getElement(i - indexStartRow, j);
            }
        }
    }

    public int[] convertToArray()
    {
        int [] array = new int[countRows * countColumns];
        int index = 0;
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < countColumns; j++) {
                array[index] = data[i][j];
                index++;
            }
        }
        return array;
    }

    public Matrix(int[] array, int countRows, int countColumns){
        this.countRows = countRows;
        this.countColumns = countColumns;
        data = new int[countRows][countColumns];

        int index = 0;
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < countColumns; j++) {
                data[i][j] = array[index];
                index++;
            }
        }
    }


}
