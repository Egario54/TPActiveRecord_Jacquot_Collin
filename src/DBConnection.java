import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection instance;
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

    public static synchronized Connection getInstance() throws SQLException {
        if (instance == null) instance = new DBConnection().con;
        return instance;
    }

    public Connection getConnection() {
        return this.con;
    }

    public String getTable() {
        return this.table;
    }

    public String getDBName() {
        return this.dbName;
    }

    public String getUsr() {
        return this.usr;
    }

    public String getPwd() {
        return this.pwd;
    }

    public String getServerName() {
        return this.svName;
    }

    public String getPort() {
        return this.port;
    }

    public void close() throws SQLException {
        this.con.close();
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setSvName(String svName) {
        this.svName = svName;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setDbName(String dbName) throws SQLException {
        this.dbName = dbName;
        instance = null;
        getInstance();
    }
}
