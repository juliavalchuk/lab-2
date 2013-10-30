import java.io.*;

public class AccountFileReader {

    private String filename;
    private int[] accounts;
    private Transaction[] transactions;

    public AccountFileReader(String filename) throws FileNotFoundException {
        if(isFileExist(filename))
            this.filename = filename;
        else
            throw new FileNotFoundException();
    }

    public AccountFileReader() throws FileNotFoundException {
        this("input.dat");
    }

    private boolean isFileExist(String filename)
    {
        File file = new File(filename);
        return file.exists();
    }

    public void readFile(String filename) throws IOException {

        DataInputStream dis = new DataInputStream(new FileInputStream(filename));
            int accountNumb;
            int transactionNumb;

            accountNumb = dis.readInt();
            accounts = new int[accountNumb];
            for (int i = 0; i < accountNumb; ++i)
                accounts[i] = dis.readInt();

            transactionNumb = dis.readInt();
            transactions = new Transaction[transactionNumb];
            for (int i = 0; i < transactionNumb; ++i)
            {
                transactions[i] = new Transaction();
                transactions[i].FromAccId = dis.readInt();
                transactions[i].ToAccId = dis.readInt();
                transactions[i].Money = dis.readInt();
            }

    }

    public void readFile() throws IOException {
        readFile(filename);
    }

    public int[] getAccount()
    {
        return accounts;
    }

    public Transaction[] getTransactions()
    {
        return transactions;
    }

}
