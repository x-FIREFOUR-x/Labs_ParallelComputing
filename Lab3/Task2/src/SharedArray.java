import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {
    private final ArrayList<Integer> array;

    private final ReentrantLock locker = new ReentrantLock();

    public SharedArray(int size) {
        this.array = new ArrayList<>(size);
    }

    public synchronized int take() {
        while (array.size() == 0) {
            try {
                wait();
            } catch (InterruptedException ignore){}
        }
        int value = array.get(0);
        array.remove(0);
        return value;
    }


    public synchronized void put(int element) {
        array.add(element);
        notifyAll();
    }
}
