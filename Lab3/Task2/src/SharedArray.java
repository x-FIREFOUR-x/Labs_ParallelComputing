import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {
    private final ArrayList<Integer> array;
    private final int maxSize;

    public SharedArray(int maxSize) {
        this.maxSize = maxSize;
        this.array = new ArrayList<>();
    }

    public int take() {
        synchronized (this) {
            while (array.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException ignore) {
                }
            }
            int value = array.get(0);
            array.remove(0);
            notifyAll();
            return value;
        }
    }


    public void put(int element) {
        synchronized (this) {
            while (array.size() >= maxSize) {
                try {
                    wait();
                } catch (InterruptedException ignore) {
                }
            }

            array.add(element);
            notifyAll();
        }
    }
}
