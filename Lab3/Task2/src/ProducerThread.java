import java.util.Random;
import java.time.LocalTime;

public class ProducerThread extends Thread{
    private final SharedArray sharedArray;

    public ProducerThread(SharedArray sharedArray) {
        this.sharedArray = sharedArray;
    }

    @Override
    public void run(){
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int value = (Math.abs(random.nextInt()) % 100) + 1;
            sharedArray.put(value);
            System.out.println("(" + LocalTime.now() + ")"
                    + " PRODUCER Put element: " + value);
        }
    }
}
