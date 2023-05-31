using System.Threading.Tasks;

namespace Lab8.MultiplyMatrix.Core
{
    public class StripeAlgorithm
    {
        private int? _countThreads;

        public StripeAlgorithm(int countThreads)
        {
            _countThreads = countThreads;
        }

        public Matrix Multiply(Matrix matrix1, Matrix matrix2)
        {
            Matrix resultMatrix = new Matrix(matrix1.CountRows, matrix2.CountColumns);
            Matrix transposedMatrix2 = (Matrix) matrix2.Clone();
            transposedMatrix2.Transpose();

            var parallelOptions = new ParallelOptions { MaxDegreeOfParallelism = _countThreads ?? 8 };

            int sizeMatrix = matrix1.CountRows;

            for (int i = 0; i < matrix1.CountRows; i++)
            {
                Parallel.For(0, resultMatrix.CountRows, parallelOptions,
                    (idxColumn) => 
                    {
                        int idxRow = (idxColumn + i) % sizeMatrix;
                        var row = matrix1.GetRow(idxRow);
                        var column = transposedMatrix2.GetRow(idxColumn);

                        int element = 0;
                        for (int k = 0; k < row.Length; k++)
                        {
                            element += row[k] * column[k];
                        }

                        resultMatrix.SetElement(idxRow, idxColumn, element);
                    }
                );
            }

            return resultMatrix;
        }
    }
}
