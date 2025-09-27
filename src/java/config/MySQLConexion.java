package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConexion {
    private static final String URL = "jdbc:mysql://localhost:3306/lab06";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión a MySQL exitosa");
        } catch (Exception e) {
            System.out.println("❌ Error en la conexión MySQL: " + e.getMessage());
        }
        return con;
    }
}
