package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Film {

    // Fields
    //Attribut titre de type String
    private String titre;
    //Attribut id de type int
    private int id;
    // attribut id_real de type int
    private int id_real;

    //Constructeur public de la classe Film
    public Film(String titre, Personne realisateur) {
        this.titre = titre;
        this.id = -1;
        this.id_real = realisateur.getId();
    }

    //Constructeur private de la classe Film
    private Film(String titre, int id, int id_real) {
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;
    }

    /**
     * Méthode qui permet de récupérer un film à partir de son id
     * @param id
     * @return un objet de type Film
     * @throws SQLException
     */
    public static Film findById(int id) throws SQLException {
        Film film = null;
        String SQLprep = "SELECT * FROM Film WHERE id=?;";
        Connection con = DBConnection.getConnection();
        PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
        prep.setInt(1, id);
        prep.execute();
        ResultSet rs = prep.getResultSet();
        if (rs.next()) {
            String titre = rs.getString("titre");
            int id_real = rs.getInt("id_real");
            film = new Film(titre, id, id_real);
        }
        return film;
    }

    /**
     * Méthode qui permet de récupérer le réalisateur d'un film
     * @return un objet de type Personne
     * @throws SQLException
     */
    public Personne getRealisateur() throws SQLException {
        return Personne.findById(this.id_real);
    }

    /**
     * Méthode static qui permet de créer la table Film dans la base de données
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String SQLprep = "CREATE TABLE Film ( " + "ID INTEGER  AUTO_INCREMENT, " + "titre varchar(40) NOT NULL, "
                + "id_real INTEGER NOT NULL, " + "PRIMARY KEY (ID), " + "FOREIGN KEY (id_real) REFERENCES Personne(ID))";
        Connection con = DBConnection.getConnection();
        PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
        prep.execute();
    }

    /**
     * Méthode static qui permet de supprimer la table Film de la base de données
     * @throws SQLException
     */
    public static void deleteTable() throws SQLException {
        String SQLprep = "DROP TABLE Film;";
        Connection con = DBConnection.getConnection();
        PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
        prep.executeUpdate();
    }

    /**
     * Méthode qui permet de supprimer un film dans la base de données
     * @throws SQLException
     */
    public void delete() throws SQLException {
        String SQLprep = "DELETE FROM Film WHERE id=?;";
        Connection con = DBConnection.getConnection();
        PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
        prep.setInt(1, id);
        prep.executeUpdate();
        this.id = -1;
    }

    /**
     * Ajoute un film à la base de données
     * @throws SQLException
     * @throws RealisateurAbsentException dans 2 cas : le réalisateur n'a simplement pas été créé
     * inexistant ou le réalisateur n'a pas été save avant la création du film.
     */
    public void save() throws SQLException, RealisateurAbsentException {
        Connection con = DBConnection.getConnection();
        if (id_real == -1){
            throw new RealisateurAbsentException();
        }
        if (this.id != -1){
            update();
        } else {
            String SQLprep = "INSERT INTO Film (titre, id_real) VALUES (?,?);";
            PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, titre);
            prep.setInt(2, id_real);
            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
    }

    /**
     * Méthode static qui permet de retourner tous les films d'un réalisateur passé en paramètre
     * @param realisateur de type Personne
     * @return une ArrayList<Film> qui contient tous les films d'un réalisateur
     * @throws SQLException
     */
    public static ArrayList<Film> findByRealisateur(Personne realisateur) throws SQLException {
        ArrayList<Film> films = new ArrayList<Film>();
        String SQLprep = "SELECT * FROM Film WHERE id_real=?;";
        Connection con = DBConnection.getConnection();
        PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
        prep.setInt(1, realisateur.getId());
        prep.execute();
        ResultSet rs = prep.getResultSet();
        while (rs.next()) {
            String titre = rs.getString("titre");
            int id = rs.getInt("id");
            int id_real = rs.getInt("id_real");
            Film film = new Film(titre, id, id_real);
            films.add(film);
        }
        return films;
    }

    /**
     * Méthode qui permet de mettre à jour un film dans la base de données
     * @throws SQLException
     */
    private void update() throws SQLException {
        String SQLprep = "UPDATE Film SET titre = ?, id_real = ? WHERE id = ? ;";
        Connection con = DBConnection.getConnection();
        PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, titre);
        prep.setInt(2, id_real);
        prep.setInt(3, id);
        prep.executeUpdate();
    }


    /**
     * Méthode qui permet de retourner le titre d'un film
     * @return un objet de type String qui contient le titre du film
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Méthode de retourner l'id d'un film
     * @return un objet de type int qui contient l'id du film
     */
    public int getId() {
        return id;
    }

    /**
     * Méthode qui permet de retourner l'id du réalisateur d'un film
     * @return un objet de type int qui contient l'id du réalisateur du film
     */
    public int getIdReal() {
        return id_real;
    }

    /**
     * Méthode qui permet de modifier le titre d'un film
     * @param titre de type String qui contient le nouveau titre du film
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Méthode qui permet de modifier l'id du réalisateur d'un film
     * @param id_real de type int qui contient le nouvel id du réalisateur du film
     */
    public void setIdReal(int id_real) {
        this.id_real = id_real;
    }

    /**
     * Méthode qui permet de retourner les données d'un film sous forme de String
     * @return un objet de type String correspondant au film
     */
    public String toString() {
        return "ActiveRecord.Film [id=" + id + ", titre=" + titre + ", id_real=" + id_real + "]";
    }

    /**
     * Méthode qui permet de comparer 2 films
     * @param obj
     * @return un objet de type boolean qui est vrai si les 2 films sont identiques
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Film) {
            Film film = (Film) obj;
            return film.getId() == this.id;
        }
        return false;
    }
}
