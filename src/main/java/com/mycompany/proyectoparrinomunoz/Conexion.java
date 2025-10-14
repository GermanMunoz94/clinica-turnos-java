
package com.mycompany.proyectoparrinomunoz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    Connection conectar = null;
    String usuario = "root";
    String contrasenia = "root";
    String bd = "bd_medica";
    String IP = "localhost";
    String puerto = "3306";
    String cadena = "jdbc:mysql://" + IP + ":" + puerto + "/" + bd;

    public Connection estableConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
            System.out.println("Se conecto correctamente");
        } catch (Exception e) {
            System.out.println("No se conecto a la base de datos, error :  " + e);
        }
        return conectar;

    }
    
    public void getDesconectar() {
        try {
            conectar.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
