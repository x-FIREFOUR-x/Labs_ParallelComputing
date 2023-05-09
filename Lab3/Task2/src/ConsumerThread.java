public class ConsumerThread extends Thread{
    private final SharedArray sharedArray;

    public ConsumerThread(SharedArray sharedArray) {
        this.sharedArray = sharedArray;
    }

    public void run(){
        while (true) {
            int value = sharedArray.take();
            System.out.println(" CONSUMER Get element: " + value);
        }
    }
}
