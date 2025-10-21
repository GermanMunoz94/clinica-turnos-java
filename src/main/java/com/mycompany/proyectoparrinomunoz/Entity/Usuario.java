

package com.mycompany.proyectoparrinomunoz.Entity;

public class Usuario {
    private int idUsuario;
    private String email;
    private String password;
    private String rol;
    private int idRelacionado;

    public Usuario() {}

    public Usuario(int idUsuario, String email, String password, String rol, int idRelacionado) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.idRelacionado = idRelacionado;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public int getIdRelacionado() { return idRelacionado; }
    public void setIdRelacionado(int idRelacionado) { this.idRelacionado = idRelacionado; }

    @Override
    public String toString() {
        return rol + " — " + email;
    }
}

