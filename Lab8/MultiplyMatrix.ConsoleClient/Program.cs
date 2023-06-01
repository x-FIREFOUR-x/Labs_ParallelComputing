using System;
using System.Diagnostics;
using System.Threading.Tasks;

using MultiplyMatrix.Core;

namespace MultiplyMatrix.ConsoleClient
{
    class Program
    {
        static async Task Main(string[] args)
        {
            Stopwatch sw = new Stopwatch();
            sw.Start();

            //var resultMatrix = await RequestSender.SendRequestWithSize(5);
            var resultMatrix = await RequestSender.SendRequestWithMatrix(5);

            sw.Stop();
            Console.WriteLine("Elapsed={0}", sw.Elapsed);

            Matrix matrix = new Matrix(resultMatrix);
            matrix.Print();
        }
    }
}
