using System;
using System.Diagnostics;
using System.Threading.Tasks;

using MultiplyMatrix.Core;

namespace MultiplyMatrix.ConsoleClient
{
    class Program
    {
        private static int _countTest = 5;
        private static int _sizeMatrix = 5;
        static async Task Main(string[] args)
        {
            Stopwatch sw = new Stopwatch();
            TimeSpan totalTime = new TimeSpan();
            for (int i = 0; i < _countTest; i++)
            {
                sw.Start();

                //var resultMatrix = await RequestSender.SendRequestWithSize(_sizeMatrix);
                var resultMatrix = await RequestSender.SendRequestWithMatrix(_sizeMatrix);

                sw.Stop();
                Console.WriteLine("  time: {0}", sw.Elapsed);
                totalTime += sw.Elapsed;
                

                //Matrix matrix = new Matrix(resultMatrix);
                //matrix.Print();
            }
            Console.WriteLine("Average time: {0}", totalTime / _countTest);
        }
    }
}
