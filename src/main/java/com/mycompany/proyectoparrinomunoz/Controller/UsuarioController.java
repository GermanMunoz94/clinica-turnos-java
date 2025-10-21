package com.mycompany.proyectoparrinomunoz.Controller;

import com.mycompany.proyectoparrinomunoz.Entity.Usuario;
import com.mycompany.proyectoparrinomunoz.repositorios.UsuarioRepositorio;

public class UsuarioController {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioController() {
        this.usuarioRepositorio = new UsuarioRepositorio();
    }

    // 🔐 Autenticar usuario por email y contraseña
    public Usuario autenticar(String email, String password) {
        return usuarioRepositorio.autenticar(email, password);
    }

    // 🔍 Buscar usuario por ID (por si lo necesitas más adelante)
    public Usuario buscarPorId(int id) {
        return usuarioRepositorio.obtenerPorId(id);
    }

    // ➕ Crear usuario (opcional)
    /*public boolean crearUsuario(Usuario usuario) {
        return usuarioRepositorio.crearUsuario(usuario);
    }*/

    public boolean crearUsuario(String email, String password, String rol, int idRelacionado) {
        return false;
    }
}

