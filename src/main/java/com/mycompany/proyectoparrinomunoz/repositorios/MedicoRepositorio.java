package com.mycompany.proyectoparrinomunoz.repositorios;
import com.mycompany.proyectoparrinomunoz.Conexion;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepositorio {

    private Conexion conexion;

    public MedicoRepositorio() {
        conexion = new Conexion();
    }

    // CREAR MÉDICO
    public boolean crearMedico(Medico medico) {
        String sql = "INSERT INTO medico (nombre, apellido, especialidad, matricula) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getApellido());
            ps.setString(3, medico.getEspecialidad());
            ps.setString(4, medico.getMatricula());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    medico.setIdMedico(rs.getInt(1));
                }
            }

            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // OBTENER MÉDICO POR ID
    public Medico obtenerPorId(int idMedico) {
        String sql = "SELECT * FROM medico WHERE id_medico = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMedico);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("especialidad"),
                        rs.getString("matricula")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // OBTENER TODOS LOS MÉDICOS
    public List<Medico> obtenerTodos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medico";

        try (Connection conn = conexion.estableConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medico medico = new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("especialidad"),
                        rs.getString("matricula")
                );
                medicos.add(medico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }

    // ACTUALIZAR MÉDICO
    public boolean actualizarMedico(Medico medico) {
        String sql = "UPDATE medico SET nombre = ?, apellido = ?, especialidad = ?, matricula = ? WHERE id_medico = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getApellido());
            ps.setString(3, medico.getEspecialidad());
            ps.setString(4, medico.getMatricula());
            ps.setInt(5, medico.getIdMedico());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR MÉDICO
    public boolean eliminarMedico(int idMedico) {
        String sql = "DELETE FROM medico WHERE id_medico = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMedico);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // FUNCIONES ADICIONALES
    // Buscar médicos por especialidad
    public List<Medico> buscarPorEspecialidad(String especialidad) {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medico WHERE especialidad = ?";

        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, especialidad);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Medico medico = new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("especialidad"),
                        rs.getString("matricula")
                );
                medicos.add(medico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }
}

