import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {
    private final ArrayList<Integer> array;

    public SharedArray(int size) {
        this.array = new ArrayList<>(size);
    }

    public int take() {
        synchronized (this){
            if(array.size() > 0){
                int value = array.get(0);
                array.remove(0);
                return value;
            }
            else {
                System.out.println(array.size());
                return 0;

            }
        }
    }

    public void put(int element) {
        synchronized (this){
            array.add(element);
        }
    }
}
