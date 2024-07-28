import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        BlockingQueue<Calls> BC = new ArrayBlockingQueue<>(100);
//        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        String filePath = "D:\\Data\\CDR_5000.txt.Parsed";
        executorService.execute(new ParseFile(BC, filePath));
        executorService.execute(new Loader(BC));

        executorService.shutdown();


        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Program terminated in " + duration / 1_000_000 + " milliseconds.");
    }
}
