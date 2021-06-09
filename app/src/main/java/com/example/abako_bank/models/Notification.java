package com.example.abako_bank.models;

import com.orm.SugarRecord;

public class Notification extends SugarRecord<Notification> {
    private int _id;
    private String uid_dispositivo;
    private String imei;
    private String mensaje;
    private boolean estado;
    private String fecha;
    private String fecha_cuando_notifico;
    private String tipo;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private int dispositivoId;
    private Dispositivo dispositivo;

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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha_cuando_notifico() {
        return fecha_cuando_notifico;
    }

    public void setFecha_cuando_notifico(String fecha_cuando_notifico) {
        this.fecha_cuando_notifico = fecha_cuando_notifico;
    }

    public int getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(int dispositivoId) {
        this.dispositivoId = dispositivoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
