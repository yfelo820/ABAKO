package com.example.abako_bank.models.models2;

public class Parlet {

    public int _id;
    public String numero;
    public boolean envio;
    public String fecha;
    public int monto;
    public int monto_entregado;
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

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getMonto_entregado() {
        return monto_entregado;
    }

    public void setMonto_entregado(int monto_entregado) {
        this.monto_entregado = monto_entregado;
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
