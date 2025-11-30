package Persistencia;


/**
 * Factory Pattern para crear instancias de DAOs
 * Permite cambiar entre PostgreSQL y MongoDB
 * @author flori
 */
public class DAOParticipacionFactory {
    
    // Configuración: cambiar entre "POSTGRESQL" o "MONGODB"
    private static final String TIPO_BD = "MONGODB"; // Cambiar aquí según necesidad
    
    /**
     * Obtiene la implementación de IParticipacionDAO según la configuración
     */
    public static IParticipacionDAO getDAO() {
        switch (TIPO_BD) {
            case "POSTGRESQL":
                System.out.println("✓ Usando PostgreSQL para Participación");
                return new ParticipacionPostgresDAO();
                
            case "MONGODB":
                System.out.println("✓ Usando MongoDB para Participación");
                return new ParticipacionMongoDAO();
                
            default:
                throw new IllegalStateException("Tipo de base de datos no soportado: " + TIPO_BD);
        }
    }
    
    /**
     * Cambia el tipo de base de datos en tiempo de ejecución
     * @param tipoBD "POSTGRESQL" o "MONGODB"
     */
    public static IParticipacionDAO getDAO(String tipoBD) {
        switch (tipoBD.toUpperCase()) {
            case "POSTGRESQL":
                System.out.println("✓ Usando PostgreSQL para Participación");
                return new ParticipacionPostgresDAO();
                
            case "MONGODB":
                System.out.println("✓ Usando MongoDB para Participación");
                return new ParticipacionMongoDAO();
                
            default:
                throw new IllegalArgumentException("Tipo de base de datos no soportado: " + tipoBD);
        }
    }
    
    /**
     * Obtiene el tipo de BD actual
     */
    public static String getTipoBD() {
        return TIPO_BD;
    }
}