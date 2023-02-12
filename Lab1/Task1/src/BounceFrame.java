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
        content.add(this.canvas, BorderLayout.CENTER);

        InitializeButton(content);
    }

    private void InitializeButton(Container content) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton buttonOneBalls = new JButton("1 Ball");
        buttonOneBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBall();
            }
        });
        buttonPanel.add(buttonOneBalls);

        JButton buttonHundredBalls = new JButton("100 Ball");
        buttonHundredBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 100; i++) {
                    createBall();
                }
            }
        });
        buttonPanel.add(buttonHundredBalls);

        JButton buttonThousandBalls = new JButton("1000 Ball");
        buttonThousandBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 1000; i++) {
                    createBall();
                }
            }
        });
        buttonPanel.add(buttonThousandBalls);

        JButton buttonClear = new JButton("Clear");
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(buttonClear);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createBall()
    {
        Ball b = new Ball(canvas);
        canvas.add(b);

        BallThread thread = new BallThread(b);
        thread.start();
        System.out.println("Created thread name = " +
                thread.getName());
    }
}
