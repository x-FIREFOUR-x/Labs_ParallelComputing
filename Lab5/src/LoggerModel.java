public class LoggerModel extends Thread{
    private boolean isPrint;

    private QueueModel queue;

    private final long cooldownTime = 100;

    private long timeStartWork;
    private long timeWorkOfLoger;


    private int totalSizeQueue = 0;
    private int count = 0;

    public double getAverageSizeQueue(){
        return (double)totalSizeQueue / count;
    }

    public LoggerModel(QueueModel queue, long timeStartWork, long timeWorkOfLoger, boolean isPrint) {
        this.queue = queue;
        this.timeStartWork = timeStartWork;
        this.timeWorkOfLoger = timeWorkOfLoger;
        this.isPrint = isPrint;
    }

    @Override
    public void run(){
        while(System.currentTimeMillis() - timeStartWork <= timeWorkOfLoger){
            if(isPrint){
                System.out.println("Requested: " + queue.getCountRequested() + "\n"
                        + "Served: " + queue.getCountServed() + "\n"
                        + "Rejected: " + queue.getCountRejected() + "\n"
                        + "Size queue: " + queue.getSize() + "\n"
                );
            }

            totalSizeQueue += queue.getSize();
            count ++;

            try {
                sleep(cooldownTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
