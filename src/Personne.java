import java.sql.*;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    /**
     * Constructeur public avec un identifiant qui indique que la personne n'est pas dans la table
     * @param nomP
     * @param prenomP
     */
    public Personne(String nomP, String prenomP) {
        this.id = -1;
        this.nom = nomP;
        this.prenom = prenomP;
    }

    /**
     * Constructeur privé permettant de créer une nouvelle personne avec son identifiant de table (utilisé par les
     * méthodes find)
     * @param identifiant
     * @param nomP
     * @param prenomP
     */
    private Personne(int identifiant, String nomP, String prenomP){
        this.id = identifiant;
        this.nom = nomP;
        this.prenom = prenomP;
    }

    /**
     * crée la table Personne
     * @throws SQLException
     */
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

    /**
     *
     * @param id
     * @throws SQLException
     */
    public void delete(int id) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement stat = con.prepareStatement("select * from personne WHERE id=?");
        stat.setInt(1, id);
        stat.executeUpdate();
        this.id = -1;
    }

    public void save(){
        if(id==-1)saveNew();
        else update();
    }

    private void saveNew(){

    }

    private void update(){

    }

    //GETTERS pour les tests
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
