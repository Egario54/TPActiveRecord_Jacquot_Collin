import java.sql.*;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne(String nomP, String prenomP) {
        this.id = -1;
        this.nom = nomP;
        this.prenom = prenomP;
    }

    public Personne(int identifiant, String nomP, String prenomP){
        this.id = identifiant;
        this.nom = nomP;
        this.prenom = prenomP;
    }

    public static void createTable() throws SQLException {
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Connection con = DBConnection.getConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        String drop = "DROP TABLE Personne";
        Connection con = DBConnection.getConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(drop);
    }

    public Personne[] findAll() throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement stat = (PreparedStatement) con.createStatement();
        ResultSet rs = stat.executeQuery("select * from personne");
        Personne[] res = new Personne[rs.getFetchSize()];
        int i = 0;
        while(rs.next()){
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            res[i]= new Personne(id,nom,prenom);
            i++;
        }
        return res;
    }

    public Personne findByID(int identifiant) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement stat = con.prepareStatement("select * from personne WHERE id=?");
        stat.setInt(1, identifiant);
        stat.execute();
        ResultSet rs = stat.getResultSet();
        Personne res = null;
        if(rs.next()){
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            res = new Personne(id,nom,prenom);
        }
        return res;
    }

    public Personne[] findByName(String name) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement stat = con.prepareStatement("select * from personne WHERE NOM=?");
        stat.setString(1, name);
        stat.execute();
        ResultSet rs = stat.getResultSet();
        Personne[] res = new Personne[rs.getFetchSize()];
        int i = 0;
        while(rs.next()){
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            res[i] = new Personne(id,nom,prenom);
            i++;
        }
        return res;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
}
