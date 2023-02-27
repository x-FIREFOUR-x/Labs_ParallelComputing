import Counter.Counter;
import Counter.SyncCounter;
import Thread.CounterThread;
import Thread.SyncCounterThread;
import Counter.LockCounter;

public class Main {
    public static void main(String[] args) {
        testCounter();
        testSynchronizedMethod();
        testSynchronizedBlock();
        testLocker();
    }

    private static void testCounter(){
        Counter counter = new Counter();
        CounterThread threadIncrement = new CounterThread(counter, 100000, true);
        CounterThread threadDecrement = new CounterThread(counter, 100000, false);

        RunTest test = new RunTest(counter, threadIncrement, threadDecrement, "Count: ");
        test.start();
    }

    private static void testSynchronizedMethod(){
        SyncCounter counter = new SyncCounter();
        CounterThread threadIncrement = new CounterThread(counter, 100000, true);
        CounterThread threadDecrement = new CounterThread(counter, 100000, false);

        RunTest test = new RunTest(counter, threadIncrement, threadDecrement, "SynchronizedMethod: ");
        test.start();
    }

    private static void testSynchronizedBlock(){
        Counter counter = new Counter();
        SyncCounterThread threadIncrement = new SyncCounterThread(counter, 100000, true);
        SyncCounterThread threadDecrement = new SyncCounterThread(counter, 100000, false);

        RunTest test = new RunTest(counter, threadIncrement, threadDecrement, "SynchronizedBlock: ");
        test.start();
    }

    private static void testLocker(){
        LockCounter counter = new LockCounter();
        CounterThread threadIncrement = new CounterThread(counter, 100000, true);
        CounterThread threadDecrement = new CounterThread(counter, 100000, false);

        RunTest test = new RunTest(counter, threadIncrement, threadDecrement, "Locker: ");
        test.start();
    }
}