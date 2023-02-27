public class JoinBallThread extends BallThread {

    private BallThread prevThread;

    public JoinBallThread(Ball ball, BallThread prevThread) {
        super(ball);
        this.prevThread = prevThread;
    }

    @Override
    public void run(){
        try {
            prevThread.join();
            super.run();
        } catch (InterruptedException e) {
            super.interrupt();
        }
        finally {
            b.clear();
        }
    }
}
