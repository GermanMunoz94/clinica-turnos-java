package com.mycompany.proyectoparrinomunoz.repositorios;

import com.mycompany.proyectoparrinomunoz.Conexion;
import com.mycompany.proyectoparrinomunoz.Entity.Usuario;

import java.sql.*;

public class UsuarioRepositorio {

    private final Conexion conexion;

    public UsuarioRepositorio() {
        this.conexion = new Conexion();
    }

    // 🔐 Autenticación de usuario
    public Usuario autenticar(String email, String password) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("rol"),
                        rs.getInt("id_relacionado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔍 Buscar por ID
    public Usuario obtenerPorId(int idUsuario) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("rol"),
                        rs.getInt("id_relacionado")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ➕ Crear usuario (por ejemplo al registrar un paciente)
    public boolean crearUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (email, password, rol, id_relacionado) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRol());
            ps.setInt(4, usuario.getIdRelacionado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
