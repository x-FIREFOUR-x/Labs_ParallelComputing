import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

enum BallType{
    NORMAL,
    IMPORTANT
}

public class Ball {
    private Component canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x = 0;
    private int y= 0;
    private int dx = 2;
    private int dy = 2;

    private BallType type;

    public Ball(Component c, BallType t){
        this.canvas = c;
        type = t;

        /*
        if(Math.random()<0.5){
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        }else{
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
        */
        x = 100;
        y = 100;
    }

    public void draw (Graphics2D g2){
        if (type == BallType.NORMAL) {
            g2.setColor(Color.blue);
            g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
        }
        else{
            g2.setColor(Color.red);
            g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
        }
    }

    public void move(){
        x+=dx;
        y+=dy;
        if(x<0){
            x = 0;
            dx = -dx;
        }
        if(x+XSIZE>=this.canvas.getWidth()){
            x = this.canvas.getWidth()-XSIZE;
            dx = -dx;
        }
        if(y<0){
            y=0;
            dy = -dy;
        }
        if(y+YSIZE>=this.canvas.getHeight()){
            y = this.canvas.getHeight()-YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }

    public BallType getType()
    {
        return type;
    }
}