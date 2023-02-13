import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class BallCanvas extends JPanel {
    private volatile ArrayList<Ball> balls = new ArrayList<>();

    public void addBall(Ball b) {
        this.balls.add(b);
    }
    public synchronized void removeBall(Ball b) {
        this.balls.remove(b);
    }

    public int countBall(){ return balls.size();}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < balls.size(); i++) {
            Ball b = balls.get(i);
            b.draw(g2);
        }
    }
}
