package Counter;
import java.util.concurrent.locks.ReentrantLock;

public class LockCounter implements ICounter {
    private int count = 0;

    ReentrantLock locker = new ReentrantLock();

    public int getCount() {
        return count;
    }


    public void increment() {
        locker.lock();
        try {
            count++;
        }
        finally {
            locker.unlock();
        }
    }


    public void decrement() {
        locker.lock();
        try {
            count--;
        }
        finally {
            locker.unlock();
        }
    }
}
