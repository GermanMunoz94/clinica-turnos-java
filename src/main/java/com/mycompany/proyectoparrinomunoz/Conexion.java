
package com.mycompany.proyectoparrinomunoz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String USUARIO = "root";
    private static final String CONTRASENIA = "root";
    private static final String BD = "bd_medica";
    private static final String IP = "localhost";
    private static final String PUERTO = "3306";

    private static final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + BD
            + "?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires&allowPublicKeyRetrieval=true";

    public Conexion() {
        // Evita instanciación
    }

    public static Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ No se encontró el driver JDBC de MySQL.");
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error desconocido al conectar: " + e.getMessage());
        }
        return null;
    }

    public static void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    // System.out.println("🔌 Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                System.err.println("⚠️ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static boolean probarConexion() {
        try (Connection conn = getConexion()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
