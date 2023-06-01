using Newtonsoft.Json;
using System;
using System.Net.Http;
using System.Threading.Tasks;

using MultiplyMatrix.Core;
using System.Text;

namespace MultiplyMatrix.ConsoleClient
{
    class RequestSender
    {
        private static async Task<int[][]> SendRequest(string requestUri, HttpContent? content)
        {
            using var client = new HttpClient();
            var response = await client.PostAsync(requestUri, content);
            if (response.IsSuccessStatusCode)
            {
                var base64String = await response.Content.ReadAsStringAsync();
                var result = JsonConvert.DeserializeObject<int[][]>(base64String);

                return result;
            }

            throw new Exception($"Error: {response.StatusCode}");
        }

        public static async Task<int[][]> SendRequestWithSize(int matrixSize)
        {
            return await SendRequest($"http://localhost:30654/api/MultiplyMatrix/multiply-generated-matrix/{matrixSize}", null);
        }

        public static async Task<int[][]> SendRequestWithMatrix(int matrixSize)
        {
            var matrix1 = new Matrix(matrixSize, matrixSize, 1, 1);
            var matrix2 = new Matrix(matrixSize, matrixSize, 1, 1);

            var data = new { Matrix1 = matrix1.GetMatrix(), Matrix2 = matrix2.GetMatrix() };
            var json = JsonConvert.SerializeObject(data);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            return await SendRequest($"http://localhost:30654/api/MultiplyMatrix/multiply-get-matrix", content);
        }
    }
}
