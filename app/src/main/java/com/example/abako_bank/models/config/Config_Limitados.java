package com.example.abako_bank.models.config;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;
import java.util.List;

public class Config_Limitados extends SugarRecord<Config_Limitados> {


    @Ignore
    private List<Integer> limitados_bola;

    private String limitados_bola_string;

    @Ignore
    private List<Integer> limitados_parlet;

    private String limitados_parlet_string;



    public Config_Limitados() {
    }

    public void setLimitados_bola(List<Integer> limitados_bola) {
        limitados_bola_string = "";
        for(int i = 0; i < limitados_bola.size()-1; i++){
            limitados_bola_string += limitados_bola.get(i)+",";
        }
        if(!limitados_bola.isEmpty()){
            limitados_bola_string += limitados_bola.get(limitados_bola.size()-1);
        }

    }

    public void setLimitados_parlet(List<Integer> limitados_parlet) {
        limitados_parlet_string = "";
        for(int i = 0; i < limitados_parlet.size()-1; i++){
            limitados_parlet_string += limitados_parlet.get(i)+",";
        }
        if(!limitados_parlet.isEmpty()){
            limitados_parlet_string += limitados_parlet.get(limitados_parlet.size()-1);
        }

    }





    public List<Integer> getLimitados_bola() {
        limitados_bola = new ArrayList<>();
        if(limitados_bola_string != null){
            String [] values = limitados_bola_string.split(",");
            for(int i =0; i  < values.length; i++){
                if(values[i]!=""){
                    limitados_bola.add(Integer.valueOf(values[i]));
                }
            }
        }


        return limitados_bola;
    }

    public List<Integer> getLimitados_parlet() {
        limitados_parlet = new ArrayList<>();
        if(limitados_parlet_string != null){
            String [] values =limitados_parlet_string.split(",");
            for(int i =0; i  < values.length; i++){
                if(values[i]!=""){
                    limitados_parlet.add(Integer.valueOf(values[i]));
                }
            }
        }

        return limitados_parlet;
    }

    public String getLimitados_bola_string() {
        return limitados_bola_string;
    }

    public String getLimitados_parlet_string() {
        return limitados_parlet_string;
    }


}
