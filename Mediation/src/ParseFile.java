import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ParseFile implements Runnable {

    private final BlockingQueue<Calls> calls;
    private final String fPath;

    public ParseFile(BlockingQueue<Calls> calls, String fPath) {
        this.calls = calls;
        this.fPath = fPath;
    }


    @Override
    public void run() {

        try (BufferedReader bufferedReader=new BufferedReader(new FileReader(fPath))){

            String line;
            while ((line =bufferedReader.readLine()) != null){
                String[] tokens = line.split(",");
                if (tokens.length==3){
                    String source=tokens[0];
                    String destination=tokens[1];
                    int duration=Integer.parseInt(tokens[2]);
                    Calls call = new Calls(source, destination, duration);
//                    System.out.println("Parsed"+call);
                    calls.put(call);
                }
            }
            calls.put(new Calls("e","e",0));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
