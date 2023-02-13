public class PrintThread extends Thread {
    char symbol;
    int countOutputSymbol;

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
        while (countOutputSymbol != 0) {

            if(SwitchThread.Instance().getActive()){
                if(SwitchThread.Instance().isPermission(idInSwitcher)){
                    System.out.print(symbol);
                    countOutputSymbol--;
                    SwitchThread.Instance().toSwitchPermission();
                }
            }
            else {
                System.out.print(symbol);
                countOutputSymbol--;
            }
        }
    }
}
