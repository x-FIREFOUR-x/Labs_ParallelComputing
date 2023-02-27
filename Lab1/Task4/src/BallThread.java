public class BallThread extends Thread {
    protected Ball b;

    public BallThread(Ball ball){
        b = ball;
    }
    @Override
    public void run(){
        try{
            for (int i = 0; i < 1000; i++) {
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
