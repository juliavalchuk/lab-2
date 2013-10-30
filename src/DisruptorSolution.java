import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DisruptorSolution implements TransitSolution {

    private static final int ENTRIES = 64;

    private  ExecutorService executorService;
    private  Disruptor<TransactionEvent> disruptor;
    private  RingBuffer<TransactionEvent> ringBuffer;
    private final EventHandler<TransactionEvent> handler;
    private Bank bank;

    public DisruptorSolution() {
        handler = new EventHandler<TransactionEvent>() {
            public void onEvent(final TransactionEvent event, final long sequence, final boolean endOfBatch) throws Exception {
                bank.transactMoney(event.getValue());
            }
        };
    }

    public Bank startTransaction(final Bank bank, final Transaction[] transactions)
    {
        initialise();

        this.bank = bank;
        int n = transactions.length;
        for(int i = 0; i < n; ++i)
            transit(transactions[i]);

        stop();
        return this.bank;
    }

    private void transit(Transaction transaction)
    {
        final long sequence = ringBuffer.next();
        final TransactionEvent valueEvent = ringBuffer.get(sequence);
        valueEvent.setValue(transaction);
        ringBuffer.publish(sequence);
    }

    private void initialise()
    {
        executorService = Executors.newCachedThreadPool();
        disruptor  = new Disruptor<TransactionEvent>(TransactionEvent.EVENT_FACTORY, ENTRIES, executorService);

        disruptor.handleEventsWith(handler);
        ringBuffer = disruptor.start();
    }

    public void stop()
    {
        disruptor.shutdown();
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
