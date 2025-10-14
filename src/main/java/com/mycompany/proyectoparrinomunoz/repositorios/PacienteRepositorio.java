package com.mycompany.proyectoparrinomunoz.repositorios;
import com.mycompany.proyectoparrinomunoz.Conexion;
import com.mycompany.proyectoparrinomunoz.Entity.Paciente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepositorio {

    private Conexion conexion;

    public PacienteRepositorio() {
        conexion = new Conexion();
    }

    // CREAR PACIENTE
    public boolean crearPaciente(Paciente paciente) {
        String sql = "INSERT INTO paciente (nombre, apellido, dni, telefono, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setString(3, paciente.getDni());
            ps.setString(4, paciente.getTelefono());
            ps.setString(5, paciente.getEmail());


            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    paciente.setIdPaciente(rs.getInt(1));
                }
            }

            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // OBTENER PACIENTE POR ID
    public Paciente obtenerPorId(int idPaciente) {
        String sql = "SELECT * FROM paciente WHERE id_paciente = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // OBTENER TODOS LOS PACIENTES
    public List<Paciente> obtenerTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM paciente";

        try (Connection conn = conexion.estableConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacientes;
    }

    // ACTUALIZAR PACIENTE
    public boolean actualizarPaciente(Paciente paciente) {
        String sql = "UPDATE paciente SET nombre = ?, apellido = ?, dni = ?, telefono = ?, email = ? WHERE id_paciente = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setString(3, paciente.getDni());
            ps.setString(4, paciente.getTelefono()); // correcto
            ps.setString(5, paciente.getEmail());    // correcto

            ps.setInt(6, paciente.getIdPaciente());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR PACIENTE
    public boolean eliminarPaciente(int idPaciente) {
        String sql = "DELETE FROM paciente WHERE id_paciente = ?";
        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // FUNCIONES ADICIONALES
    // Buscar pacientes por nombre o apellido
    public List<Paciente> buscarPorNombreApellido(String texto) {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM paciente WHERE nombre LIKE ? OR apellido LIKE ?";

        try (Connection conn = conexion.estableConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String patron = "%" + texto + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacientes;
    }
}
