import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private ObjectCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm 2");
        this.canvas = new ObjectCanvas();
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());

        Container content = this.getContentPane();

        InitializeHoleAndCounter(content);

        content.add(this.canvas, BorderLayout.CENTER);

        InitializeButton(content);
    }

    private void InitializeHoleAndCounter(Container content)
    {
        JPanel counterPanel =  new JPanel();
        counterPanel.setBackground(Color.lightGray);
        JLabel labelCounter = new JLabel("In hole: 0");
        counterPanel.add(labelCounter);
        content.add(counterPanel, BorderLayout.NORTH);

        CounterHitsHoles counterHitsHoles = CounterHitsHoles.Instance();
        counterHitsHoles.setLabel(labelCounter);

        Hole hole = new Hole(canvas);
        canvas.addHole(hole);
    }

    private void InitializeButton(Container content)
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonOneBalls = new JButton("1 Ball");
        JButton buttonHundredBalls = new JButton("100 Ball");
        JButton buttonThousandBalls = new JButton("1000 Ball");
        JButton buttonStop = new JButton("Stop");

        buttonOneBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b = new Ball(canvas);
                canvas.addBall(b);

                BallThread thread = new BallThread(b);
                thread.start();
                System.out.println("Created thread name = " +
                        thread.getName());
            }
        });

        buttonHundredBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 100; i++) {
                    Ball b = new Ball(canvas);
                    canvas.addBall(b);

                    BallThread thread = new BallThread(b);
                    thread.start();
                    System.out.println("Created thread name = " +
                            thread.getName());
                }
            }
        });

        buttonThousandBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 1000; i++) {
                    Ball b = new Ball(canvas);
                    canvas.addBall(b);

                    BallThread thread = new BallThread(b);
                    thread.start();
                    System.out.println("Created thread name = " +
                            thread.getName());
                }
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

        JButton button = new JButton("Clear");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Created thread name = " +
                        canvas.getBalls().size());
                canvas.repaint();
            }
        });
        buttonPanel.add(button);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}