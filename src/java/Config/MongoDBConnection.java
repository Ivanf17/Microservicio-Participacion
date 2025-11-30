/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

/**
 *
 * @author flori
 */

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import java.util.Arrays;

/**
 * Conexión a MongoDB para el Microservicio de Participación
 * Patrón Singleton
 * @author flori
 */
public class MongoDBConnection {
    
    private static MongoDBConnection instance;
    private MongoClient mongoClient;
    private MongoDatabase database;
    
    // Configuración de MongoDB
    private static final String MONGO_HOST = "localhost";
    private static final int MONGO_PORT = 27017;
    private static final String MONGO_DATABASE = "ms_participacion";
    
    private MongoDBConnection() {
        try {
            // Configuración del cliente MongoDB
            MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                    builder.hosts(Arrays.asList(
                        new ServerAddress(MONGO_HOST, MONGO_PORT)
                    ))
                )
                .build();
            
            this.mongoClient = MongoClients.create(settings);
            this.database = mongoClient.getDatabase(MONGO_DATABASE);
            
            System.out.println("✓ Conexión MongoDB establecida: " + MONGO_DATABASE);
            
        } catch (Exception e) {
            System.err.println("✗ Error al conectar con MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Obtiene la instancia única de la conexión (Singleton)
     */
    public static synchronized MongoDBConnection getInstance() {
        if (instance == null) {
            instance = new MongoDBConnection();
        }
        return instance;
    }
    
    /**
     * Obtiene la base de datos
     */
    public MongoDatabase getDatabase() {
        return database;
    }
    
    /**
     * Obtiene el cliente MongoDB
     */
    public MongoClient getMongoClient() {
        return mongoClient;
    }
    
    /**
     * Cierra la conexión
     */
    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("✓ Conexión MongoDB cerrada");
        }
    }
    
    /**
     * Verifica si la conexión está activa
     */
    public boolean isConnected() {
        try {
            if (mongoClient != null && database != null) {
                database.listCollectionNames().first();
                return true;
            }
        } catch (Exception e) {
            System.err.println("✗ Conexión perdida: " + e.getMessage());
        }
        return false;
    }
}
