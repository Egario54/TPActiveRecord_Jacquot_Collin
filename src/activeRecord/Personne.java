package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Personne {
    // Attributs
    // Attribut id de la personne de type int
    private int id;
    // Attribut nom de la personne de type String
    private String nom;
    // Attribut prenom de la personne de type String
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
    public Personne(int identifiant, String nomP, String prenomP){
        this.id = identifiant;
        this.nom = nomP;
        this.prenom = prenomP;
    }

    /**
     * crée la table Personne
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String createString = "CREATE TABLE IF NOT EXISTS `personne` (\n" +
                "  `ID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `NOM` varchar(40) NOT NULL,\n" +
                "  `PRENOM` varchar(40) NOT NULL,\n" +
                "  PRIMARY KEY (`ID`)\n" +
                ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";
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
    public static ArrayList<Personne> findAll() throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement stat = con.prepareStatement("select * from personne");
        stat.execute();
        ResultSet rs = stat.getResultSet();
        ArrayList<Personne> res= new ArrayList<>();
        while(rs.next()){
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            res.add(new Personne(id,nom,prenom));
        }
        return res;
    }

    /**
     * Permet de chercher une seule personne selon son ID
     * @param identifiant
     * @return null ou une personne
     * @throws SQLException
     */
    public static Personne findById(int identifiant) throws SQLException {
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
     * @param name nom du spaghetti
     * @return un tableau de personnes vide ou contenant des personnes
     * @throws SQLException
     */
    public static ArrayList<Personne> findByName(String name) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement stat = con.prepareStatement("select * from personne WHERE NOM=?");
        stat.setString(1, name);
        stat.execute();
        ResultSet rs = stat.getResultSet();
        ArrayList<Personne> res= new ArrayList<>();
        while(rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            res.add(new Personne(id, nom, prenom));
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
        PreparedStatement stat = con.prepareStatement("insert into personne (NOM,PRENOM) values(?,?)",Statement.RETURN_GENERATED_KEYS);
        stat.setString(1, this.nom);
        stat.setString(2, this.prenom);
        stat.executeUpdate();
        ResultSet rs = stat.getGeneratedKeys();
        if(rs.next()){
            this.id = rs.getInt(1);
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
