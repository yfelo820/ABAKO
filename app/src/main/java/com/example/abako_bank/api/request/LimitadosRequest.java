package com.example.abako_bank.api.request;

public class LimitadosRequest {

    private String bola;
    private String parlet;

    public LimitadosRequest() {
    }

    public LimitadosRequest(String bola, String parlet) {
        this.bola = bola;
        this.parlet = parlet;
    }

    public String getBola() {
        return bola;
    }

    public void setBola(String bola) {
        this.bola = bola;
    }

    public String getParlet() {
        return parlet;
    }

    public void setParlet(String parlet) {
        this.parlet = parlet;
    }
}
