package Thread;

import Counter.ICounter;

public class AThread extends Thread {

    protected final ICounter counter;

    protected int countIterations;

    protected boolean isIncrement;

    public AThread(ICounter counter, int countIterations, boolean isIncrement) {
        this.counter = counter;
        this.countIterations = countIterations;
        this.isIncrement = isIncrement;
    }
}
