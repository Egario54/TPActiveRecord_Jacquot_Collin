import java.sql.*;
import java.util.ArrayList;

public class Film {

    private String titre;
    private int id;
    private int id_real;

    public Film(String titre, Personne realisateur) {
        this.titre = titre;
        this.id = -1;
        this.id_real = realisateur.getId();
    }

    private Film(String titre, int id, int id_real) {
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;
    }

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

    public static Personne getRealisateur() throws SQLException {
        Personne realisateur = null;
        String SQLprep = "SELECT * FROM Personne WHERE id=?;";
        Connection con = DBConnection.getConnection();
        PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
        prep.setInt(1, this.id_real);
        prep.execute();
        ResultSet rs = prep.getResultSet();
        if (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            realisateur = new Personne(this.id_real,nom, prenom);
        }
        return realisateur;
    }

    public static void createTable() throws SQLException {
        String SQLprep = "CREATE TABLE Film (id INTEGER  AUTO_INCREMENT, titre VARCHAR NOT NULL , id_real INTEGER NOT NULL , PRIMARY KEY (ID), FOREIGN KEY (id_real));";
        Connection con = DBConnection.getConnection();
        PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
        prep.execute();
    }

    public static void deleteTable() throws SQLException {
        String SQLprep = "DROP TABLE Film;";
        Connection con = DBConnection.getConnection();
        PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
        prep.execute();
    }

    public void delete() throws SQLException {
        String SQLprep = "DELETE FROM Film WHERE id=?;";
        Connection con = DBConnection.getConnection();
        PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
        prep.setInt(1, id);
        prep.execute();
        this.id = -1;
    }

    public void save() throws SQLException, RealisateurAbsentException {
        Connection con = DBConnection.getConnection();
        if (id_real == -1){
            throw new RealisateurAbsentException();
        }
        if (this.id != -1){
            String SQLprep = "UPDATE Film SET titre=?, id_real=? WHERE id=?;";
            PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, titre);
            prep.setInt(2, id_real);
            prep.setInt(3, id);
            prep.execute();
        } else {
            String SQLprep = "INSERT INTO Film (titre, id_real) VALUES (?,?);";
            PreparedStatement prep = con.prepareStatement(SQLprep, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, titre);
            prep.setInt(2, id_real);
            prep.execute();
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
    }

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


    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }

    public int getIdReal() {
        return id_real;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Film) {
            Film film = (Film) obj;
            return film.getId() == this.id;
        }
        return false;
    }
}
