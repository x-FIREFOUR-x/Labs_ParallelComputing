public class AsyncBankTest {
    public static final int AMOUNT_ACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {
        Bank b = new Bank(AMOUNT_ACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < AMOUNT_ACCOUNTS; i++){
            TransferThread thread = new TransferThread(b, i, INITIAL_BALANCE);
            thread.setPriority(Thread.NORM_PRIORITY + i % 2);
            thread.start();
        }
    }
}
