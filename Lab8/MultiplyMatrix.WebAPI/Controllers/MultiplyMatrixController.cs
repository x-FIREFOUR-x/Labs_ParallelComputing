using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

using MultiplyMatrix.Core;

namespace MultiplyMatrix.WebAPI
{

    [ApiController]
    [Route("api/[controller]")]
    public class MultiplyMatrixController : ControllerBase
    {
        private readonly IMultiplyMatrixAlgorithm _multiplyMatrix;

        public MultiplyMatrixController(IMultiplyMatrixAlgorithm multiplyMatrix)
        {
            _multiplyMatrix = multiplyMatrix;
        }

        [HttpPost("multiply-generated-matrix/{matrixSize:int}")]
        public async Task<int[][]> MultiplyGeneratedMatrix(int matrixSize)
        {
            Matrix matrix1 = new Matrix(matrixSize, matrixSize, 1, 1);
            Matrix matrix2 = new Matrix(matrixSize, matrixSize, 1, 1);

            Matrix resultMatrix = _multiplyMatrix.Multiply(matrix1, matrix2);

            return resultMatrix.GetMatrix();
        }

        [HttpPost("multiply-get-matrix/")]
        [RequestSizeLimit(100_000_000)]
        public async Task<int[][]> MultiplyGetMatrix([FromBody] MatrixData data)
        {
            Matrix matrix1 = new Matrix(data.Matrix1);
            Matrix matrix2 = new Matrix(data.Matrix1);

            Matrix resultMatrix = _multiplyMatrix.Multiply(matrix1, matrix2);

            return resultMatrix.GetMatrix();
        }
    }
}
