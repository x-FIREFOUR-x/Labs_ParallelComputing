public class ProducerThread extends Thread{
    private final SharedArray sharedArray;
    private final int count;

    public ProducerThread(SharedArray sharedArray, int count) {
        this.sharedArray = sharedArray;
        this.count = count;
    }

    @Override
    public void run(){
        for (int i = 1; i <= count; i++) {
            sharedArray.put(i);
        }
    }
}
