import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private ObjectCanvas canvas;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 550;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm 4");
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

        JCheckBox checkBox = new JCheckBox("Hole");
        checkBox.setSelected(true);
        canvas.checkBoxActive = checkBox;
        buttonPanel.add(checkBox);

        JButton buttonTwoBalls = new JButton("2 Balls");
        buttonTwoBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball blueBall = new Ball(canvas, Color.blue);
                Ball redBall = new Ball(canvas, Color.red);
                canvas.addBall(blueBall);
                canvas.addBall(redBall);

                BallThread thread = new BallThread(blueBall);
                JoinBallThread joinThread = new JoinBallThread(redBall, thread);
                thread.start();
                joinThread.start();

                System.out.println("Created thread name = " + canvas.countBall());
            }
        });
        buttonPanel.add(buttonTwoBalls);

        JButton buttonFiveBalls = new JButton("5 Balls");
        buttonFiveBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball blueBall = new Ball(canvas, Color.blue);
                canvas.addBall(blueBall);
                BallThread thread = new BallThread(blueBall);
                thread.start();

                BallThread prevThread = thread;

                for (int i = 0; i < 4; i++) {
                    Ball redBall = new Ball(canvas, Color.red);
                    canvas.addBall(redBall);
                    JoinBallThread joinThread = new JoinBallThread(redBall, prevThread);
                    joinThread.start();
                    prevThread = joinThread;
                }

                System.out.println("Created thread name = " + canvas.countBall());
            }
        });
        buttonPanel.add(buttonFiveBalls);

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
}
