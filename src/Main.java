import com.sun.jndi.ldap.Connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        String filename = "input.dat";
        Bank bank, bank1, bank2;
        int[] accounts;
        Transaction[] transactions;
        long time;

        RingBufferSolution ringBufferSolution = new RingBufferSolution();
        DisruptorSolution disruptorSolution = new DisruptorSolution();
        AccountFileReader fileReader = new AccountFileReader(filename);
        bank = new Bank();

        fileReader.readFile();
        accounts = fileReader.getAccount();
        transactions = fileReader.getTransactions();

        bank = addAccountsFromAray(bank, accounts);
        System.out.println("Current sum: " + bank.accSum());

        // RingBufferSolution
        time = System.currentTimeMillis();
        bank1 = ringBufferSolution.startTransaction(bank, transactions);
        time = System.currentTimeMillis() - time;
        outResult("RingBufferSolution", bank1.accSum(), time);

        //DisruptorSolution
        time = System.currentTimeMillis();
        bank2 = disruptorSolution.startTransaction(bank, transactions);
        time = System.currentTimeMillis() - time;
        outResult("DisruptorSolution", bank2.accSum(), time);


    }

    public static Bank addAccountsFromAray(Bank bank, int[] accounts)
    {
        int n = accounts.length;
        for(int i = 0; i < n; ++i)
            bank.addAccount(i, accounts[i]);
        return bank;
    }

    public static void outResult(String name, long sum, long time)
    {
        System.out.println();
        System.out.println(name + " sum: " + sum);
        System.out.println(name + " time: " + time);
    }
}

