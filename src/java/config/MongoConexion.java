package config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConexion {
    private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "lab06";

    public static MongoDatabase getConexion() {
        try {
            MongoClient client = MongoClients.create(URI);
            MongoDatabase db = client.getDatabase(DB_NAME);
            System.out.println("✅ Conexión a MongoDB exitosa");
            return db;
        } catch (Exception e) {
            System.out.println("❌ Error en la conexión MongoDB: " + e.getMessage());
            return null;
        }
    }
}
