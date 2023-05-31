using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MultiplyMatrix.Core
{
    public interface IMultiplyMatrixAlgorithm
    {
        public Matrix Multiply(Matrix matrix1, Matrix matrix2);
    }
}
