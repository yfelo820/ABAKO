package com.example.abako_bank.models.models2;

public class Listero {

    public int _id;
    public String nombre;
    public String bruto;
    public String limpio;
    public String pago;
    public String createdAt;
    public String updatedAt;
    public String deletedAt;
    public int usuarioId;
    public int colectorId;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBruto() {
        return bruto;
    }

    public void setBruto(String bruto) {
        this.bruto = bruto;
    }

    public String getLimpio() {
        return limpio;
    }

    public void setLimpio(String limpio) {
        this.limpio = limpio;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
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

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getColectorId() {
        return colectorId;
    }

    public void setColectorId(int colectorId) {
        this.colectorId = colectorId;
    }
}
