import java.util.Random;

import static java.lang.Thread.sleep;

public class ProducerTasks implements Runnable {
    private QueueModel queue;

    private long timeStartWork;
    private long timeWorkOfProducer;

    private int minProduceTime = 20;
    private int maxProduceTime = 60;

    private Random random;

    public ProducerTasks(QueueModel queue, long timeStartWork, long timeWorkOfProducer){
        this.queue = queue;
        this.timeStartWork = timeStartWork;
        this.timeWorkOfProducer = timeWorkOfProducer;

        random = new Random();
    }


    @Override
    public void run() {
        while(System.currentTimeMillis() - timeStartWork <= timeWorkOfProducer){
            int produceTime = random.nextInt((maxProduceTime - minProduceTime) + 1) + minProduceTime;
            try {
                sleep(produceTime);
            } catch (InterruptedException e) {
            }
            queue.put(1);
        }
    }
}
