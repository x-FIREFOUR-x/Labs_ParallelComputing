import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import static java.lang.Thread.sleep;

public class QueueModel {
    private final ArrayList<Integer> items;
    private int maxSize;
    private int currentSize;

    private ReentrantLock locker = new ReentrantLock();
    final Condition notEmpty = locker.newCondition();

    private int countRejected = 0;
    private int countRequest = 0;

    public int getCountRejected(){
        return countRejected;
    }

    public int getCountRequest(){
        return countRequest;
    }

    public QueueModel(int size) {
        items = new ArrayList<>();
        maxSize = size;
        currentSize = 0;
    }

    public void put(int item){
        locker.lock();
        try {
            countRequest++;
            if(currentSize == maxSize){
                countRejected++;
                return;
            }
            else {
                items.add(item);
                currentSize++;
                notEmpty.signalAll();
            }
        }finally {
            locker.unlock();
        }
    }

    public int pop(){
            //pop with queue
        locker.lock();
        int item;
        try{
            while(currentSize == 0){
                notEmpty.await();
            }

            item = items.remove(0);
            currentSize--;
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }finally {
            locker.unlock();
        }

        return item;
    }
}
