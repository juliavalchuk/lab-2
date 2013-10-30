import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RingBufferSolution implements TransitSolution {
    RingBuffer<Transaction> ringBuffer;
    final int BUFFR_SIZE = 100;
    Bank bank;
    Thread producer;
    ExecutorService executorService;

    public RingBufferSolution()
    {
        ringBuffer = new RingBuffer<Transaction>(BUFFR_SIZE);
    }

    public Bank startTransaction(final Bank bank, final Transaction[] transactions)
    {

        initialise(transactions);

        this.bank = bank;
        while (producer.isAlive())
        {
            executorService.submit(new Consumer(bank, ringBuffer.pop()));
        }

        stop();

        return this.bank;
    }

    private void initialise(Transaction[] transactions)
    {
        Producer prod = new Producer(ringBuffer, transactions);
        producer = new Thread(prod);
        executorService = Executors.newCachedThreadPool();

        producer.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void stop()
    {
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
