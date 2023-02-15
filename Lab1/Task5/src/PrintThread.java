public class PrintThread extends Thread {
    char symbol;
    int countOutputSymbol;

    private static final Object locker = new Object();

    int idInSwitcher;

    public PrintThread(char symbol, int countOutputSymbol){
        this.symbol = symbol;
        this.countOutputSymbol = countOutputSymbol;
        idInSwitcher = SwitchThread.Instance().addThread();

    }

    public PrintThread(char symbol){
        this.symbol = symbol;
        this.countOutputSymbol = 2;
        idInSwitcher = SwitchThread.Instance().addThread();
    }

    @Override
    public void run(){
        if(SwitchThread.Instance().getActive()){
            for (int i = 0; i < countOutputSymbol; i++) {
                try {
                    regularPrint();
                } catch (InterruptedException e) {
                }
            }
        }
        else {
            for (int i = 0; i < countOutputSymbol; i++) {
                System.out.print(symbol);
            }
        }
    }

    private void regularPrint() throws InterruptedException{
        synchronized (locker){
            while(!SwitchThread.Instance().isPermission(idInSwitcher)){
                locker.wait();
            }
            System.out.print(symbol);

            SwitchThread.Instance().toSwitchPermission();
            locker.notifyAll();
        }
    }
}
