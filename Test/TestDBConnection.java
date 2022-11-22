import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDBConnection {


    // test les méthodes de la classe DBConnection
    @Test
    public void testDBConnection() throws SQLException {
        DBConnection.getConnection();
        assertEquals(DBConnection.getCon(), true);
    }

    // test qu'il y est bien qu'un seul objet de la classe DBConnection
    @Test
    public void testDBConnection_uniqueObjet() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();
        assertEquals(c1, c2);
    }

    // test que l'objet retourner par la méthode getConnection() est bien de type java.sql.Connection
    @Test
    public void testDBConnection_type() throws SQLException {
        assertEquals(DBConnection.getConnection() instanceof Connection, true);
    }

    // test quand on change le nom de la base de donnée
    @Test
    public void testDBConnection_changeDBName() throws SQLException {
        DBConnection.setNomDB("testpersonne");
        Connection c1 = DBConnection.getConnection();
        DBConnection.setNomDB("test");
        Connection c2 = DBConnection.getConnection();
        // vérifie que les deux objets sont différents
        assert !c1.equals(c2);


    }
}

