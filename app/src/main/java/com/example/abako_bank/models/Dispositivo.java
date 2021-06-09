package com.example.abako_bank.models;

public class Dispositivo {

    private int _id;

    private String uid_dispositivo;

    private String imei;

    private boolean estado;

    private boolean requiere_actualizar;

    private boolean es_banco;

    private boolean notificado_al_banco;

    private String fecha_cambio_estado;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private String listanegraId;

    private String usuarioId;

    private User usuario;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUid_dispositivo() {
        return uid_dispositivo;
    }

    public void setUid_dispositivo(String uid_dispositivo) {
        this.uid_dispositivo = uid_dispositivo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isRequiere_actualizar() {
        return requiere_actualizar;
    }

    public void setRequiere_actualizar(boolean requiere_actualizar) {
        this.requiere_actualizar = requiere_actualizar;
    }

    public boolean isEs_banco() {
        return es_banco;
    }

    public void setEs_banco(boolean es_banco) {
        this.es_banco = es_banco;
    }

    public boolean isNotificado_al_banco() {
        return notificado_al_banco;
    }

    public void setNotificado_al_banco(boolean notificado_al_banco) {
        this.notificado_al_banco = notificado_al_banco;
    }

    public String getFecha_cambio_estado() {
        return fecha_cambio_estado;
    }

    public void setFecha_cambio_estado(String fecha_cambio_estado) {
        this.fecha_cambio_estado = fecha_cambio_estado;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getListanegraId() {
        return listanegraId;
    }

    public void setListanegraId(String listanegraId) {
        this.listanegraId = listanegraId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
}
