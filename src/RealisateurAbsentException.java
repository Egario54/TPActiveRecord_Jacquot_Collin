public class RealisateurAbsentException extends Exception {
    public RealisateurAbsentException() {
        super("Le film doit avoir un réalisateur existant");
    }
}
