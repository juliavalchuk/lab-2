import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class RingBuffer<T> {
    private final int SIZE;
    private volatile int head;
    private volatile int tail;
    private AtomicInteger currsize;
   // private AtomicReferenceArray<T> elements;
    private T[] elements;

    public RingBuffer(int size) {
        SIZE = size;
      //  elements = new AtomicReferenceArray<T>(size);
        elements = (T[]) new Object[size];
        currsize = new AtomicInteger(0);
       // head = -1;
    }

    public boolean push(T elem)
    {
        while (isFull()) {}
        if(!isFull())
        {
            currsize.getAndIncrement();
           // elements.set(tail, elem);
            elements[tail] = elem;
            tail = increment(tail);

            return true;
        }
        return false;
    }

    public T pop()
    {

        if(!isEmpty())
        {
            T elem;
            currsize.getAndDecrement();
            head = increment(head);
           // elem = elements.get(head);
            elem = elements[head];

            return elem;
        }
        return null;
    }

    public boolean isEmpty()
    {
        //return tail - 1 - head % SIZE == 0;
        return currsize.intValue() == 0;
    }

    public boolean isFull()
    {
       // return tail == head;
        return currsize.intValue() == SIZE;
    }

    private int increment(int a)
    {
        return (a + 1) % SIZE;
    }

}
