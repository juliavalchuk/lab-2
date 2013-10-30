import com.lmax.disruptor.EventFactory;

public final class TransactionEvent {
    private Transaction value;

    public Transaction getValue() {
        return value;
    }

    public void setValue(Transaction value) {
        this.value = value;
    }

    public final static EventFactory<TransactionEvent> EVENT_FACTORY = new EventFactory<TransactionEvent>() {
        public TransactionEvent newInstance() {
            return new TransactionEvent();
        }
    };
}
