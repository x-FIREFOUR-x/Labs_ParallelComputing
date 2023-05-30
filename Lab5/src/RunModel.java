import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RunModel implements Callable<List<Double>> {

    private int indexModel;
    private final boolean activePrintLogger;

    private QueueModel queue;
    private long startTime;
    private long workingTime;

    private int countProducers;
    private int countConsumers;

    public RunModel(int indexModel, QueueModel queue, long startTime,
                    long workingTime, int countProducers , int countConsumers, boolean activePrintLogger)
    {
        this.indexModel = indexModel;
        this.activePrintLogger = activePrintLogger;
        this.queue = queue;
        this.startTime = startTime;
        this.workingTime = workingTime;

        this.countProducers = countProducers;
        this.countConsumers = countConsumers;
    }

    @Override
    public List<Double> call() {
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

        LoggerModel loggerModel = new LoggerModel(queue, startTime, workingTime, activePrintLogger);

        try{
            loggerModel.start();

            executor.invokeAll(callables);

            loggerModel.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

            int countRejected = queue.getCountRejected();
            int countRequest = queue.getCountRequested();
            double chanceOfRejected =(double)countRejected / countRequest;
            double averageSize = loggerModel.getAverageSizeQueue();
            System.out.println("Model: " + indexModel + "\n"
                    + "Chance rejection: " + chanceOfRejected + "\n"
                    + "Average size queue: " + averageSize + "\n"
                    + "Rejected: " + countRejected + "\n"
                    + "Requested: " + countRequest + "\n");

        executor.shutdown();

        List<Double> result = new ArrayList<Double>();
        result.add(chanceOfRejected);
        result.add(averageSize);

        return result;
    }
}
