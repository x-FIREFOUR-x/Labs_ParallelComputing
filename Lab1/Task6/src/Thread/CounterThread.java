package Thread;
import Counter.ICounter;

public class CounterThread extends AThread{
    public CounterThread(ICounter counter, int countIterations, boolean isIncrement) {
        super(counter, countIterations, isIncrement);
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
