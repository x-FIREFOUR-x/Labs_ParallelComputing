import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        QueueModel queue = new QueueModel(10);
        QueueModel queue2 = new QueueModel(10);
        QueueModel queue3 = new QueueModel(10);

        long timeStart = System.currentTimeMillis();
        long timeWorkingModel = 5000;

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        ArrayList<Future<List<Double>>> futures = new ArrayList<Future<List<Double>>>();

        int countModel = 3;

        RunModel model = new RunModel(
                1,  queue, timeStart, timeWorkingModel, 1, 3, true);
        futures.add(executor.submit(model));


        RunModel model2 = new RunModel(
                2, queue2, timeStart, timeWorkingModel, 1,  3, false);
        futures.add(executor.submit(model2));
        RunModel model3 = new RunModel(
                3, queue3, timeStart, timeWorkingModel, 1,  3, false);
        futures.add(executor.submit(model3));

        List<Double> result;
        double chanceOfRejected = 0;
        double averageSize = 0;
        for (int i = 0; i < countModel; i++) {
            try {
                result = futures.get(i).get();
                chanceOfRejected += result.get(0);
                averageSize += result.get(1);
            } catch (Exception ignored) {
            }
        }

        System.out.println( "Average chance rejection: " + chanceOfRejected / countModel + "\n"
                + "Average average size queue: " + averageSize / countModel + "\n");

        executor.shutdown();
    }
}