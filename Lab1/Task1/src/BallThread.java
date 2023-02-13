import java.time.Duration;
import java.time.Instant;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball){
        b = ball;
    }
    @Override
    public void run(){
        try{
            Instant start = Instant.now();

            for(int i=1; i<2000; i++){
                b.move();
                Thread.sleep(5);
            }
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println("Thread time working: " + timeElapsed / 1000 + "," + timeElapsed % 1000);

        } catch(InterruptedException ex){
        }finally {
            b.clear();
        }
    }
}
