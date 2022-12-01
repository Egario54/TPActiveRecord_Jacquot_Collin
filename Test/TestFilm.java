import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestFilm {

    @BeforeEach
    public void beforeEach() throws SQLException, RealisateurAbsentException {
        Personne.createTable();
        Film.createTable();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        Film.deleteTable();
        Personne.deleteTable();
    }

    @Test
    public void test1_findById() throws SQLException, RealisateurAbsentException {
        Personne realisateur = new Personne("Kuenemann","Nicolas");
        Film film = new Film("Le film",realisateur);
        realisateur.save();
        film.save();
        Personne realisateur2 = new Personne("Jacquot","Thierry");
        Film film2 = new Film("Le film2",realisateur2);
        realisateur2.save();
        film2.save();
        Film f = Film.findById(2);
        assertEquals("Le film 2",f.getTitre());
    }

    @Test
    public void test2_getRealisateur() throws SQLException, RealisateurAbsentException {
        Personne realisateur = new Personne("Kuenemann","Nicolas");
        Film film = new Film("Le film",realisateur);
        realisateur.save();
        film.save();
        Personne realisateur2 = new Personne("Jacquot","Thierry");
        Film film2 = new Film("Le film2",realisateur2);
        realisateur2.save();
        film2.save();
        assertEquals("Kuenemann",film.getRealisateur().getNom());
    }

    @Test
    public void test3_delete() throws SQLException, RealisateurAbsentException {
        Personne realisateur = new Personne("Kuenemann","Nicolas");
        Film film = new Film("Le film",realisateur);
        realisateur.save();
        film.save();
        Personne realisateur2 = new Personne("Jacquot","Thierry");
        Film film2 = new Film("Le film2",realisateur2);
        realisateur2.save();
        film2.save();
        film.delete();
        assertEquals(null,Film.findById(1));
    }

    @Test
    public void test4_findByRealisateur() throws SQLException, RealisateurAbsentException {
        Personne realisateur = new Personne("Kuenemann","Nicolas");
        Film film = new Film("Le film",realisateur);
        realisateur.save();
        film.save();
        Personne realisateur2 = new Personne("Jacquot","Thierry");
        Film film2 = new Film("Le film2",realisateur2);
        realisateur2.save();
        film2.save();
        assertEquals("Le film",Film.findByRealisateur(realisateur).get(0).getTitre());
    }

    @Test
    public void test5_save() throws SQLException, RealisateurAbsentException {
        Personne realisateur = new Personne("Kuenemann","Nicolas");
        Film film = new Film("Le film",realisateur);
        realisateur.save();
        film.save();
        assertEquals("Le film",Film.findById(1).getTitre());
    }

    @Test
    public void update() throws SQLException, RealisateurAbsentException {
        Personne realisateur = new Personne("Kuenemann","Nicolas");
        Film film = new Film("Le film",realisateur);
        realisateur.save();
        film.save();
        film.setTitre("Le film de merde");
        film.save();
        assertEquals("Le film de merde",Film.findById(1).getTitre());
    }
}
