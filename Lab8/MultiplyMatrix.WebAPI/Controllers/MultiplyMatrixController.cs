using System;
using System.IO;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
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
        public async Task<int[][]> MultiplyGeneratedMatrices(int matrixSize)
        {
            Matrix matrix1 = new Matrix(matrixSize, matrixSize, 0, 100);
            Matrix matrix2 = new Matrix(matrixSize, matrixSize, 0, 100);

            Matrix resultMatrix = _multiplyMatrix.Multiply(matrix1, matrix2);

            return resultMatrix.GetMatrix();
        }

        [HttpPost("multiply-get-matrix/")]
        [RequestSizeLimit(100_000_000)]
        public async Task<int[][]> MultiplyGetMatrices(IFormFileCollection files)
        {
            Matrix matrix1 = DeserializeMatrixFromFile(files[0]);
            Matrix matrix2 = DeserializeMatrixFromFile(files[1]);

            Matrix resultMatrix = _multiplyMatrix.Multiply(matrix1, matrix2);

            return resultMatrix.GetMatrix();
        }

        public static Matrix DeserializeMatrixFromFile(IFormFile file)
        {
            using var stream = new MemoryStream();
            file.CopyTo(stream);

            var utf8Reader = new Utf8JsonReader(stream.ToArray());
            int[][] matrix = JsonSerializer.Deserialize<int[][]>(ref utf8Reader)!;

            return new Matrix(matrix);
        }
    }
}
