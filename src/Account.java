import java.util.concurrent.atomic.AtomicInteger;

public class Account {
    private final int id;
    private volatile int money;

    public Account(int id) {
        this(id, 0);
    }

    public Account(int id, int money)
    {
        this.id = id;
        this.money = money;
    }

    public void setMoney(int value)
    {
        money = value;
    }

    public void addMoney(int value)
    {
        money += value;
    }

    public int getMoney()
    {
        return money;
    }

    public int getId()
    {
        return id;
    }
}
