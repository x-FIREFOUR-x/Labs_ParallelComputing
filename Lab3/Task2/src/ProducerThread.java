public class ProducerThread extends Thread{
    private final SharedArray sharedArray;

    public ProducerThread(SharedArray sharedArray) {
        this.sharedArray = sharedArray;
    }

    @Override
    public void run(){
        int i = 1;
        while(true) {
            sharedArray.put(i);
            i++;
        }
    }
}
