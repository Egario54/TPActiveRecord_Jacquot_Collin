import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;
    private Connection con;

    private String usr;
    private String pwd;
    private String svName;

    private String port;
    private String table;


    private String dbName;



    private DBConnection() throws SQLException {
        // variables a modifier en fonction de la base
        this.usr = "root";
        this.pwd = "";
        this.svName = "localhost";
        //Attention, sous MAMP, le port est 8889
        this.port = "3306";
        this.table = "personne";

        // iL faut une base nommee testPersonne !
        this.dbName = "testpersonne";

        // creation de la connection
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.usr);
        connectionProps.put("password", this.pwd);
        String urlDB = "jdbc:mysql://" + this.svName + ":";
        urlDB += this.port + "/" + this.dbName;

        Connection connect = DriverManager.getConnection(urlDB, connectionProps);
        this.con = connect;
    }

    public static synchronized DBConnection getInstance() throws SQLException {
        if(instance==null) instance = new DBConnection();
        return instance;
    }
}
