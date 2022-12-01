package activeRecord;

/**
 * Cette classe est levée dans le cas où le réalisateur est absent.
 * ATTENTION :
 * Vous devez créer un réalisateur (=personne) et l'enregistrer avec save() avant toute opération !
 */
public class RealisateurAbsentException extends Exception {

    public RealisateurAbsentException() {
        super("Le film doit avoir un réalisateur existant");
    }
}
