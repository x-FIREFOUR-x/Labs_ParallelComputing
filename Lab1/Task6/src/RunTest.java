import Counter.ICounter;
import Thread.AThread;

public class RunTest extends Thread {
    private ICounter counter;
    private AThread incThread;
    private AThread decThread;
    private String message;

    public RunTest(ICounter counter, AThread incThread, AThread decThread, String message){
        this.counter = counter;
        this.incThread = incThread;
        this.decThread = decThread;
        this.message = message;
    }

    @Override
    public void run(){
        try{
            super.run();
            incThread.start();
            decThread.start();

            incThread.join();
            decThread.join();

            System.out.println(message + counter.getCount());
        } catch (InterruptedException e) {

         }
    }
}
