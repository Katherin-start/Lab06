package config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConexion {
    private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "lab06";

    private static MongoClient client = null;

    public static MongoDatabase getConexion() {
        try {
            if (client == null) {
                client = MongoClients.create(URI);
            }
            MongoDatabase db = client.getDatabase(DB_NAME);
            System.out.println("✅ Conexión a MongoDB exitosa: " + DB_NAME);
            return db;
        } catch (Exception e) {
            System.out.println("❌ Error en la conexión MongoDB: " + e.getMessage());
            return null;
        }
    }

    // Para cerrar conexión cuando ya no se use
    public static void cerrarConexion() {
        if (client != null) {
            client.close();
            System.out.println("🔒 Conexión a MongoDB cerrada");
        }
    }
}
