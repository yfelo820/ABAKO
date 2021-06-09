package com.example.abako_bank.models.models2;

public class Lista1 {

    public int id;
    public String nombre;
    public Double limpio;
    public Double premio;
    public Double gana;

    public Lista1(int i, String n, Double l, Double p, Double g)
    {
        id = i;
        nombre = n;
        limpio = l;
        premio = p;
        gana = g;

    }

    public Lista1() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLimpio() {
        return limpio;
    }

    public void setLimpio(Double limpio) {
        this.limpio = limpio;
    }

    public Double getPremio() {
        return premio;
    }

    public void setPremio(Double premio) {
        this.premio = premio;
    }

    public Double getGana() {
        return gana;
    }

    public void setGana(Double gana) {
        this.gana = gana;
    }

    @Override
    public String toString()
    {
        return nombre+"  |  "+limpio+"  |  "+premio+"  |  "+gana;

    }


}
