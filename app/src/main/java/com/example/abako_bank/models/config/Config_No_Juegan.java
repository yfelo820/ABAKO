package com.example.abako_bank.models.config;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;
import java.util.List;

public class Config_No_Juegan extends SugarRecord<Config_No_Juegan> {

    @Ignore
    private List<Integer> no_juegan;

    private String limitados_no_juegan_string;

    public Config_No_Juegan() {
    }

    public void setNo_juegan(List<Integer> no_juegan) {
        limitados_no_juegan_string = "";
        for(int i = 0; i < no_juegan.size()-1; i++){
            limitados_no_juegan_string += no_juegan.get(i)+",";
        }
        if(!no_juegan.isEmpty()){
            limitados_no_juegan_string += no_juegan.get(no_juegan.size()-1);
        }

    }

    public List<Integer> getNo_juegan() {
        no_juegan = new ArrayList<>();
        if(limitados_no_juegan_string != null){
            String [] values =limitados_no_juegan_string.split(",");
            for(int i =0; i  < values.length; i++){
                if(values[i]!=""){
                    no_juegan.add(Integer.valueOf(values[i]));
                }

            }
        }

        return no_juegan;
    }

    public String getLimitados_no_juegan_string() {
        return limitados_no_juegan_string;
    }
}
