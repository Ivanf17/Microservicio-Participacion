package Config;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQLConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/ms_participacion";
    private static final String USER = "postgres"; 
    private static final String PASS = "123456"; 

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("Error en conexión: " + e.getMessage());
            return null;
        }
    }
}


