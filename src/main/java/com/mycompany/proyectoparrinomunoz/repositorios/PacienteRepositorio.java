package com.mycompany.proyectoparrinomunoz.repositorios;

import com.mycompany.proyectoparrinomunoz.Conexion;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepositorio {

    // === CREAR PACIENTE ===
    public boolean crearPaciente(Paciente paciente) {
        String sql = "INSERT INTO paciente (nombre, apellido, dni, telefono, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setString(3, paciente.getDni());
            ps.setString(4, paciente.getTelefono());
            ps.setString(5, paciente.getEmail());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        paciente.setIdPaciente(rs.getInt(1));
                    }
                }
            }

            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en crearPaciente(): " + e.getMessage());
            return false;
        }
    }

    // === OBTENER PACIENTE POR ID ===
    public Paciente obtenerPorId(int idPaciente) {
        String sql = "SELECT id_paciente, nombre, apellido, dni, telefono, email FROM paciente WHERE id_paciente = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Paciente(
                            rs.getInt("id_paciente"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("dni"),
                            rs.getString("telefono"),
                            rs.getString("email")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en obtenerPorId(): " + e.getMessage());
        }
        return null;
    }

    // === OBTENER TODOS LOS PACIENTES ===
    public List<Paciente> obtenerTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT id_paciente, nombre, apellido, dni, telefono, email FROM paciente";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pacientes.add(new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error en obtenerTodos(): " + e.getMessage());
        }
        return pacientes;
    }

    // === ACTUALIZAR PACIENTE ===
    public boolean actualizarPaciente(Paciente paciente) {
        String sql = "UPDATE paciente SET nombre = ?, apellido = ?, dni = ?, telefono = ?, email = ? WHERE id_paciente = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setString(3, paciente.getDni());
            ps.setString(4, paciente.getTelefono());
            ps.setString(5, paciente.getEmail());
            ps.setInt(6, paciente.getIdPaciente());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en actualizarPaciente(): " + e.getMessage());
            return false;
        }
    }

    // === ELIMINAR PACIENTE ===
    public boolean eliminarPaciente(int idPaciente) {
        String sqlUsuario = "DELETE FROM usuario WHERE id_relacionado = ?";
        String sqlPaciente = "DELETE FROM paciente WHERE id_paciente = ?";

        try (Connection conn = Conexion.getConexion()) {
            conn.setAutoCommit(false); // inicia transacción

            try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario);
                 PreparedStatement psPaciente = conn.prepareStatement(sqlPaciente)) {

                // Eliminar usuario vinculado
                psUsuario.setInt(1, idPaciente);
                psUsuario.executeUpdate();

                // Eliminar paciente
                psPaciente.setInt(1, idPaciente);
                psPaciente.executeUpdate();

                conn.commit(); // confirma transacción
                return true;

            } catch (SQLException e) {
                conn.rollback(); // revierte si falla algo
                System.err.println("Error en eliminarPaciente(): " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error en conexión eliminarPaciente(): " + e.getMessage());
            return false;
        }
    }


    // === BUSCAR POR DNI ===
    public Paciente buscarPorDni(String dni) {
        String sql = "SELECT id_paciente, nombre, apellido, dni, telefono, email FROM paciente WHERE dni = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Paciente(
                            rs.getInt("id_paciente"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("dni"),
                            rs.getString("telefono"),
                            rs.getString("email")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en buscarPorDni(): " + e.getMessage());
        }
        return null;
    }
}
