

package com.mycompany.proyectoparrinomunoz.Entity;

public class Usuario {

    private int idUsuario;
    private String usuario;
    private String contrasenia;
    private String rol; // Ej: "ADMIN", "MEDICO", "RECEPCION", etc.

    // Constructores
    public Usuario() {
    }

    public Usuario(int idUsuario, String usuario, String contrasenia, String rol) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public Usuario(String usuario, String contrasenia, String rol) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public Usuario(int i, String admin, String admin1, String administrador, String admin2) {

    }

    // Getters y setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario != null ? usuario : "";
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia != null ? contrasenia : "";
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol != null ? rol : "";
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return usuario + " (" + rol + ")";
    }
}
