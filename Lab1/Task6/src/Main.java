public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();
        CounterThread threadIncrement = new CounterThread(counter, 100000, true);
        CounterThread threadDecrement = new CounterThread(counter, 100000, false);

        RunTest test = new RunTest(counter, threadIncrement, threadDecrement);
        test.start();
    }
}