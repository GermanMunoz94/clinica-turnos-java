package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.Entity.Usuario;
import com.mycompany.proyectoparrinomunoz.Service.UsuarioService;

import java.util.List;

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController() {
        usuarioService = new UsuarioService();
    }

    public Usuario autenticar(String usuario, String contrasenia) {
        try {
            return usuarioService.autenticar(usuario, contrasenia);
        } catch (Exception e) {
            System.err.println("Error en autenticar(): " + e.getMessage());
            return null;
        }
    }

    public boolean crearUsuario(Usuario u) {
        try {
            return usuarioService.crearUsuario(u);
        } catch (Exception e) {
            System.err.println("Error en crearUsuario(): " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarUsuario(Usuario u) {
        try {
            return usuarioService.actualizarUsuario(u);
        } catch (Exception e) {
            System.err.println("Error en actualizarUsuario(): " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarUsuario(int id) {
        try {
            return usuarioService.eliminarUsuario(id);
        } catch (Exception e) {
            System.err.println("Error en eliminarUsuario(): " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> listarUsuarios() {
        try {
            return usuarioService.listarUsuarios();
        } catch (Exception e) {
            System.err.println("Error en listarUsuarios(): " + e.getMessage());
            return List.of();
        }
    }
}
