using System;

namespace MultiplyMatrix.Core
{
    public class Matrix : ICloneable
    {
        int[][] data;

        public int CountRows { get; private set; }
        public int CountColumns { get; private set;}

        public Matrix(int countRows, int countColumns)
        {
            CountRows = countRows;
            CountColumns = countColumns;
            data = new int[countRows][];
            for (int i = 0; i < countRows; i++)
                data[i] = new int[countColumns];
        }
        public Matrix(int countRows, int countColumns, int randomMin, int randomMax) 
            :this(countRows, countColumns)
        {
            Random random = new Random();
            for (int i = 0; i < CountRows; i++)
            {
                for (int j = 0; j < CountColumns; j++)
                {
                    int numb = (random.Next(randomMax - randomMin + 1)) + randomMin;
                    data[i][j] = numb;
                }
            }
        }

        public Matrix(int[][] matrix)
        {
            CountRows = matrix.Length;
            CountColumns = matrix[0].Length;
            data = matrix;
        }

        public int GetElement(int idxRow, int idxColumn)
        {
            return data[idxRow][idxColumn];
        }
        public void SetElement(int idxRow, int idxColumn, int element)
        {
            data[idxRow][idxColumn] = element;
        }

        public int[] GetRow(int index)
        {
            return data[index];
        }
        public void SetRow(int index, int[] row)
        {
            data[index] = row;
        }

        public int[][] GetMatrix()
        {
            return data;
        }

        public bool Equal(Matrix matrix)
        {
            if (this.CountColumns != matrix.CountRows || this.CountRows != matrix.CountColumns)
                return false;

            for (int i = 0; i < matrix.CountRows; i++)
            {
                for (int j = 0; j < matrix.CountColumns; j++)
                {
                    if (this.data[i][j] == matrix.GetElement(i, j))
                        return false;
                }
            }

            return true;
        }

        public object Clone()
        {
            Matrix clone = new Matrix(this.CountRows, this.CountColumns);
            for (int i = 0; i < clone.CountRows; i++)
            {
                for (int j = 0; j < clone.CountColumns; j++)
                {
                    clone.SetElement(i, j, this.data[i][j]);
                }
            }
            return clone;
        }

        public void Transpose()
        {
            for (int i = 0; i < CountRows; i++)
            {
                for (int j = i + 1; j < CountColumns; j++)
                {
                    int buf = data[i][j];
                    data[i][j] = data[j][i];
                    data[j][i] = buf;
                }
            }
        }

        public Matrix Add(Matrix matrix)
        {
            Matrix result = new Matrix(this.CountRows, this.CountColumns);
            for (int i = 0; i < this.CountRows; i++)
            {
                for (int j = 0; j < this.CountColumns; j++)
                {
                    int elem = data[i][j] + matrix.GetElement(i, j);
                    result.SetElement(i, j, elem);
                }
            }
            return result;
        }
    }
}
