public class CounterThread extends Thread{
    private final Counter counter;

    private int countIterations;

    private boolean isIncrement;

    public CounterThread(Counter counter, int countIterations, boolean isIncrement) {
        this.counter = counter;
        this.countIterations = countIterations;
        this.isIncrement = isIncrement;
    }

    @Override
    public void run(){
        if (this.isIncrement){
            for (int i = 0; i < countIterations; i++) {
                counter.increment();
            }
        }
        else{
            for (int i = 0; i < countIterations; i++) {
                counter.decrement();
            }
        }
    }
}
