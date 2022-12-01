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
        Personne p1 = new Personne("Jacques","Jean");
        Personne p2 = new Personne("Nuit","Jour");
        Personne p3 = new Personne("Collin","Alex");
        Personne p4 = new Personne("Lost","Originality");
        p1.save();
        p2.save();
        p3.save();
        p4.save();
        Film f1 = new Film("Le film",p1);
        Film f2 = new Film("Le film 2",p2);
        Film f3 = new Film("Le film 3",p3);
        Film f4 = new Film("Le film 4",p4);
        f1.save();
        f2.save();
        f3.save();
        f4.save();
    }

    @AfterEach
    public void afterEach() throws SQLException {
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

        assertEquals("Nuit", p.getNom());
    }

    @Test
    public void test3_deleteTable() throws SQLException, RealisateurAbsentException {
        Film.deleteTable();
        assertThrows(RealisateurAbsentException.class, () -> Film.getRealisateur(2));
    }

    @Test
    public void test4_delete() throws SQLException, RealisateurAbsentException {

        assertThrows(RealisateurAbsentException.class, () -> Film.getRealisateur(2));
    }
}
