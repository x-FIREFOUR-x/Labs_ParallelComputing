import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 550;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm 3");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());

        Container content = this.getContentPane();

        content.add(canvas, BorderLayout.CENTER);

        InitializeButton(content);
    }

    private void InitializeButton(Container content)
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton buttonOneBalls = new JButton("1 Normal, 1 Priority");
        buttonOneBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBall(BallType.NORMAL);
                createBall(BallType.IMPORTANT);
                System.out.println("Created thread name = " + canvas.countBall());
            }
        });
        buttonPanel.add(buttonOneBalls);

        JButton buttonHundredBalls = new JButton("100N, 1P Balls");
        buttonHundredBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 100; i++) {
                    createBall(BallType.NORMAL);
                }
                createBall(BallType.IMPORTANT);
                System.out.println("Created thread name = " + canvas.countBall());
            }
        });
        buttonPanel.add(buttonHundredBalls);

        JButton buttonThousandBalls = new JButton("3000N, 1P Balls");
        buttonThousandBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 3000; i++) {
                    createBall(BallType.NORMAL);
                }
                createBall(BallType.IMPORTANT);
                System.out.println("Created thread name = " + canvas.countBall());
            }
        });
        buttonPanel.add(buttonThousandBalls);

        JButton buttonOneNormalBalls = new JButton("3000P, 1N Balls");
        buttonOneNormalBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 3000; i++) {
                    createBall(BallType.IMPORTANT);
                }
                createBall(BallType.NORMAL);
                System.out.println("Created thread name = " + canvas.countBall());
            }
        });
        buttonPanel.add(buttonOneNormalBalls);

        JButton buttonStop = new JButton("Stop");
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(buttonStop);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createBall(BallType type){
        Ball b = new Ball(canvas, type);
        canvas.addBall(b);

        BallThread thread = new BallThread(b);
        thread.start();
    }
}
