import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) {
        QueueModel queue = new QueueModel(10);
        QueueModel queue2 = new QueueModel(10);
        QueueModel queue3 = new QueueModel(10);

        long timeStart = System.currentTimeMillis();
        long timeWorkingModel = 5000;

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        List<Callable<Void>> models = new ArrayList<>();

        RunModel model = new RunModel(
                1,  queue, timeStart, timeWorkingModel, 1,  3, false);
        models.add(Executors.callable(model, null));


        RunModel model2 = new RunModel(
                2, queue2, timeStart, timeWorkingModel, 1,  3, false);
        models.add(Executors.callable(model2, null));
        RunModel model3 = new RunModel(
                3, queue3, timeStart, timeWorkingModel, 1,  3, false);
        models.add(Executors.callable(model3, null));


        try{
            executor.invokeAll(models);
        }catch (InterruptedException ignore){
        }

        executor.shutdown();
    }
}