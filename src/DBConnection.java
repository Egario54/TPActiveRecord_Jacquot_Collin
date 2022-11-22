import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection con;

    private String usr;
    private String pwd;
    private String svName;

    private String port;
    private String table;


    private static String dbName;


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

        this.con = DriverManager.getConnection(urlDB, connectionProps);
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (con == null){
            new DBConnection();
        }
        return con;
    }

    public static boolean getCon() {
        return con != null;
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

    public static void setNomDB(String dbNom) throws SQLException {
        if (con != null && dbName != dbNom){
            dbName = dbNom;
            con=null;
        }
    }
}
