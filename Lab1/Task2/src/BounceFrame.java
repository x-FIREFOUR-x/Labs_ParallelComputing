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

        JButton buttonTenThousandBalls = new JButton("10000 Ball");
        buttonTenThousandBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 10000; i++) {
                    createBall();
                }
            }
        });
        buttonPanel.add(buttonTenThousandBalls);

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

    private void createBall(){
        Ball b = new Ball(canvas);
        canvas.addBall(b);

        BallThread thread = new BallThread(b);
        thread.start();
        System.out.println("Created thread name = " +
                thread.getName());
    }
}
