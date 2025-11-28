package Persistencia;

public class DAOParticipacionFactory {

    public static IParticipacionDAO getDAO() {
        return new ParticipacionPostgresDAO();
    }
}