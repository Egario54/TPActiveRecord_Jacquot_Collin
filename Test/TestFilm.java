import activeRecord.Film;
import activeRecord.Personne;
import activeRecord.RealisateurAbsentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestFilm {

    Film film;
    Film film2;
    Personne realisateur;
    Personne realisateur2;

    @BeforeEach
    public void before() throws SQLException, RealisateurAbsentException {
        Personne.createTable();
        Film.createTable();

        realisateur = new Personne("Kuenemann","Nicolas");
        realisateur.save();
        film = new Film("Le film",realisateur);
        film.save();
        realisateur2 = new Personne("Jacquot","Thierry");
        realisateur2.save();
        film2 = new Film("Le film 2",realisateur2);
        film2.save();
    }

    @AfterEach
    public void after() throws SQLException {
        Film.deleteTable();
        Personne.deleteTable();
    }

    @Test
    public void test1_findById() throws SQLException, RealisateurAbsentException {
        Film f = Film.findById(2);
        assertEquals("Le film 2",f.getTitre());
    }

    @Test
    public void test2_getRealisateur() throws SQLException, RealisateurAbsentException {
        assertEquals("Kuenemann",film.getRealisateur().getNom());
    }

    @Test
    public void test3_delete() throws SQLException, RealisateurAbsentException {
        film.delete();
        assertEquals(null,Film.findById(1));
    }

    @Test
    public void test4_findByRealisateur() throws SQLException, RealisateurAbsentException {
        assertEquals("Le film",Film.findByRealisateur(realisateur).get(0).getTitre());
    }

    @Test
    public void test5_save() throws SQLException, RealisateurAbsentException {
        Personne realisateur = new Personne("Kuenemann","Niconico");
        realisateur.save();
        Film film = new Film("Le fameux film",realisateur);
        film.save();
        assertEquals("Le fameux film",Film.findById(3).getTitre());
    }

    @Test
    public void update() throws SQLException, RealisateurAbsentException {
        Personne realisateur = new Personne("Kuenemann","Nicolas");
        realisateur.save();
        Film film3 = new Film("Le film pourri",realisateur);
        film3.save();
        film3.setTitre("Le film de merde");
        film3.save();
        assertEquals("Le film de merde",Film.findById(3).getTitre());
    }
}
