import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Calls> BC=new ArrayBlockingQueue<>(100);
        ExecutorService executorService= Executors.newFixedThreadPool(2);

        String filePath="D:\\Data\\CDR_5000.txt.Parsed";
        executorService.execute(new ParseFile(BC,filePath));
        executorService.execute(new Loader(BC));

        executorService.shutdown();


    }
}