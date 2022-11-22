import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Personne[] resultat = Personne.findAll();
        assertEquals(p1,resultat[0]);
        assertEquals(p2,resultat[1]);
        assertEquals(p3,resultat[2]);
        assertEquals(p4,resultat[3]);
    }
    /**
     * Test de la méthode qui récupère une seule personne selon son ID
     */
    @Test
    public void test2_findByID() throws SQLException {
        Personne resultat = Personne.findByID(2);
        assertEquals(p3,resultat);
    }

    /**
     * Test de la méthode qui récupère toutes les personnes dans la base ayant le même nom que dans le paramètre
     */
    @Test
    public void test3_findByName() throws SQLException {
        Personne[] resultat = Personne.findByName("Nuit");
        assertEquals(p2,resultat[0]);
    }

    @Test
    public void test4_save(){

    }

    @Test
    public void test5_delete(){

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
