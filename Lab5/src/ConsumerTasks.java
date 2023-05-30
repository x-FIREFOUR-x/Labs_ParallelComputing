import static java.lang.Thread.sleep;

public class ConsumerTasks implements Runnable{
    private QueueModel queue;

    private long timeStartWork;
    private long timeWorkOfConsumer;

    private final long consumeTime = 150;

    public ConsumerTasks(QueueModel queue, long timeStartWork, long timeWorkOfConsumer){
        this.queue = queue;
        this.timeStartWork = timeStartWork;
        this.timeWorkOfConsumer = timeWorkOfConsumer;
    }

    @Override
    public void run() {
        while(System.currentTimeMillis() - timeStartWork <= timeWorkOfConsumer){
            queue.pop();
            try {
                sleep(consumeTime);
            } catch (InterruptedException ignore) {
            }
        }
    }
}
