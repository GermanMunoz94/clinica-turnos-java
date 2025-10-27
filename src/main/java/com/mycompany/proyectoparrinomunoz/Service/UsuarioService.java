package com.mycompany.proyectoparrinomunoz.Service;

import com.mycompany.proyectoparrinomunoz.Entity.Usuario;
import com.mycompany.proyectoparrinomunoz.repositorios.UsuarioRepositorio;

import java.util.List;

public class UsuarioService {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioService() {
        usuarioRepositorio = new UsuarioRepositorio();
    }

    public Usuario autenticar(String usuario, String contrasenia) {
        if (usuario == null || contrasenia == null ||
                usuario.isBlank() || contrasenia.isBlank()) {
            System.err.println("Error: usuario o contraseña vacíos.");
            return null;
        }

        try {
            Usuario u = usuarioRepositorio.autenticar(usuario, contrasenia);
            if (u == null) {
                System.err.println("Credenciales incorrectas o usuario inexistente.");
            }
            return u;
        } catch (Exception e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            return null;
        }
    }

    public boolean crearUsuario(Usuario u) {
        if (!validarUsuario(u)) {
            System.err.println("Error: datos del usuario incompletos.");
            return false;
        }

        // Evitar duplicados por nombre de usuario
        List<Usuario> existentes = usuarioRepositorio.listarUsuarios();
        boolean duplicado = existentes.stream()
                .anyMatch(existing -> existing.getUsuario().equalsIgnoreCase(u.getUsuario()));

        if (duplicado) {
            System.err.println("Error: el nombre de usuario ya existe.");
            return false;
        }

        return usuarioRepositorio.crearUsuario(u);
    }

    public boolean actualizarUsuario(Usuario u) {
        if (u == null || u.getIdUsuario() <= 0) {
            System.err.println("Error: ID de usuario no válido.");
            return false;
        }
        return usuarioRepositorio.actualizarUsuario(u);
    }

    public boolean eliminarUsuario(int id) {
        if (id <= 0) {
            System.err.println("Error: ID de usuario no válido.");
            return false;
        }
        return usuarioRepositorio.eliminarUsuario(id);
    }

    public List<Usuario> listarUsuarios() {
        try {
            return usuarioRepositorio.listarUsuarios();
        } catch (Exception e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            return List.of();
        }
    }

    private boolean validarUsuario(Usuario u) {
        return u != null &&
                u.getUsuario() != null && !u.getUsuario().isBlank() &&
                u.getContrasenia() != null && !u.getContrasenia().isBlank() &&
                u.getRol() != null && !u.getRol().isBlank();
    }
}
