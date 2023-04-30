import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RunModel extends Thread {

    private QueueModel queue;
    private long startTime;
    private long workingTime;

    private int countProducers;
    private int countConsumers;

    public RunModel(QueueModel queue, long startTime, long workingTime, int countProducers , int countConsumers){
        this.queue = queue;
        this.startTime = startTime;
        this.workingTime = workingTime;

        this.countProducers = countProducers;
        this.countConsumers = countConsumers;
    }

    @Override
    public void run() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        List<Callable<Void>> callables = new ArrayList<>();

        for (int i = 0; i < countProducers; i++) {
            callables.add(Executors.callable(
                    new ProducerTasks(queue, startTime, workingTime), null));
        }

        for (int i = 0; i < countConsumers; i++) {
            callables.add(
                    Executors.callable(new ConsumerTasks(queue, startTime, workingTime), null));
        }

        try{
            executor.invokeAll(callables);

            int countRejected = queue.getCountRejected();
            int countRequest = queue.getCountRequest();

            double chanceOfRejected =(double)countRejected / countRequest;

            System.out.println("Chance rejection:" + chanceOfRejected);
            System.out.println("Reject:" + countRejected);
            System.out.println("Request:" + countRequest);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
    }
}
