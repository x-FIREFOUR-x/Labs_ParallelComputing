import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Hole {
    private Component canvas;
    private static final int XSIZE = 60;
    private static final int YSIZE = 60;
    private int x = 0;
    private int y= 0;

    public Hole(Component c){
        this.canvas = c;
    }

    public void draw (Graphics2D g2){
        g2.setColor(Color.yellow);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public boolean intersection(int x, int y) {
        return x >= this.x && x <= this.x + XSIZE && y >= this.y && y <= this.y + YSIZE;
    }
}
