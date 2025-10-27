package com.mycompany.proyectoparrinomunoz.repositorios;

import com.mycompany.proyectoparrinomunoz.Entity.Usuario;
import com.mycompany.proyectoparrinomunoz.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio {

    public Usuario autenticar(String usuario, String contrasenia) {
        String sql = "SELECT * FROM usuario WHERE usuario = ? AND contrasenia = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasenia);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("usuario"),
                        rs.getString("contrasenia"),
                        rs.getString("rol")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error en autenticar(): " + e.getMessage());
        }
        return null;
    }

    public boolean crearUsuario(Usuario u) {
        String sql = "INSERT INTO usuario (usuario, contrasenia, rol) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getContrasenia());
            ps.setString(3, u.getRol());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en crearUsuario(): " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarUsuario(Usuario u) {
        String sql = "UPDATE usuario SET usuario = ?, contrasenia = ?, rol = ? WHERE id_usuario = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getContrasenia());
            ps.setString(3, u.getRol());
            ps.setInt(4, u.getIdUsuario());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en actualizarUsuario(): " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en eliminarUsuario(): " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("usuario"),
                        rs.getString("contrasenia"),
                        rs.getString("rol")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error en listarUsuarios(): " + e.getMessage());
        }
        return lista;
    }
}
