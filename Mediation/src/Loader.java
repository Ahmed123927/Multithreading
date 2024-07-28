import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

public class Loader implements Runnable{
    private final BlockingQueue<Calls> calls;

    public Loader(BlockingQueue<Calls> calls) {
        this.calls = calls;
    }


    @Override
    public void run() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/Mediation", "postgres", "root")){
            while (true){
                Calls call=calls.take();
                if(call.getDestination().equals("e")){
                    break;
                }
//                System.out.println("inserted" + call);
                insertData(con,call);
            }
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertData(Connection con, Calls call) throws SQLException {
        String query = "INSERT INTO calls (source, destination, duration) VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement=con.prepareStatement(query)){
            preparedStatement.setString(1,call.getSource());
            preparedStatement.setString(2,call.getDestination());
            preparedStatement.setInt(3,call.getDuration());
            preparedStatement.executeUpdate();
        }
        catch (Exception e){
            con.close();
        }
    }
}
