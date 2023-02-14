public class RunTest extends Thread {
    private Counter counter;
    private CounterThread incThread;
    private CounterThread decThread;

    public RunTest(Counter counter, CounterThread incThread, CounterThread decThread){
        this.counter = counter;
        this.incThread = incThread;
        this.decThread = decThread;
    }

    @Override
    public void run(){
        try{
            super.run();
            incThread.start();
            decThread.start();

            incThread.join();
            decThread.join();

            System.out.println("Count: " + counter.getCount());
        } catch (InterruptedException e) {

         }
    }
}
