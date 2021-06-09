package com.example.abako_bank.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.abako_bank.api.repository.ConfiguracionRepository;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.ColectorsResponse;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.PendingDeviceResponse;
import com.example.abako_bank.models.Dispositivo;
import com.example.abako_bank.models.Settings;
import com.example.abako_bank.models.config.Config_Dias_Inactivo;
import com.example.abako_bank.models.config.Config_Horario_Dia;
import com.example.abako_bank.models.config.Config_Horario_Noche;
import com.example.abako_bank.models.config.Config_Limitados;
import com.example.abako_bank.models.config.Config_Pagos;
import com.example.abako_bank.models.config.Config_Pagos_Limitados;
import com.example.abako_bank.models.config.Config_Percent_Pago;
import com.example.abako_bank.models.config.Config_Topes_Lim;
import com.example.abako_bank.models.config.Config_User_Pass;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private ConfiguracionRepository configuracionRepository;
    private MutableLiveData<DefaultResponse> listeroResult = new MutableLiveData<>();

    private MutableLiveData<DefaultResponse> colectorResult = new MutableLiveData<>();

    private MutableLiveData<Config_Limitados> config_limitadosMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<PendingDeviceResponse> pendingdevicesresult;

    private MutableLiveData<ColectorsResponse> colectoresTodos;

    ConfigViewModel(ConfiguracionRepository configuracionRepository){
        this.configuracionRepository = configuracionRepository;
        pendingdevicesresult = new MutableLiveData<>();
        colectoresTodos = new MutableLiveData<>();

    }

    public void addConfiguracionListero(final Dispositivo dispositivo){
        this.configuracionRepository.addConfiguracionListero(dispositivo).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code() == 200){
                    DefaultResponse defaultResponse = new DefaultResponse("OK", "");
                    defaultResponse.setStatus("200");
                    defaultResponse.setMessage(response.body().getMessage());
                    listeroResult.postValue(defaultResponse);
                }else if(response.code() == 401){
                    //pedir autenticacion
                    Settings settings = Settings.listAll(Settings.class).get(0);
                    configuracionRepository.autenticarBanco(settings.getUsername(), settings.getPassword(),
                            settings.getDevice_uuid(), settings.getDevice_id()).enqueue(new Callback<AutenticateResponse>() {
                        @Override
                        public void onResponse(Call<AutenticateResponse> call, Response<AutenticateResponse> response) {
                            if(response.code() == 200){
                                AutenticateResponse defaultResponse = response.body();
                                defaultResponse.setStatus("200");
                                //guardar el token del banco
                                if(!Settings.listAll(Settings.class).isEmpty()){
                                    Settings settings = Settings.listAll(Settings.class).get(0);
                                    settings.setToken(defaultResponse.getToken());
                                    settings.save();
                                    addConfiguracionListero(dispositivo);
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<AutenticateResponse> call, Throwable t) {
                            DefaultResponse defaultResponse = new DefaultResponse("ERROR", "");
                            defaultResponse.setStatus("400");
                            listeroResult.postValue(defaultResponse);

                        }
                    });

                } else if (response.code() == 400) {
                    try {
                        String res = response.errorBody().string();
                        Gson g = new Gson();
                        DefaultResponse defaultResponse = g.fromJson(res, DefaultResponse.class);
                        listeroResult.postValue(defaultResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                DefaultResponse defaultResponse = new DefaultResponse("ERROR", "");
                defaultResponse.setStatus("400");
                defaultResponse.setMessage("Error en la petición");
                listeroResult.postValue(defaultResponse);

            }
        });
    }

    public void addConfiguracionColector(final Dispositivo dispositivo){
        this.configuracionRepository.addConfiguracionColector(dispositivo).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code() == 200){
                    DefaultResponse defaultResponse = new DefaultResponse(response.body().getMessage(), "");
                    defaultResponse.setStatus("200");
                    colectorResult.postValue(defaultResponse);
                }else if(response.code() == 401){
                    //pedir autenticacion
                    Settings settings = Settings.listAll(Settings.class).get(0);
                    configuracionRepository.autenticarBanco(settings.getUsername(), settings.getPassword(),
                            settings.getDevice_uuid(), settings.getDevice_id()).enqueue(new Callback<AutenticateResponse>() {
                        @Override
                        public void onResponse(Call<AutenticateResponse> call, Response<AutenticateResponse> response) {
                            if(response.code() == 200){
                                AutenticateResponse defaultResponse = response.body();
                                defaultResponse.setStatus("200");
                                //guardar el token del banco
                                if(!Settings.listAll(Settings.class).isEmpty()){
                                    Settings settings = Settings.listAll(Settings.class).get(0);
                                    settings.setToken(defaultResponse.getToken());
                                    settings.save();
                                    addConfiguracionColector(dispositivo);
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<AutenticateResponse> call, Throwable t) {
                            DefaultResponse defaultResponse = new DefaultResponse("ERROR", "");
                            defaultResponse.setStatus("400");
                            defaultResponse.setMessage("Error en la petición");
                            colectorResult.postValue(defaultResponse);

                        }
                    });

                }else if(response.code() == 400){
                    try {
                        String res = response.errorBody().string();
                        Gson g = new Gson();
                        DefaultResponse defaultResponse = g.fromJson(res, DefaultResponse.class);
                        colectorResult.postValue(defaultResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                DefaultResponse defaultResponse = new DefaultResponse("ERROR", "");
                defaultResponse.setStatus("400");
                defaultResponse.setMessage("Error en la petición");
                colectorResult.postValue(defaultResponse);

            }
        });
    }

    public void saveConfigPagosPercent(int tipo_juego, String pago_normal_bola, String pago_no_normal_bola, String pago_normal_parlet,
                                       String pago_no_normal_parlet, String pago_normal_centena, String pago_no_normal_centena){
        List<Config_Percent_Pago> config_percent_pagos = Config_Percent_Pago.find(Config_Percent_Pago.class, "tipojuego = ?", String.valueOf(tipo_juego));
        if(config_percent_pagos.isEmpty()){
            Config_Percent_Pago config_percent_pago = new Config_Percent_Pago();

            config_percent_pago.setTipo_juego(tipo_juego);
            config_percent_pago.setBola_normal(Double.valueOf(pago_normal_bola));
            config_percent_pago.setBola_no_normal(Double.valueOf(pago_no_normal_bola));

            config_percent_pago.setParlet_normal(Double.valueOf(pago_normal_parlet));
            config_percent_pago.setParlet_no_normal(Double.valueOf(pago_no_normal_parlet));

            config_percent_pago.setCentena_normal(Double.valueOf(pago_normal_centena));
            config_percent_pago.setCentene_no_normal(Double.valueOf(pago_no_normal_centena));

            config_percent_pago.save();
        }else{
            Config_Percent_Pago config_percent_pago = config_percent_pagos.get(0);

            config_percent_pago.setTipo_juego(tipo_juego);
            config_percent_pago.setBola_normal(Double.valueOf(pago_normal_bola));
            config_percent_pago.setBola_no_normal(Double.valueOf(pago_no_normal_bola));

            config_percent_pago.setParlet_normal(Double.valueOf(pago_normal_parlet));
            config_percent_pago.setParlet_no_normal(Double.valueOf(pago_no_normal_parlet));

            config_percent_pago.setCentena_normal(Double.valueOf(pago_normal_centena));
            config_percent_pago.setCentene_no_normal(Double.valueOf(pago_no_normal_centena));

            config_percent_pago.save();
        }
    }

    public void saveConfigHorarioDia(int tipo_juego, String inactivo_hora, String inactivo_minuto, String activo_hora, String activo_minuto){
        List<Config_Horario_Dia> config_horario_dias = Config_Horario_Dia.find(Config_Horario_Dia.class,"tipojuego = ?", String.valueOf(tipo_juego));
        if(config_horario_dias.isEmpty()){
            Config_Horario_Dia config_horario_dia = new Config_Horario_Dia();
            config_horario_dia.setTipo_juego(tipo_juego);
            config_horario_dia.setHora_inactivo(inactivo_hora);
            config_horario_dia.setMinuto_inactivo(inactivo_minuto);

            config_horario_dia.setHora_activo(activo_hora);
            config_horario_dia.setMinuto_activo(activo_minuto);

            config_horario_dia.save();
        }else{
            Config_Horario_Dia config_horario_dia = config_horario_dias.get(0);
            config_horario_dia.setTipo_juego(tipo_juego);
            config_horario_dia.setHora_inactivo(inactivo_hora);
            config_horario_dia.setMinuto_inactivo(inactivo_minuto);

            config_horario_dia.setHora_activo(activo_hora);
            config_horario_dia.setMinuto_activo(activo_minuto);

            config_horario_dia.save();
        }
    }

    public void saveConfigHorarioNoche(int tipo_juego, String inactivo_hora, String inactivo_minuto, String activo_hora, String activo_minuto){
        List<Config_Horario_Noche> config_horario_noches = Config_Horario_Noche.find(Config_Horario_Noche.class,"tipojuego = ?", String.valueOf(tipo_juego));
        if(config_horario_noches.isEmpty()){
            Config_Horario_Noche config_horario_noche = new Config_Horario_Noche();
            config_horario_noche.setTipo_juego(tipo_juego);
            config_horario_noche.setHora_inactivo(inactivo_hora);
            config_horario_noche.setMinuto_inactivo(inactivo_minuto);

            config_horario_noche.setHora_activo(activo_hora);
            config_horario_noche.setMinuto_activo(activo_minuto);

            config_horario_noche.save();
        }else{
            Config_Horario_Noche config_horario_noche = config_horario_noches.get(0);
            config_horario_noche.setTipo_juego(tipo_juego);
            config_horario_noche.setHora_inactivo(inactivo_hora);
            config_horario_noche.setMinuto_inactivo(inactivo_minuto);

            config_horario_noche.setHora_activo(activo_hora);
            config_horario_noche.setMinuto_activo(activo_minuto);

            config_horario_noche.save();
        }
    }

    public void saveConfigPagos(int tipo_juego, String pago_centena, String pago_corrido, String pago_fijo,
                                String pago_parlet, String pago_parlet_m){
        List<Config_Pagos> config_pagos = Config_Pagos.find(Config_Pagos.class,"tipojuego = ?", String.valueOf(tipo_juego));

        if(config_pagos.isEmpty()){
            Config_Pagos configPagos = new Config_Pagos();

            configPagos.setTipo_juego(tipo_juego);
            configPagos.setCentena(Double.valueOf(pago_centena));

            configPagos.setCorrido(Double.valueOf(pago_corrido));

            configPagos.setFijo(Double.valueOf(pago_fijo));

            configPagos.setParlet(Double.valueOf(pago_parlet));

            configPagos.setParlet_m(Double.valueOf(pago_parlet_m));
            configPagos.save();
        }else{
            Config_Pagos configPagos = config_pagos.get(0);

            configPagos.setTipo_juego(tipo_juego);
            configPagos.setCentena(Double.valueOf(pago_centena));

            configPagos.setCorrido(Double.valueOf(pago_corrido));

            configPagos.setFijo(Double.valueOf(pago_fijo));

            configPagos.setParlet(Double.valueOf(pago_parlet));

            configPagos.setParlet_m(Double.valueOf(pago_parlet_m));

            configPagos.save();
        }
    }

    public void saveConfigPagosLimitados(int tipo_juego, String pago_limitado_corrido, String pago_limitado_fijo, String pago_limitado_parlet){
        List<Config_Pagos_Limitados> config_pagos_limitados = Config_Pagos_Limitados.find(Config_Pagos_Limitados.class, "tipojuego = ?", String.valueOf(tipo_juego));

        if(config_pagos_limitados.isEmpty()){
            Config_Pagos_Limitados configPagosLimitados = new Config_Pagos_Limitados();

            configPagosLimitados.setTipo_juego(tipo_juego);
            configPagosLimitados.setCorrido(Double.valueOf(pago_limitado_corrido));
            configPagosLimitados.setFijo(Double.valueOf(pago_limitado_fijo));
            configPagosLimitados.setParlet(Double.valueOf(pago_limitado_parlet));

            configPagosLimitados.save();
        }else{
            Config_Pagos_Limitados configPagosLimitados = config_pagos_limitados.get(0);

            configPagosLimitados.setTipo_juego(tipo_juego);
            configPagosLimitados.setCorrido(Double.valueOf(pago_limitado_corrido));
            configPagosLimitados.setFijo(Double.valueOf(pago_limitado_fijo));
            configPagosLimitados.setParlet(Double.valueOf(pago_limitado_parlet));

            configPagosLimitados.save();
        }


    }

    public void saveUserPass(String user, String password, String device){
        List<Config_User_Pass> config_user_passes = Config_User_Pass.listAll(Config_User_Pass.class);

        if(config_user_passes.isEmpty()){
            Config_User_Pass config_user_pass = new Config_User_Pass();

            config_user_pass.setDevice(device);
            config_user_pass.setUsername(user);
            config_user_pass.setPassword(password);

            config_user_pass.save();
        }else{
            Config_User_Pass config_user_pass = config_user_passes.get(0);

            config_user_pass.setDevice(device);
            config_user_pass.setUsername(user);
            config_user_pass.setPassword(password);

            config_user_pass.save();

        }


    }

    public void saveConfigTopes(int tipo_juego, String tope_bola, String tope_parlet, String tope_centena){
        List<Config_Topes_Lim> config_limitados = Config_Topes_Lim.find(Config_Topes_Lim.class, "tipojuego = ?", String.valueOf(tipo_juego));

        if(config_limitados.isEmpty()){
            Config_Topes_Lim configLimitados = new Config_Topes_Lim();

            configLimitados.setTipo_juego(tipo_juego);
            configLimitados.setTope_bola(Double.valueOf(tope_bola));
            configLimitados.setTope_centena(Double.valueOf(tope_centena));
            configLimitados.setTope_parlet(Double.valueOf(tope_parlet));

            configLimitados.save();
        }else{
            Config_Topes_Lim configLimitados = config_limitados.get(0);

            configLimitados.setTipo_juego(tipo_juego);
            configLimitados.setTope_bola(Double.valueOf(tope_bola));
            configLimitados.setTope_centena(Double.valueOf(tope_centena));
            configLimitados.setTope_parlet(Double.valueOf(tope_parlet));

            configLimitados.save();
        }


    }

    public void saveLetra(String letra, String device){
        List<Config_User_Pass> config_user_passes = Config_User_Pass.listAll(Config_User_Pass.class);

        if(config_user_passes.isEmpty()){
            Config_User_Pass config_user_pass = new Config_User_Pass();

            config_user_pass.setDevice(device);
            config_user_pass.setNombre(letra);

            config_user_pass.save();
        }else{
            Config_User_Pass config_user_pass = config_user_passes.get(0);

            config_user_pass.setDevice(device);
            config_user_pass.setNombre(letra);

            config_user_pass.save();

        }


    }

    public void saveLetraListero(String letra, String device, String nombre_colector){
        List<Config_User_Pass> config_user_passes = Config_User_Pass.listAll(Config_User_Pass.class);

        if(config_user_passes.isEmpty()){
            Config_User_Pass config_user_pass = new Config_User_Pass();

            config_user_pass.setDevice(device);
            config_user_pass.setNombre(letra);
            config_user_pass.setColector_nombre(nombre_colector);

            config_user_pass.save();
        }else{
            Config_User_Pass config_user_pass = config_user_passes.get(0);

            config_user_pass.setDevice(device);
            config_user_pass.setNombre(letra);
            config_user_pass.setColector_nombre(nombre_colector);

            config_user_pass.save();

        }


    }

    public void saveDiasInactivo(int dias){
        List<Config_Dias_Inactivo> config_dias_inactivos = Config_Dias_Inactivo.listAll(Config_Dias_Inactivo.class);

        if(config_dias_inactivos.isEmpty()){
            Config_Dias_Inactivo config_dias_inactivo = new Config_Dias_Inactivo();

            config_dias_inactivo.setDias_inactivo(dias);

            config_dias_inactivo.save();
        }else{
            Config_Dias_Inactivo config_dias_inactivo = config_dias_inactivos.get(0);

            config_dias_inactivo.setDias_inactivo(dias);

            config_dias_inactivo.save();

        }


    }

    public LiveData<Config_Limitados> getConfig_limitadosMutableLiveData() {
        return config_limitadosMutableLiveData;
    }

    public void setConfig_limitadosMutableLiveData( Config_Limitados config_limitadosMutableLiveData) {
        this.config_limitadosMutableLiveData.setValue(config_limitadosMutableLiveData);
    }

    public MutableLiveData<DefaultResponse> getListeroResult() {
        return listeroResult;
    }

    public void setListeroResult(MutableLiveData<DefaultResponse> listeroResult) {
        this.listeroResult = listeroResult;
    }

    public void getPendingDevices(){
        this.configuracionRepository.getAllPendingDevices().enqueue(new Callback<PendingDeviceResponse>() {
            @Override
            public void onResponse(Call<PendingDeviceResponse> call, Response<PendingDeviceResponse> response) {
                if(response.code() == 200){
                    PendingDeviceResponse defaultResponse = response.body();
                    defaultResponse.setStatusCode("200");
                    pendingdevicesresult.setValue(defaultResponse);
                }else if(response.code() == 401){
                    //pedir autenticacion
                    Settings settings = Settings.listAll(Settings.class).get(0);
                    configuracionRepository.autenticarBanco(settings.getUsername(), settings.getPassword(),
                            settings.getDevice_uuid(), settings.getDevice_id()).enqueue(new Callback<AutenticateResponse>() {
                        @Override
                        public void onResponse(Call<AutenticateResponse> call, Response<AutenticateResponse> response) {
                            if(response.code() == 200){
                                AutenticateResponse defaultResponse = response.body();
                                defaultResponse.setStatus("200");
                                //guardar el token del banco
                                if(!Settings.listAll(Settings.class).isEmpty()){
                                    Settings settings = Settings.listAll(Settings.class).get(0);
                                    settings.setToken(defaultResponse.getToken());
                                    settings.save();
                                    getPendingDevices();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<AutenticateResponse> call, Throwable t) {
                            PendingDeviceResponse defaultResponse = new PendingDeviceResponse();
                            defaultResponse.setStatusCode("400");
                            pendingdevicesresult.postValue(defaultResponse);

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<PendingDeviceResponse> call, Throwable t) {
                DefaultResponse defaultResponse = new DefaultResponse("ERROR", "");
                defaultResponse.setStatus("400");
                listeroResult.postValue(defaultResponse);

            }
        });
    }

    public void getAllColectores(){
        this.configuracionRepository.getAllColectors().enqueue(new Callback<ColectorsResponse>() {
            @Override
            public void onResponse(Call<ColectorsResponse> call, Response<ColectorsResponse> response) {
                if(response.code() == 200){
                    ColectorsResponse defaultResponse = response.body();
                    defaultResponse.setStatusCode("200");
                    colectoresTodos.setValue(defaultResponse);
                }else if(response.code() == 401){
                    //pedir autenticacion
                    Settings settings = Settings.listAll(Settings.class).get(0);
                    configuracionRepository.autenticarBanco(settings.getUsername(), settings.getPassword(),
                            settings.getDevice_uuid(), settings.getDevice_id()).enqueue(new Callback<AutenticateResponse>() {
                        @Override
                        public void onResponse(Call<AutenticateResponse> call, Response<AutenticateResponse> response) {
                            if(response.code() == 200){
                                AutenticateResponse defaultResponse = response.body();
                                defaultResponse.setStatus("200");
                                //guardar el token del banco
                                if(!Settings.listAll(Settings.class).isEmpty()){
                                    Settings settings = Settings.listAll(Settings.class).get(0);
                                    settings.setToken(defaultResponse.getToken());
                                    settings.save();
                                    getPendingDevices();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<AutenticateResponse> call, Throwable t) {
                            PendingDeviceResponse defaultResponse = new PendingDeviceResponse();
                            defaultResponse.setStatusCode("400");
                            pendingdevicesresult.postValue(defaultResponse);

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ColectorsResponse> call, Throwable t) {
                DefaultResponse defaultResponse = new DefaultResponse("ERROR", "");
                defaultResponse.setStatus("400");
                listeroResult.postValue(defaultResponse);

            }
        });
    }

    public MutableLiveData<PendingDeviceResponse> getPendingdevicesresult() {
        return pendingdevicesresult;
    }

    public MutableLiveData<ColectorsResponse> getColectoresTodos() {
        return colectoresTodos;
    }

    public MutableLiveData<DefaultResponse> getColectorResult() {
        return colectorResult;
    }
}