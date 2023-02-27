import javax.swing.*;

public class CounterHitsHoles {
    private static CounterHitsHoles instance;
    private static int count;
    private static JLabel counterLabel;

    public static CounterHitsHoles Instance(){
        if (instance == null){
            instance = new CounterHitsHoles();
            count = 0;
        }
        return instance;
    }

    public void setLabel(JLabel label){
        counterLabel = label;
    }

    public synchronized void upCounter(){
        count++;
        counterLabel.setText("In hole: " + count);
    }


}
