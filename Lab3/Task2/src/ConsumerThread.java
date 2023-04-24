public class ConsumerThread extends Thread{
    private final SharedArray sharedArray;

    private final int count;

    public ConsumerThread(SharedArray sharedArray, int count) {
        this.sharedArray = sharedArray;
        this.count = count;
    }

    public void run(){
        for (int i = 1; i <= count; i++) {
            int value = sharedArray.take();
            System.out.println(" CONSUMER Get element: " + value);
        }
    }
}
