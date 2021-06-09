package com.example.abako_bank.api.request.listero;

import com.example.abako_bank.api.request.listero.FullConfig;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private String nombre;

    private double bruto;

    private double limpio;

    private double pago;

    private List<FullConfig> configlisteros;

    private String nombre_colector;

    public Config(String nombre, double bruto, double limpio, double pago, String nombre_colector) {
        this.nombre = nombre;
        this.bruto = bruto;
        this.limpio = limpio;
        this.pago = pago;
        configlisteros = new ArrayList<>();
        this.nombre_colector = nombre_colector;
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

    public List<FullConfig> getConfiguraciones() {
        return configlisteros;
    }

    public void setConfiguraciones(List<FullConfig> configuraciones) {
        this.configlisteros = configuraciones;
    }

    public void addFullConfig(FullConfig fullConfig){
        this.configlisteros.add(fullConfig);
    }

    public String getNombre_colector() {
        return nombre_colector;
    }

    public void setNombre_colector(String nombre_colector) {
        this.nombre_colector = nombre_colector;
    }
}
