package com.mycompany.proyectoparrinomunoz.repositorios;

import com.mycompany.proyectoparrinomunoz.Conexion;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepositorio {

    // === CREAR MÉDICO ===
    public boolean crearMedico(Medico medico) {
        String sql = "INSERT INTO medico (nombre, apellido, especialidad, telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getApellido());
            ps.setString(3, medico.getEspecialidad());
            ps.setString(4, medico.getTelefono());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) medico.setIdMedico(rs.getInt(1));
                }
            }

            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en crearMedico(): " + e.getMessage());
            return false;
        }
    }

    // === OBTENER MÉDICO POR ID ===
    public Medico obtenerPorId(int idMedico) {
        String sql = "SELECT id_medico, nombre, apellido, especialidad, telefono FROM medico WHERE id_medico = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMedico);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Medico(
                            rs.getInt("id_medico"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("especialidad"),
                            rs.getString("telefono")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en obtenerPorId(): " + e.getMessage());
        }
        return null;
    }

    // === OBTENER TODOS LOS MÉDICOS ===
    public List<Medico> obtenerTodos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT id_medico, nombre, apellido, especialidad, telefono FROM medico";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medicos.add(new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("especialidad"),
                        rs.getString("telefono")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error en obtenerTodos(): " + e.getMessage());
        }

        return medicos;
    }

    // === ACTUALIZAR MÉDICO ===
    public boolean actualizarMedico(Medico medico) {
        String sql = "UPDATE medico SET nombre = ?, apellido = ?, especialidad = ?, telefono = ? WHERE id_medico = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getApellido());
            ps.setString(3, medico.getEspecialidad());
            ps.setString(4, medico.getTelefono());
            ps.setInt(5, medico.getIdMedico());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en actualizarMedico(): " + e.getMessage());
            return false;
        }
    }

    // === ELIMINAR MÉDICO ===
    public boolean eliminarMedico(int idMedico) {
        String sql = "DELETE FROM medico WHERE id_medico = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMedico);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en eliminarMedico(): " + e.getMessage());
            return false;
        }
    }

    // === BUSCAR POR ESPECIALIDAD ===
    public List<Medico> buscarPorEspecialidad(String especialidad) {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT id_medico, nombre, apellido, especialidad, telefono FROM medico WHERE especialidad LIKE ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + especialidad + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    medicos.add(new Medico(
                            rs.getInt("id_medico"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("especialidad"),
                            rs.getString("telefono")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en buscarPorEspecialidad(): " + e.getMessage());
        }

        return medicos;
    }
}
