package com.example.abako_bank.api.response;

public class BankUser {
    private int _id;
    private String usuario;
    private String contrasena;
    private int rol;
    private String updatedAt;
    private String createdAt;

    public BankUser() {
    }

    public BankUser(int _id, String usuario, String contrasena, int rol, String updatedAt, String createdAt) {
        this._id = _id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
