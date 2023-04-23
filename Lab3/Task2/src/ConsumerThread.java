import java.time.LocalTime;
import java.util.Random;

public class ConsumerThread extends Thread{
    private final SharedArray sharedArray;

    public ConsumerThread(SharedArray sharedArray) {
        this.sharedArray = sharedArray;
    }

    public void run(){
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int value = sharedArray.take();
            System.out.println("(" + LocalTime.now() + ")"
                    + " CONSUMER Get element: " + value);
        }
    }
}
