
package com.mycompany.proyectoparrinomunoz.repositorios;

import java.sql.*;
import com.mycompany.proyectoparrinomunoz.Conexion;
import com.mycompany.proyectoparrinomunoz.Entity.Medico;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import com.mycompany.proyectoparrinomunoz.Entity.Turno;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.proyectoparrinomunoz.utils.EmailService;


public class TurnoRepositorio {

    private Conexion conexion;

    public TurnoRepositorio() {
        conexion = new Conexion();
    }

    // CREAR UN TURNO
    public boolean crearTurno(Turno turno) {
        String sql = "INSERT INTO turno (fecha, hora, id_paciente, id_medico) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, turno.getFecha());
            ps.setString(2, turno.getHora());
            ps.setInt(3, turno.getPaciente().getIdPaciente());
            ps.setInt(4, turno.getMedico().getIdMedico());

            int filas = ps.executeUpdate();

            // Obtener id generado
            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    turno.setIdTurno(rs.getInt(1));
                }
            }

            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean enviarNotificacionTurno(Turno turno) {
        Paciente paciente = new PacienteRepositorio().obtenerPorId(turno.getPaciente().getIdPaciente());
        if (paciente == null || paciente.getEmail() == null || paciente.getEmail().isEmpty()) {
            System.out.println("⚠️ Paciente no encontrado o sin email.");
            return false;
        }

        Medico medico = new MedicoRepositorio().obtenerPorId(turno.getMedico().getIdMedico());

        String asunto = "Confirmación de turno médico";
        String contenido = """
        <h2>Confirmación de Turno Médico</h2>
        <p>Estimado/a <b>%s %s</b>,</p>
        <p>Le confirmamos su turno con el Dr./Dra. <b>%s %s</b> el día <b>%s</b> a las <b>%s</b>.</p>
        <p>Gracias por confiar en nosotros.</p>
        <br>
        <p><i>Este es un mensaje automático, por favor no responda.</i></p>
        """.formatted(
                paciente.getNombre(),
                paciente.getApellido(),
                medico.getNombre(),
                medico.getApellido(),
                turno.getFecha(),
                turno.getHora()
        );

        EmailService emailService = new EmailService();
        return emailService.enviarCorreo(paciente.getEmail(), asunto, contenido);
    }


    // OBTENER UN TURNO POR ID
    public Turno obtenerPorId(int idTurno) {
        String sql = "SELECT * FROM turno WHERE id_turno = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTurno);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Paciente paciente = new PacienteRepositorio().obtenerPorId(rs.getInt("id_paciente"));
                Medico medico = new MedicoRepositorio().obtenerPorId(rs.getInt("id_medico"));
                return new Turno(
                        rs.getInt("id_turno"),
                        paciente,
                        medico,
                        rs.getString("fecha"),
                        rs.getString("hora")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // OBTENER TODOS LOS TURNOS
    public List<Turno> obtenerTodos() {
        List<Turno> turnos = new ArrayList<>();
        String sql = "SELECT * FROM turno";
        try (Connection conn = conexion.estableConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paciente paciente = new PacienteRepositorio().obtenerPorId(rs.getInt("id_paciente"));
                Medico medico = new MedicoRepositorio().obtenerPorId(rs.getInt("id_medico"));
                Turno turno = new Turno(
                        rs.getInt("id_turno"),
                        paciente,
                        medico,
                        rs.getString("fecha"),
                        rs.getString("hora")
                );
                turnos.add(turno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turnos;
    }

    // ACTUALIZAR UN TURNO
    public boolean actualizarTurno(Turno turno) {
        String sql = "UPDATE turno SET fecha = ?, hora = ?, id_paciente = ?, id_medico = ? WHERE id_turno = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, turno.getFecha());
            ps.setString(2, turno.getHora());
            ps.setInt(3, turno.getPaciente().getIdPaciente());
            ps.setInt(4, turno.getMedico().getIdMedico());
            ps.setInt(5, turno.getIdTurno());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR UN TURNO
    public boolean eliminarTurno(int idTurno) {
        String sql = "DELETE FROM turno WHERE id_turno = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTurno);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // FUNCIONES ADICIONALES
    // Buscar turnos por paciente
    public List<Turno> obtenerPorPaciente(int idPaciente) {
        List<Turno> turnos = new ArrayList<>();
        String sql = "SELECT * FROM turno WHERE id_paciente = ? ORDER BY fecha DESC, hora DESC";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Paciente paciente = new PacienteRepositorio().obtenerPorId(rs.getInt("id_paciente"));
                Medico medico = new MedicoRepositorio().obtenerPorId(rs.getInt("id_medico"));
                Turno turno = new Turno(
                        rs.getInt("id_turno"),
                        paciente,
                        medico,
                        rs.getString("fecha"),
                        rs.getString("hora")
                );
                turnos.add(turno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turnos;
    }

}
        