
package com.mycompany.proyectoparrinomunoz.repositorios;

import com.mycompany.proyectoparrinomunoz.Conexion;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnoRepositorio {

    // === CREAR TURNO ===
    public boolean crearTurno(Turno t) {
        String sql = "INSERT INTO turno (id_paciente, id_medico, fecha, hora) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getPaciente().getIdPaciente());
            ps.setInt(2, t.getMedico().getIdMedico());
            ps.setString(3, t.getFecha());
            ps.setString(4, t.getHora());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en crearTurno(): " + e.getMessage());
            return false;
        }
    }

    // === ACTUALIZAR TURNO ===
    public boolean actualizarTurno(Turno t) {
        String sql = "UPDATE turno SET id_paciente = ?, id_medico = ?, fecha = ?, hora = ? WHERE id_turno = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getPaciente().getIdPaciente());
            ps.setInt(2, t.getMedico().getIdMedico());
            ps.setString(3, t.getFecha());
            ps.setString(4, t.getHora());
            ps.setInt(5, t.getIdTurno());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en actualizarTurno(): " + e.getMessage());
            return false;
        }
    }

    // === ELIMINAR TURNO ===
    public boolean eliminarTurno(int idTurno) {
        String sql = "DELETE FROM turno WHERE id_turno = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTurno);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en eliminarTurno(): " + e.getMessage());
            return false;
        }
    }

    // === LISTAR TODOS LOS TURNOS ===
    public List<Turno> listarTurnos() {
        List<Turno> turnos = new ArrayList<>();
        String sql = """
                SELECT t.id_turno, t.fecha, t.hora,
                       p.id_paciente, p.nombre AS nombre_paciente, p.apellido AS apellido_paciente,
                       m.id_medico, m.nombre AS nombre_medico, m.apellido AS apellido_medico
                FROM turno t
                INNER JOIN paciente p ON t.id_paciente = p.id_paciente
                INNER JOIN medico m ON t.id_medico = m.id_medico
                """;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nombre_paciente"),
                        rs.getString("apellido_paciente"),
                        "",
                        "",
                        ""
                );

                Medico m = new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("nombre_medico"),
                        rs.getString("apellido_medico"),
                        "",
                        ""
                );

                turnos.add(new Turno(
                        rs.getInt("id_turno"),
                        p,
                        m,
                        rs.getString("fecha"),
                        rs.getString("hora")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error en listarTurnos(): " + e.getMessage());
        }

        return turnos;
    }

    // === LISTAR TURNOS POR MÉDICO ===
    public List<Turno> listarTurnosPorMedico(int idMedico) {
        List<Turno> turnos = new ArrayList<>();
        String sql = """
                SELECT t.id_turno, t.fecha, t.hora,
                       p.id_paciente, p.nombre, p.apellido
                FROM turno t
                INNER JOIN paciente p ON t.id_paciente = p.id_paciente
                WHERE t.id_medico = ?
                """;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMedico);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Paciente p = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        "",
                        "",
                        ""
                );

                turnos.add(new Turno(
                        rs.getInt("id_turno"),
                        p,
                        null,
                        rs.getString("fecha"),
                        rs.getString("hora")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error en listarTurnosPorMedico(): " + e.getMessage());
        }

        return turnos;
    }

    // === LISTAR TURNOS POR PACIENTE ===
    public List<Turno> listarTurnosPorPaciente(int idPaciente) {
        List<Turno> turnos = new ArrayList<>();
        String sql = """
                SELECT t.id_turno, t.fecha, t.hora,
                       m.id_medico, m.nombre, m.apellido
                FROM turno t
                INNER JOIN medico m ON t.id_medico = m.id_medico
                WHERE t.id_paciente = ?
                """;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Medico m = new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        "",
                        ""
                );

                turnos.add(new Turno(
                        rs.getInt("id_turno"),
                        null,
                        m,
                        rs.getString("fecha"),
                        rs.getString("hora")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error en listarTurnosPorPaciente(): " + e.getMessage());
        }

        return turnos;
    }
}
        