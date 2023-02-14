import java.util.ArrayList;

public class SwitchThread {
    private static SwitchThread instance;

    private static boolean active;

    private static volatile int countThread = 0;
    private static volatile int nextThreadWork = 0;


    public static SwitchThread Instance(){
        if (instance == null){
            instance = new SwitchThread();
            active = false;
        }

        return  instance;
    }

    public void setActive(boolean a){
        active = a;
    }

    public boolean getActive(){
        return active;
    }

    public int addThread(){
        countThread++;
        return countThread - 1;
    }

    public synchronized boolean isPermission(int idThread)
    {
        return idThread == nextThreadWork;
    }

    public synchronized void toSwitchPermission(){
        nextThreadWork++;
        if (nextThreadWork == countThread){
            nextThreadWork = 0;
        }
    }

    public void resetSwitch(){
        countThread = 0;
        nextThreadWork = 0;
    }
}
