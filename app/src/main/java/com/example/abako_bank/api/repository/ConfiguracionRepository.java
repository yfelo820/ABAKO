package com.example.abako_bank.api.repository;

import com.example.abako_bank.api.ConfiguracionService;
import com.example.abako_bank.api.request.BankPhoneData;
import com.example.abako_bank.api.request.listero.Config;
import com.example.abako_bank.api.request.listero.FullConfig;
import com.example.abako_bank.api.request.listero.ListerConfig;
import com.example.abako_bank.api.request.colector.ColectorConfig;
import com.example.abako_bank.api.request.colector.CObjectOne;
import com.example.abako_bank.api.request.colector.CObjectTwo;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.ColectorsResponse;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.PendingDeviceResponse;
import com.example.abako_bank.models.Dispositivo;
import com.example.abako_bank.models.Settings;
import com.example.abako_bank.models.User;
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
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfiguracionRepository {
    static final String BASE_URL = "http://96.47.228.226:4000/";
    //static final String BASE_URL = "http://192.168.1.103:4000/";
    private ConfiguracionService configuracionService;
    private static volatile ConfiguracionRepository instance;

    public ConfiguracionRepository() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        configuracionService = retrofit.create(ConfiguracionService.class);


    }

    public static ConfiguracionRepository getInstance() {
        if (instance == null) {
            instance = new ConfiguracionRepository();
        }
        return instance;
    }

    public Call<AutenticateResponse> autenticarBanco(String username, String password, String uuid, String imei){
        return configuracionService.autenticateBank(new BankPhoneData(username, password, uuid, imei));
    }

    public Call<DefaultResponse> addConfiguracionListero(Dispositivo dispositivo){
        List<Settings> settings = Settings.listAll(Settings.class);
        ListerConfig listerConfig = new ListerConfig();

        listerConfig.setDispositivo(dispositivo);
        List<Config_User_Pass> config_user_passes = Config_User_Pass.listAll(Config_User_Pass.class);
        if(!config_user_passes.isEmpty()){
            listerConfig.setUsuario(new User(config_user_passes.get(0).getUsername(),config_user_passes.get(0).getPassword(),1));
        }else{//usar por defecto test, testing
            listerConfig.setUsuario(new User("test","testing",1));
        }

        Config config = new Config(config_user_passes.get(0).getNombre(), 0, 0, 0, config_user_passes.get(0).getColector_nombre());

        //buscar config para BOTE tipo_juego 1
        FullConfig fullConfig_bote = new FullConfig();
        fullConfig_bote.setNombre_plantilla("configuraciones");
        fullConfig_bote.setEs_plantilla(false);
        fullConfig_bote.setTipo_juego(1);
        List<Config_Horario_Dia> config_horario_dias = Config_Horario_Dia.find(Config_Horario_Dia.class, "tipojuego = ?", String.valueOf(1));
        if(!config_horario_dias.isEmpty()){
            fullConfig_bote.setHora_activo_dia(config_horario_dias.get(0).getHora_activo()+":"+
                    config_horario_dias.get(0).getMinuto_activo()+":00.000");

            fullConfig_bote.setHora_inactivo_dia(config_horario_dias.get(0).getHora_inactivo()+":"+
                    config_horario_dias.get(0).getMinuto_inactivo()+":00.000");
        }

        List<Config_Horario_Noche> config_horario_noches = Config_Horario_Noche.find(Config_Horario_Noche.class, "tipojuego = ?", String.valueOf(1));
        if(!config_horario_noches.isEmpty()){
            fullConfig_bote.setHora_activo_noche(config_horario_dias.get(0).getHora_activo()+":"+
                    config_horario_dias.get(0).getMinuto_activo()+":00.000");

            fullConfig_bote.setHora_inactivo_noche(config_horario_dias.get(0).getHora_inactivo()+":"+
                    config_horario_dias.get(0).getMinuto_inactivo()+":00.000");
        }

        List<Config_Topes_Lim> config_limitados = Config_Topes_Lim.find(Config_Topes_Lim.class, "tipojuego = ?", String.valueOf(1));
        if(!config_limitados.isEmpty()){
            fullConfig_bote.setTope_bola(config_limitados.get(0).getTope_bola());
            fullConfig_bote.setTope_parlet(config_limitados.get(0).getTope_parlet());
            fullConfig_bote.setTope_centena(config_limitados.get(0).getTope_centena());
        }

        List<Config_Pagos> config_pagos = Config_Pagos.find(Config_Pagos.class, "tipojuego = ?", String.valueOf(1));
        if(!config_pagos.isEmpty()){
            fullConfig_bote.setPago_fijo(config_pagos.get(0).getFijo());
            fullConfig_bote.setPago_centena(config_pagos.get(0).getCentena());
            fullConfig_bote.setPago_corrido(config_pagos.get(0).getCorrido());
            fullConfig_bote.setPago_parlet(config_pagos.get(0).getParlet());
            fullConfig_bote.setPago_parlet_m(config_pagos.get(0).getParlet_m());
        }

        List<Config_Pagos_Limitados> config_pagos_limitados = Config_Pagos_Limitados.find(Config_Pagos_Limitados.class, "tipojuego = ?", String.valueOf(1));
        if(!config_pagos_limitados.isEmpty()){
            fullConfig_bote.setPago_limitado_fijo(config_pagos_limitados.get(0).getFijo());
            fullConfig_bote.setPago_limitado_corrido(config_pagos_limitados.get(0).getCorrido());
            fullConfig_bote.setPago_limitado_parlet(config_pagos_limitados.get(0).getParlet());
        }

        List<Config_Percent_Pago> config_percent_pagos = Config_Percent_Pago.find(Config_Percent_Pago.class, "tipojuego = ?", String.valueOf(1));
        if(!config_percent_pagos.isEmpty()){
            fullConfig_bote.setPago_bola_ave_normal(config_percent_pagos.get(0).getBola_normal());
            fullConfig_bote.setPago_bola_ave_exep(config_percent_pagos.get(0).getBola_no_normal());
            fullConfig_bote.setPago_parlet_ave_normal(config_percent_pagos.get(0).getParlet_normal());
            fullConfig_bote.setPago_parlet_ave_exep(config_percent_pagos.get(0).getParlet_no_normal());
            fullConfig_bote.setPago_centena_ave_normal(config_percent_pagos.get(0).getCentena_normal());
            fullConfig_bote.setPago_centena_ave_exep(config_percent_pagos.get(0).getCentene_no_normal());
        }

        config.addFullConfig(fullConfig_bote);

        /*Configuando charada**/

        FullConfig fullConfig_charada = new FullConfig();
        fullConfig_charada.setNombre_plantilla("configlisteros");
        fullConfig_charada.setEs_plantilla(false);
        fullConfig_charada.setTipo_juego(2);
        List<Config_Horario_Dia> config_horario_dias_charada = Config_Horario_Dia.find(Config_Horario_Dia.class, "tipojuego = ?", String.valueOf(2));
        if(!config_horario_dias_charada.isEmpty()){
            fullConfig_charada.setHora_activo_dia(config_horario_dias_charada.get(0).getHora_activo()+":"+
                    config_horario_dias_charada.get(0).getMinuto_activo()+":00.000");

            fullConfig_charada.setHora_inactivo_dia(config_horario_dias_charada.get(0).getHora_inactivo()+":"+
                    config_horario_dias_charada.get(0).getMinuto_inactivo()+":00.000");
        }

        List<Config_Horario_Noche> config_horario_noches_charada = Config_Horario_Noche.find(Config_Horario_Noche.class, "tipojuego = ?", String.valueOf(2));
        if(!config_horario_noches_charada.isEmpty()){
            fullConfig_charada.setHora_activo_noche(config_horario_noches_charada.get(0).getHora_activo()+":"+
                    config_horario_noches_charada.get(0).getMinuto_activo()+":00.000");

            fullConfig_charada.setHora_inactivo_noche(config_horario_noches_charada.get(0).getHora_inactivo()+":"+
                    config_horario_noches_charada.get(0).getMinuto_inactivo()+":00.000");
        }

        List<Config_Topes_Lim> config_limitados_charada = Config_Topes_Lim.find(Config_Topes_Lim.class, "tipojuego = ?", String.valueOf(2));
        if(!config_limitados_charada.isEmpty()){
            fullConfig_charada.setTope_bola(config_limitados_charada.get(0).getTope_bola());
            fullConfig_charada.setTope_parlet(config_limitados_charada.get(0).getTope_parlet());
            fullConfig_charada.setTope_centena(config_limitados_charada.get(0).getTope_centena());
        }

        List<Config_Pagos> config_pagos_charada = Config_Pagos.find(Config_Pagos.class, "tipojuego = ?", String.valueOf(2));
        if(!config_pagos_charada.isEmpty()){
            fullConfig_charada.setPago_fijo(config_pagos_charada.get(0).getFijo());
            fullConfig_charada.setPago_centena(config_pagos_charada.get(0).getCentena());
            fullConfig_charada.setPago_corrido(config_pagos_charada.get(0).getCorrido());
            fullConfig_charada.setPago_parlet(config_pagos_charada.get(0).getParlet());
            fullConfig_charada.setPago_parlet_m(config_pagos_charada.get(0).getParlet_m());
        }

        List<Config_Pagos_Limitados> config_pagos_limitados_charada = Config_Pagos_Limitados.find(Config_Pagos_Limitados.class, "tipojuego = ?", String.valueOf(1));
        if(!config_pagos_limitados_charada.isEmpty()){
            fullConfig_charada.setPago_limitado_fijo(config_pagos_limitados_charada.get(0).getFijo());
            fullConfig_charada.setPago_limitado_corrido(config_pagos_limitados_charada.get(0).getCorrido());
            fullConfig_charada.setPago_limitado_parlet(config_pagos_limitados_charada.get(0).getParlet());
        }

        List<Config_Percent_Pago> config_percent_pagos_charada = Config_Percent_Pago.find(Config_Percent_Pago.class, "tipojuego = ?", String.valueOf(1));
        if(!config_percent_pagos_charada.isEmpty()){
            fullConfig_charada.setPago_bola_ave_normal(config_percent_pagos_charada.get(0).getBola_normal());
            fullConfig_charada.setPago_bola_ave_exep(config_percent_pagos_charada.get(0).getBola_no_normal());
            fullConfig_charada.setPago_parlet_ave_normal(config_percent_pagos_charada.get(0).getParlet_normal());
            fullConfig_charada.setPago_parlet_ave_exep(config_percent_pagos_charada.get(0).getParlet_no_normal());
            fullConfig_charada.setPago_centena_ave_normal(config_percent_pagos_charada.get(0).getCentena_normal());
            fullConfig_charada.setPago_centena_ave_exep(config_percent_pagos_charada.get(0).getCentene_no_normal());
        }

        config.addFullConfig(fullConfig_charada);

        listerConfig.setConfig(config);


        return configuracionService.crearConfigListero(listerConfig,settings.get(0).getToken(),settings.get(0).getDevice_uuid());
    }


    public Call<DefaultResponse> addConfiguracionColector(Dispositivo dispositivo){
        List<Settings> settings = Settings.listAll(Settings.class);
        ColectorConfig colectorConfig = new ColectorConfig();

        colectorConfig.setDispositivo(dispositivo);
        List<Config_User_Pass> config_user_passes = Config_User_Pass.listAll(Config_User_Pass.class);
        if(!config_user_passes.isEmpty()){
            colectorConfig.setUsuario(new User(config_user_passes.get(0).getUsername(),config_user_passes.get(0).getPassword(),2));
        }else{//usar por defecto test, testing
            colectorConfig.setUsuario(new User("test","testing",2));
        }

        CObjectTwo CObjectTwo = new CObjectTwo();

        List<Config_Dias_Inactivo> config_dias_inactivos = Config_Dias_Inactivo.listAll(Config_Dias_Inactivo.class);
        if(!config_dias_inactivos.isEmpty()){
            CObjectTwo.setDias_inactividad(config_dias_inactivos.get(0).getDias_inactivo());
            CObjectTwo.setEs_plantilla(0);
        }else{//usar por defecto test, testing
            CObjectTwo.setDias_inactividad(3);
            CObjectTwo.setEs_plantilla(0);
        }

        CObjectOne config = new CObjectOne(config_user_passes.get(0).getNombre(), 0, 0, 0, CObjectTwo);


        colectorConfig.setConfig(config);


        return configuracionService.crearConfigColector(colectorConfig,settings.get(0).getToken(),settings.get(0).getDevice_uuid());
    }

    public Call<PendingDeviceResponse> getAllPendingDevices(){
        final Settings settings = Settings.listAll(Settings.class).get(0);
        return configuracionService.dispositivosPendientes(settings.getToken(), settings.getDevice_uuid());

    }

    public Call<ColectorsResponse> getAllColectors(){
        final Settings settings = Settings.listAll(Settings.class).get(0);
        return configuracionService.todosColectores(settings.getToken(), settings.getDevice_uuid());

    }

}
