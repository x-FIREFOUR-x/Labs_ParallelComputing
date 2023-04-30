public class Main {
    public static void main(String[] args) {
        QueueModel queue = new QueueModel(10);

        long timeStart = System.currentTimeMillis();
        long timeWorkingModel = 6000;

        RunModel model = new RunModel(queue, timeStart, timeWorkingModel, 1,  3);
        model.start();

        try{
            model.join();
        }catch (InterruptedException ignore){
        }
    }
}