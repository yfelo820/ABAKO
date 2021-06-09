package com.example.abako_bank.models.models2;

import java.util.List;

public class Play {

    public int _id;
    public int tipo_jugada;
    public int tipo_tirada;
    public boolean estado;
    public String fecha;
    public double bruto;
    public double limpio;
    public double pago_listero;
    public double ganancia_banco;
    public int premio;
    public String createdAt;
    public String updatedAt;
    public String deletedAt;
    public int listeroId;
    public Object tiroId;
    public Listero listero;
    public List<Bola> bolas;
    public List<Centena> centenas;
    public List<Parlet> parlets;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getTipo_jugada() {
        return tipo_jugada;
    }

    public void setTipo_jugada(int tipo_jugada) {
        this.tipo_jugada = tipo_jugada;
    }

    public int getTipo_tirada() {
        return tipo_tirada;
    }

    public void setTipo_tirada(int tipo_tirada) {
        this.tipo_tirada = tipo_tirada;
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

    public double getBruto() {
        return bruto;
    }

    public void setBruto(double bruto) {
        this.bruto = bruto;
    }

    public double getLimpio() {
        return limpio;
    }

    public void setLimpio(double limpio) {
        this.limpio = limpio;
    }

    public double getPago_listero() {
        return pago_listero;
    }

    public void setPago_listero(double pago_listero) {
        this.pago_listero = pago_listero;
    }

    public double getGanancia_banco() {
        return ganancia_banco;
    }

    public void setGanancia_banco(double ganancia_banco) {
        this.ganancia_banco = ganancia_banco;
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

    public int getListeroId() {
        return listeroId;
    }

    public void setListeroId(int listeroId) {
        this.listeroId = listeroId;
    }

    public Object getTiroId() {
        return tiroId;
    }

    public void setTiroId(Object tiroId) {
        this.tiroId = tiroId;
    }

    public Listero getListero() {
        return listero;
    }

    public void setListero(Listero listero) {
        this.listero = listero;
    }

    public List<Bola> getBolas() {
        return bolas;
    }

    public void setBolas(List<Bola> bolas) {
        this.bolas = bolas;
    }

    public List<Centena> getCentenas() {
        return centenas;
    }

    public void setCentenas(List<Centena> centenas) {
        this.centenas = centenas;
    }

    public List<Parlet> getParlets() {
        return parlets;
    }

    public void setParlets(List<Parlet> parlets) {
        this.parlets = parlets;
    }
}
