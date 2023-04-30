public class LoggerModel extends Thread{
    private QueueModel queue;

    private final long cooldownTime = 100;

    private long timeStartWork;
    private long timeWorkOfLoger;

    public LoggerModel(QueueModel queue, long timeStartWork, long timeWorkOfLoger) {
        this.queue = queue;
        this.timeStartWork = timeStartWork;
        this.timeWorkOfLoger = timeWorkOfLoger;
    }

    @Override
    public void run(){
        while(System.currentTimeMillis() - timeStartWork <= timeWorkOfLoger){
            System.out.println("Requested:" + queue.getCountRequested() + "\n"
                    + "Served: " + queue.getCountServed() + "\n"
                    + "Rejected:" + queue.getCountRejected() + "\n"
            );

            try {
                sleep(cooldownTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
