import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class FileGenerator {

    public static String generateBinaryAccountsFile(int accountNumb, String filename) throws IOException {

        DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename));
        Random rand = new Random();

        dos.writeInt(accountNumb);

        for (int i = 0; i < accountNumb; i++)
            dos.writeInt(rand.nextInt(1000));

        int transactionNumber = rand.nextInt(1000);
        dos.writeInt(transactionNumber);

        for (int i = 0; i < transactionNumber; i++)
        {
            dos.writeInt(rand.nextInt(accountNumb));
            dos.writeInt(rand.nextInt(accountNumb));
            dos.writeInt(rand.nextInt(20) + 1);
        }

        dos.close();

        return filename;
    }
}
