package com.example.abako_bank.models.models2;

public class Bola {

    public int _id;
    public String numero;
    public boolean envio;
    public String fecha;
    public int monto_corrido;
    public int monto_fijo;
    public int monto_total;
    public boolean ganador;
    public int premio;
    public String createdAt;
    public String updatedAt;
    public String deletedAt;
    public int jugadaId;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isEnvio() {
        return envio;
    }

    public void setEnvio(boolean envio) {
        this.envio = envio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getMonto_corrido() {
        return monto_corrido;
    }

    public void setMonto_corrido(int monto_corrido) {
        this.monto_corrido = monto_corrido;
    }

    public int getMonto_fijo() {
        return monto_fijo;
    }

    public void setMonto_fijo(int monto_fijo) {
        this.monto_fijo = monto_fijo;
    }

    public int getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(int monto_total) {
        this.monto_total = monto_total;
    }

    public boolean isGanador() {
        return ganador;
    }

    public void setGanador(boolean ganador) {
        this.ganador = ganador;
    }

    public int getPremio() {
        return premio;
    }

    public void setPremio(int premio) {
        this.premio = premio;
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

    public int getJugadaId() {
        return jugadaId;
    }

    public void setJugadaId(int jugadaId) {
        this.jugadaId = jugadaId;
    }
}
