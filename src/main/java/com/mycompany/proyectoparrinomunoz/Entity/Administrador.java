/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoparrinomunoz.Entity;

public class Administrador extends Usuario {

    private String areaResponsable;
    private String telefonoContacto;

    public Administrador() {
        super();
    }

    public Administrador(int idUsuario, String usuario, String contrasenia, String rol,
                         String areaResponsable, String telefonoContacto) {
        super(idUsuario, usuario, contrasenia, rol);
        this.areaResponsable = areaResponsable;
        this.telefonoContacto = telefonoContacto;
    }

    public String getAreaResponsable() {
        return areaResponsable != null ? areaResponsable : "";
    }

    public void setAreaResponsable(String areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

    public String getTelefonoContacto() {
        return telefonoContacto != null ? telefonoContacto : "";
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    @Override
    public String toString() {
        return getUsuario() + " (Admin - " + getAreaResponsable() + ")";
    }
}
