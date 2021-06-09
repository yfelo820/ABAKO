package com.example.abako_bank.api.request.colector;

public class CObjectOne {

    private String nombre;

    private double bruto;

    private double limpio;

    private double pago;

    private CObjectTwo configcolector;

    public CObjectOne(String nombre, double bruto, double limpio, double pago, CObjectTwo CObjectTwo) {
        this.nombre = nombre;
        this.bruto = bruto;
        this.limpio = limpio;
        this.pago = pago;
        this.configcolector = CObjectTwo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public CObjectTwo getConfigcolector() {
        return configcolector;
    }

    public void setConfigcolector(CObjectTwo configcolector) {
        this.configcolector = configcolector;
    }
}
