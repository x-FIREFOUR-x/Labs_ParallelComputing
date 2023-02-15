import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BounceFrame extends JFrame {
    private JCheckBox checkBox;
    public static final int WIDTH = 350;
    public static final int HEIGHT = 140;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm 5");
        Container content = this.getContentPane();

        checkBox = new JCheckBox("Switch of threads");
        content.add(checkBox, BorderLayout.NORTH);

        JPanel panel1 = InitializeBlock1(content);
        JPanel panel2 = InitializeBlock2(content);

        content.add(panel1, BorderLayout.CENTER);
        content.add(panel2, BorderLayout.AFTER_LAST_LINE);
    }

    private JPanel InitializeBlock1(Container content)
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.blue);

        JButton buttonTwoThread = new JButton("Start 2 Threads");
        buttonTwoThread.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println();

                SwitchThread.Instance().setActive(checkBox.isSelected());
                SwitchThread.Instance().resetSwitch();

                PrintThread thread1 = new PrintThread(checkBox, '-', 50);
                PrintThread thread2 = new PrintThread(checkBox, '|', 50);
                thread1.start();
                thread2.start();
            }
        });
        panel.add(buttonTwoThread);

        JButton buttonThreeThread = new JButton("Start 3 Threads");
        buttonThreeThread.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println();

                SwitchThread.Instance().setActive(checkBox.isSelected());
                SwitchThread.Instance().resetSwitch();

                PrintThread thread1 = new PrintThread(checkBox, '-', 50);
                PrintThread thread2 = new PrintThread(checkBox, '|', 50);
                PrintThread thread3 = new PrintThread(checkBox, '*', 50);
                thread1.start();
                thread2.start();
                thread3.start();
            }
        });
        panel.add(buttonThreeThread);

        return panel;
    }

    private JPanel InitializeBlock2(Container content){
        JPanel panel = new JPanel();
        panel.setBackground(Color.yellow);

        JSpinner inputField = new JSpinner();
        panel.add(inputField);

        JButton button = new JButton("Start n Threads");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println();
                int countThreads = (int)inputField.getValue();

                if (countThreads > 0)
                {
                    SwitchThread.Instance().setActive(checkBox.isSelected());
                    SwitchThread.Instance().resetSwitch();

                    ArrayList<PrintThread> threads = new ArrayList<PrintThread>();
                    for (int i = 0; i < countThreads; i++) {
                        PrintThread thread = new PrintThread(checkBox, (char)(i + 33), 25);
                        threads.add(thread);
                    }
                    for (int i = 0; i < threads.size(); i++) {
                        threads.get(i).start();
                    }
                }

            }
        });
        panel.add(button);

        return panel;
    }
}
