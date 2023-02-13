import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ObjectCanvas extends JPanel {

    public JCheckBox checkBoxActive;
    private volatile ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Hole> holes = new ArrayList<>();

    public ArrayList<Hole> getHoles() {return holes;};

    public ArrayList<Ball> getBalls() {return balls;};

    public void addBall(Ball b) {
        this.balls.add(b);
    }

    public synchronized void removeBall(Ball b) {
        this.balls.remove(b);
    }

    public int countBall(){ return balls.size();}

    public void addHole(Hole h) {
        this.holes.add(h);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            if (ball != null){
                ball.draw(g2);
            }
        }

        if (checkBoxActive.isSelected()){
            for (int i = 0; i < holes.size(); i++) {
                Hole hole = holes.get(i);
                hole.draw(g2);
            }
        }
    }
}
