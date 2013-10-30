
public class Consumer implements Runnable {
    private Transaction transaction;
    private Bank bank;

    public Consumer(Bank bank, Transaction transaction)
    {
        this.bank = bank;
        this.transaction = transaction;
    }

    @Override
    public void run() {
        bank.transactMoney(transaction);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
