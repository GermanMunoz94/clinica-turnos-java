package com.mycompany.proyectoparrinomunoz.repositorios;

import com.mycompany.proyectoparrinomunoz.Conexion;
import com.mycompany.proyectoparrinomunoz.Entity.Diagnostico;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoRepositorio {

    private Conexion conexion;

    public DiagnosticoRepositorio() {
        conexion = new Conexion();
    }

    // === CREAR DIAGNOSTICO ===
    public boolean crearDiagnostico(Diagnostico diagnostico) {
        String sql = "INSERT INTO diagnostico (id_turno, fecha, descripcion, receta) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, diagnostico.getTurno().getIdTurno());
            ps.setTimestamp(2, Timestamp.valueOf(diagnostico.getFecha())); // ✅ Conversión segura
            ps.setString(3, diagnostico.getDescripcion());
            ps.setString(4, diagnostico.getReceta());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    diagnostico.setIdDiagnostico(rs.getInt(1));
                }
            }
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // === OBTENER DIAGNOSTICO POR ID ===
    public Diagnostico obtenerPorId(int idDiagnostico) {
        String sql = "SELECT * FROM diagnostico WHERE id_diagnostico = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDiagnostico);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Turno turno = new TurnoRepositorio().obtenerPorId(rs.getInt("id_turno"));
                return new Diagnostico(
                        rs.getInt("id_diagnostico"),
                        turno,
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("descripcion"),
                        rs.getString("receta")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // === OBTENER TODOS LOS DIAGNOSTICOS ===
    public List<Diagnostico> obtenerTodos() {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = "SELECT * FROM diagnostico";
        try (Connection conn = conexion.estableConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Turno turno = new TurnoRepositorio().obtenerPorId(rs.getInt("id_turno"));
                Diagnostico d = new Diagnostico(
                        rs.getInt("id_diagnostico"),
                        turno,
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("descripcion"),
                        rs.getString("receta")
                );
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // === OBTENER DIAGNOSTICOS POR TURNO ===
    public List<Diagnostico> obtenerPorTurno(int idTurno) {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = "SELECT * FROM diagnostico WHERE id_turno = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTurno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Turno turno = new TurnoRepositorio().obtenerPorId(rs.getInt("id_turno"));
                Diagnostico d = new Diagnostico(
                        rs.getInt("id_diagnostico"),
                        turno,
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("descripcion"),
                        rs.getString("receta")
                );
                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // === ACTUALIZAR DIAGNOSTICO ===
    public boolean actualizarDiagnostico(Diagnostico diagnostico) {
        String sql = "UPDATE diagnostico SET descripcion = ?, receta = ? WHERE id_diagnostico = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, diagnostico.getDescripcion());
            ps.setString(2, diagnostico.getReceta());
            ps.setInt(3, diagnostico.getIdDiagnostico());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // === ELIMINAR DIAGNOSTICO ===
    public boolean eliminarDiagnostico(int idDiagnostico) {
        String sql = "DELETE FROM diagnostico WHERE id_diagnostico = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDiagnostico);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // === OBTENER DIAGNOSTICOS POR PACIENTE ===
    public List<Diagnostico> obtenerPorPaciente(int idPaciente) {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = """
        SELECT d.*
        FROM diagnostico d
        JOIN turno t ON d.id_turno = t.id_turno
        WHERE t.id_paciente = ?
        ORDER BY d.fecha DESC
        """;

        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Turno turno = new TurnoRepositorio().obtenerPorId(rs.getInt("id_turno"));
                Diagnostico d = new Diagnostico(
                        rs.getInt("id_diagnostico"),
                        turno,
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("descripcion"),
                        rs.getString("receta")
                );
                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
