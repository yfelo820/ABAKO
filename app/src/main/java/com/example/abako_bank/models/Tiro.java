package com.example.abako_bank.models;

public class Tiro {

    private int _id;
    private int tipo_tirada;
    private int centena;
    private int fijo;
    private int corrido_1;
    private int corrido_2;
    private String fecha;

    public Tiro(int tipo_tirada, int centena, int fijo, int corrido_1, int corrido_2) {
        this.tipo_tirada = tipo_tirada;
        this.centena = centena;
        this.fijo = fijo;
        this.corrido_1 = corrido_1;
        this.corrido_2 = corrido_2;
    }

    public Tiro() {
    }

    public int getTipo_tirada() {
        return tipo_tirada;
    }

    public void setTipo_tirada(int tipo_tirada) {
        this.tipo_tirada = tipo_tirada;
    }

    public int getCentena() {
        return centena;
    }

    public void setCentena(int centena) {
        this.centena = centena;
    }

    public int getFijo() {
        return fijo;
    }

    public void setFijo(int fijo) {
        this.fijo = fijo;
    }

    public int getCorrido_1() {
        return corrido_1;
    }

    public void setCorrido_1(int corrido_1) {
        this.corrido_1 = corrido_1;
    }

    public int getCorrido_2() {
        return corrido_2;
    }

    public void setCorrido_2(int corrido_2) {
        this.corrido_2 = corrido_2;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return centena+" "+fijo+" "+corrido_1+" "+corrido_2;
    }
}
