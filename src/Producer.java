
public class Producer implements Runnable{
    private RingBuffer<Transaction> transactionRingBuffer;
    private Transaction[] transactions;
    private final int SIZE;
    int i;

    public Producer(RingBuffer<Transaction> transaction, Transaction[] array)
    {
        transactionRingBuffer = transaction;
        transactions = array;
        SIZE = array.length;
    }

    private void addToBuffer(int i)
    {
        transactionRingBuffer.push(transactions[i]);
    }

    @Override
    public void run() {

        while (i < SIZE)
        {
            addToBuffer(i++);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }
}
