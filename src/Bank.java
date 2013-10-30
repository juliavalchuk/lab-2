import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank {
    private Map<Integer,Account> accounts;


    public Bank()
    {
       // accounts = Collections.synchronizedMap( new Hashtable<Integer, Account>());
        accounts = new Hashtable<Integer, Account>();
    }

    public void addAccount(Account acc)
    {
        accounts.put(acc.getId(), acc);
    }

    public void addAccount(int number, int money)
    {
        accounts.put(number,new Account(number, money));
    }

    public void addAccount(int number)
    {
        addAccount(number, 0);
    }

    public void transactMoney(int accNumFrom, int accNumbTo, int money)
    {
        accounts.get(accNumFrom).addMoney(- money);
        accounts.get(accNumbTo).addMoney(money);
    }

    public void transactMoney(Transaction transaction)
    {
        transactMoney(transaction.FromAccId, transaction.ToAccId, transaction.Money);
    }

    public long accSum()
    {
        long sum = 0;
        for(Account acc : accounts.values())
        {
            sum += acc.getMoney();
        }

        return sum;
    }
}
