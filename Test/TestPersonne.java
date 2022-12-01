import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestPersonne {
    Personne p1;
    Personne p2;
    Personne p3;
    Personne p4;

    /**
     * Préparation des données et création de la table
     * @throws SQLException comme d'habitude
     */
    @BeforeEach
    public void before() throws SQLException {
        Personne.createTable();
        p1 = new Personne("Jacques","Jean");
        p2 = new Personne("Nuit","Jour");
        p3 = new Personne("Collin","Alex");
        p4 = new Personne("Lost","Originality");
        p1.save();
        p2.save();
        p3.save();
        p4.save();
    }

    /**
     * Test de la méthode qui récupère toutes les personnes dans la base
     */
    @Test
    public void test1_findAllPersons() throws SQLException {
        List<Personne> resultat = Personne.findAll();
        assertEquals(p1.getId(),resultat.get(0).getId());
        assertEquals(p2.getId(),resultat.get(1).getId());
        assertEquals(p3.getId(),resultat.get(2).getId());
        assertEquals(p4.getId(),resultat.get(3).getId());
    }
    /**
     * Test de la méthode qui récupère une seule personne selon son ID
     */
    @Test
    public void test2_findByID() throws SQLException {
        Personne resultat = Personne.findById(3);
        assertEquals(p3.getId(),resultat.getId());
    }

    /**
     * Test de la méthode qui récupère toutes les personnes dans la base ayant le même nom que dans le paramètre
     */
    @Test
    public void test3_findByName() throws SQLException {
        List<Personne> resultat = Personne.findByName("Nuit");
        assertEquals(p2.getId(),resultat.get(0).getId());
    }

    @Test
    public void test4_save() throws SQLException {
        Personne p5 = new Personne("un","truc");
        p5.save();
        assertEquals(p5.getId(), Personne.findById(5).getId());
    }

    @Test
    public void test5_delete() throws SQLException {
        p4.delete();
        assertNull(Personne.findById(4));
    }

    /**
     * Suppression de la table
     * @throws SQLException comme d'hab
     */
    @AfterEach
    public void after() throws SQLException {
        Personne.deleteTable();
    }
}
