package com.example.abako_bank.models;

public class Parlet {
    private int _id;
    private String numero;
    private boolean envio;
    private String fecha;
    private int monto;
    private double monto_entregado;
    private boolean ganador;
    private double premio;

    public Parlet(int _id, String numero, boolean envio, String fecha, int monto, double monto_entregado, boolean ganador, double premio) {
        this._id = _id;
        this.numero = numero;
        this.envio = envio;
        this.fecha = fecha;
        this.monto = monto;
        this.monto_entregado = monto_entregado;
        this.ganador = ganador;
        this.premio = premio;
    }

    public Parlet() {
    }

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

    public double getMonto_entregado() {
        return monto_entregado;
    }

    public void setMonto_entregado(double monto_entregado) {
        this.monto_entregado = monto_entregado;
    }

    public boolean isGanador() {
        return ganador;
    }

    public void setGanador(boolean ganador) {
        this.ganador = ganador;
    }

    public double getPremio() {
        return premio;
    }

    public void setPremio(double premio) {
        this.premio = premio;
    }
}
