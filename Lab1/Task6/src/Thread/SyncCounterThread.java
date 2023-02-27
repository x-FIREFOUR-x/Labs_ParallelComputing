package Thread;

import Counter.ICounter;

public class SyncCounterThread extends AThread{
    public SyncCounterThread(ICounter counter, int countIterations, boolean isIncrement) {
        super(counter, countIterations, isIncrement);
    }

    @Override
    public void run(){
        if (this.isIncrement){
            for (int i = 0; i < countIterations; i++) {
                synchronized (counter){
                    counter.increment();
                }
            }
        }
        else{
            for (int i = 0; i < countIterations; i++) {
                synchronized (counter){
                    counter.decrement();
                }
            }
        }
    }
}
