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
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Connection con = DBConnection.getConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(createString);
    }

    /**
     * supprime la table Personne
     * @throws SQLException
     */
    public static void deleteTable() throws SQLException {
        String drop = "DROP TABLE Personne";
        Connection con = DBConnection.getConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(drop);
    }

    /**
     * Renvoie l'ensemble des personnes trouvées dans la table
     * @return un tableau de personnes contenues dans la table
     * @throws SQLException
     */
    public static Personne[] findAll() throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement stat = (PreparedStatement) con.createStatement();
        ResultSet rs = stat.executeQuery("select * from personne");
        Personne[] res = null;
        if(rs.getFetchSize()!=0){
            res = new Personne[rs.getFetchSize()];
            int i = 0;
            while(rs.next()){
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int id = rs.getInt("id");
                res[i] = new Personne(id,nom,prenom);
                i++;
            }
        }
        return res;
    }

    /**
     * Permet de chercher une seule personne selon son ID
     * @param identifiant
     * @return null ou une personne
     * @throws SQLException
     */
    public static Personne findByID(int identifiant) throws SQLException {
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

    /**
     * Récupère un ou plusieurs personnes ayant le nom trouvé ou null
     * @param name nom du
     * @return un tableau de personnes vide ou contenant des personnes
     * @throws SQLException
     */
    public static Personne[] findByName(String name) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement stat = con.prepareStatement("select * from personne WHERE NOM=?");
        stat.setString(1, name);
        stat.execute();
        ResultSet rs = stat.getResultSet();
        Personne[] res = null;
        if(rs.getFetchSize()!=0){
            res = new Personne[rs.getFetchSize()];
            int i = 0;
            while(rs.next()){
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int id = rs.getInt("id");
                res[i] = new Personne(id,nom,prenom);
                i++;
            }
        }
        return res;
    }

    /**
     * Supprime une personne de la table si son id est différent de 1.
     * @throws SQLException
     */
    public void delete() throws SQLException {
        if(this.id!=1){
            Connection con = DBConnection.getConnection();
            PreparedStatement stat = con.prepareStatement("DELETE FROM Personne WHERE id=?");
            stat.setInt(1, this.id);
            stat.executeUpdate();
            this.id = -1;
        }
    }

    /**
     * ajoute ou update la personne selon le cas
     * @throws SQLException
     */
    public void save() throws SQLException {
        if(id==-1)saveNew();
        else update();
    }

    /**
     * Permet d'enregistrer la personne dans la base
     * L'attribut id est update par après
     * @throws SQLException
     */
    private void saveNew() throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement stat = con.prepareStatement("insert into personne values(?,?)",Statement.RETURN_GENERATED_KEYS);
        stat.setString(1, this.nom);
        stat.setString(2, this.prenom);
        stat.executeUpdate();
        ResultSet rs = stat.getGeneratedKeys();
        if(rs.next()){
            this.id = rs.getInt("id");
        }
    }

    /**
     * Simple update des attributs de Personne dans la base
     * @throws SQLException
     */
    private void update() throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement stat = con.prepareStatement("update personne set nom=?, prenom=? where id=?",Statement.RETURN_GENERATED_KEYS);
        stat.setString(1, this.nom);
        stat.setString(2, this.prenom);
        stat.setInt(2, this.id);
        stat.executeUpdate();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Personne)) return false;
        Personne p = (Personne) obj;
        return this.id==p.getId()&&this.nom==p.getNom()&&this.prenom==p.getPrenom();
    }

    //GETTERS pour les tests + equals
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
