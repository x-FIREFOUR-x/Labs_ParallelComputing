import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm 1");
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
        JButton buttonOneBalls = new JButton("1 Ball");
        JButton buttonHundredBalls = new JButton("100 Ball");
        JButton buttonThousandBalls = new JButton("3000 Ball");
        JButton buttonStop = new JButton("Stop");

        buttonOneBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBall(BallType.NORMAL);
                createBall(BallType.IMPORTANT);
            }
        });

        buttonHundredBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 100; i++) {
                    createBall(BallType.NORMAL);
                }
                createBall(BallType.IMPORTANT);
            }
        });

        buttonThousandBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 3000; i++) {
                    createBall(BallType.NORMAL);
                }
                createBall(BallType.IMPORTANT);
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(buttonOneBalls);
        buttonPanel.add(buttonHundredBalls);
        buttonPanel.add(buttonThousandBalls);
        buttonPanel.add(buttonStop);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createBall(BallType type){
        Ball b = new Ball(canvas, type);
        canvas.addBall(b);

        BallThread thread = new BallThread(b);
        thread.start();
        System.out.println("Created thread name = " +
                thread.getName());
    }
}
