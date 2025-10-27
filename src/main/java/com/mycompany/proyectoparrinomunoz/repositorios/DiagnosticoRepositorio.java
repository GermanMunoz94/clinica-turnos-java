package com.mycompany.proyectoparrinomunoz.repositorios;

import com.mycompany.proyectoparrinomunoz.Conexion;
import com.mycompany.proyectoparrinomunoz.Entity.Diagnostico;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoRepositorio {

    public DiagnosticoRepositorio() {}

    // === Crear diagnóstico ===
    public boolean crearDiagnostico(Diagnostico d, int idTurno) {
        String sql = "INSERT INTO diagnostico (id_turno, fecha, descripcion, receta) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idTurno);
            ps.setDate(2, Date.valueOf(java.time.LocalDate.now()));
            ps.setString(3, d.getDescripcion());
            ps.setString(4, d.getReceta() != null ? d.getReceta() : "");

            int filas = ps.executeUpdate();
            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) d.setIdDiagnostico(rs.getInt(1));
            }
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error en crearDiagnostico(): " + e.getMessage());
            return false;
        }
    }


    // === Obtener diagnóstico por ID ===
    public Diagnostico obtenerPorId(int idDiagnostico) {
        String sql = """
            SELECT d.id_diagnostico, d.descripcion, d.fecha, d.receta,
                   p.id_paciente, p.nombre AS nombre_paciente, p.apellido AS apellido_paciente,
                   m.id_medico, m.nombre AS nombre_medico, m.apellido AS apellido_medico,
                   m.especialidad, m.telefono AS telefono_medico
            FROM diagnostico d
            JOIN turno t ON d.id_turno = t.id_turno
            JOIN paciente p ON t.id_paciente = p.id_paciente
            JOIN medico m ON t.id_medico = m.id_medico
            WHERE d.id_diagnostico = ?
            """;
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDiagnostico);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nombre_paciente"),
                        rs.getString("apellido_paciente"),
                        null, null, null
                );

                Medico medico = new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("nombre_medico"),
                        rs.getString("apellido_medico"),
                        rs.getString("especialidad"),
                        rs.getString("telefono_medico")
                );

                return new Diagnostico(
                        rs.getInt("id_diagnostico"),
                        paciente,
                        medico,
                        rs.getString("descripcion")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error en obtenerPorId(): " + e.getMessage());
        }
        return null;
    }


    // === Listar diagnósticos ===
    public List<Diagnostico> listarDiagnosticos() {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = """
            SELECT d.id_diagnostico, d.descripcion, d.fecha, d.receta,
                   p.id_paciente, p.nombre AS nombre_paciente, p.apellido AS apellido_paciente,
                   m.id_medico, m.nombre AS nombre_medico, m.apellido AS apellido_medico,
                   m.especialidad, m.telefono AS telefono_medico
            FROM diagnostico d
            JOIN turno t ON d.id_turno = t.id_turno
            JOIN paciente p ON t.id_paciente = p.id_paciente
            JOIN medico m ON t.id_medico = m.id_medico
            ORDER BY d.fecha DESC
            """;

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nombre_paciente"),
                        rs.getString("apellido_paciente"),
                        null, null, null
                );

                Medico medico = new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("nombre_medico"),
                        rs.getString("apellido_medico"),
                        rs.getString("especialidad"),
                        rs.getString("telefono_medico")
                );

                Diagnostico d = new Diagnostico(
                        rs.getInt("id_diagnostico"),
                        paciente,
                        medico,
                        rs.getString("descripcion")
                );
                lista.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Error en listarDiagnosticos(): " + e.getMessage());
        }

        return lista;
    }


    // === Actualizar diagnóstico ===
    public boolean actualizarDiagnostico(Diagnostico d) {
        String sql = "UPDATE diagnostico SET id_paciente = ?, id_medico = ?, descripcion = ? WHERE id_diagnostico = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getPaciente().getIdPaciente());
            ps.setInt(2, d.getMedico().getIdMedico());
            ps.setString(3, d.getDescripcion());
            ps.setInt(4, d.getIdDiagnostico());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en actualizarDiagnostico(): " + e.getMessage());
            return false;
        }
    }

    // === Eliminar diagnóstico ===
    public boolean eliminarDiagnostico(int idDiagnostico) {
        String sql = "DELETE FROM diagnostico WHERE id_diagnostico = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDiagnostico);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en eliminarDiagnostico(): " + e.getMessage());
            return false;
        }
    }
}
