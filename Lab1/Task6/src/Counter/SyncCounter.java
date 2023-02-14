package Counter;

public class SyncCounter implements ICounter {

    private volatile int count = 0;
    public int getCount() {
        return count;
    }

    public synchronized void increment() {
        count++;
    }

    public synchronized void decrement() {
        count--;
    }
}
