package com.example.abako_bank.models.config;

import com.orm.SugarRecord;

public class Config_Dias_Inactivo extends SugarRecord<Config_Dias_Inactivo> {

    private int dias_inactivo;

    public int getDias_inactivo() {
        return dias_inactivo;
    }

    public void setDias_inactivo(int dias_inactivo) {
        this.dias_inactivo = dias_inactivo;
    }
}
