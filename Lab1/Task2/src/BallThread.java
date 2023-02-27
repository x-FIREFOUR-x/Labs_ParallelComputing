import java.time.Instant;
import java.time.Duration;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball){
        b = ball;
    }
    @Override
    public void run(){
        try{
            while(true){
                if(b.isHitTheHoles()){
                    break;
                }
                b.move();
                Thread.sleep(5);
            }
            CounterHitsHoles.Instance().upCounter();

        } catch(InterruptedException ex){

        } finally {
            b.clear();
        }
    }
}
