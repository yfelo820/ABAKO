package com.example.abako_bank.api.request.colector;

public class CObjectTwo {

    private int dias_inactividad;

    private int es_plantilla;

    public CObjectTwo() {
    }

    public CObjectTwo(int dias_inactividad, int es_plantilla) {
        this.dias_inactividad = dias_inactividad;
        this.es_plantilla = es_plantilla;
    }

    public int getDias_inactividad() {
        return dias_inactividad;
    }

    public void setDias_inactividad(int dias_inactividad) {
        this.dias_inactividad = dias_inactividad;
    }

    public int getEs_plantilla() {
        return es_plantilla;
    }

    public void setEs_plantilla(int es_plantilla) {
        this.es_plantilla = es_plantilla;
    }
}
