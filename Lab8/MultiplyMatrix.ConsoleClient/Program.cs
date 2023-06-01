using System;
using System.Diagnostics;
using System.Threading.Tasks;

namespace MultiplyMatrix.ConsoleClient
{
    class Program
    {
        private static int _countTest = 5;
        private static int _sizeMatrix = 500;
        static async Task Main(string[] args)
        {
            TimeSpan totalTime = new TimeSpan();
            for (int i = 0; i < _countTest + 1; i++)
            {
                Stopwatch sw = new Stopwatch();
                sw.Start();

                var resultMatrix = await RequestSender.SendRequestWithMatrix(_sizeMatrix);
                //var resultMatrix = await RequestSender.SendRequestWithSize(_sizeMatrix);
                
                sw.Stop();
                Console.WriteLine("  time: {0}", sw.Elapsed);
                if (i != 0)
                {
                    totalTime += sw.Elapsed;
                }
                
                
                //Matrix matrix = new Matrix(resultMatrix);
                //matrix.Print();
            }
            var miliseconds = totalTime.TotalMilliseconds;
            Console.WriteLine("Average time: {0}", totalTime / _countTest);
            Console.WriteLine("Average time: {0}", miliseconds / _countTest);
        }
    }
}
