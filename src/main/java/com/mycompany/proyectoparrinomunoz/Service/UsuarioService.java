package com.mycompany.proyectoparrinomunoz.Service;

import com.mycompany.proyectoparrinomunoz.Entity.Usuario;
import com.mycompany.proyectoparrinomunoz.repositorios.UsuarioRepositorio;

public class UsuarioService {
    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioService() {
        this.usuarioRepositorio = new UsuarioRepositorio();
    }

    public Usuario autenticar(String email, String password) {
        return usuarioRepositorio.autenticar(email, password);
    }

    public boolean crearUsuario(Usuario usuario) {
        return usuarioRepositorio.crearUsuario(usuario);
    }
}
