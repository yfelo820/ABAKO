package com.example.abako_bank.models;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class Listero {
    private int _id;
    private String nombre;
    private String bruto;
    private String limpio;
    private String pago;
    private String premio;
    private String g_p;
    private int usuarioId;
    private  int colectorId;
    List<Jugada> jugadas;

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

    public List<Jugada> getJugadas() {
        return jugadas;
    }

    public void setJugadas(List<Jugada> jugadas) {
        this.jugadas = jugadas;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getG_p() {
        return g_p;
    }

    public void setG_p(String g_p) {
        this.g_p = g_p;
    }

    public void calcular(){
        double value_limpio = 0;
        double value_bruto = 0;
        double value_premio = 0;
        double value_g_p = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        for (int j = 0; j < jugadas.size(); j++){
            Jugada jugada = jugadas.get(j);
            value_limpio += jugada.getLimpio();
            value_bruto += jugada.getBruto();
            value_premio += jugada.getPremio();
            value_g_p = value_limpio - value_bruto;
        }


        limpio = String.valueOf(df.format(value_limpio));
        bruto = String.valueOf(df.format(value_bruto));
        premio = String.valueOf(df.format(value_premio));
        g_p = String.valueOf(df.format(value_g_p));
    }
}
