package com.example.abako_bank.api.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.abako_bank.api.BankService;
import com.example.abako_bank.api.request.BankPhoneData;
import com.example.abako_bank.api.request.BlackList;
import com.example.abako_bank.api.request.LimitadosRequest;
import com.example.abako_bank.api.request.NoJueganRequest;
import com.example.abako_bank.api.request.TiroRequest;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.BlackListResponse;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.NotificationResponse;
import com.example.abako_bank.api.response.TiroResponse;
import com.example.abako_bank.api.response.prizes.PrizesColectorResponse;
import com.example.abako_bank.api.response.prizes.PrizesListasResponse;
import com.example.abako_bank.api.response.prizes.PrizesResponse;
import com.example.abako_bank.models.Settings;
import com.example.abako_bank.models.Tiro;
import com.example.abako_bank.models.config.Config_Limitados;
import com.example.abako_bank.models.config.Config_No_Juegan;
import com.example.abako_bank.websocket.utils.DebugUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BankRepository {
    static final String BASE_URL = "http://96.47.228.226:4000/";
    //static final String BASE_URL = "http://192.168.1.103:4000/";
    private BankService bankService;
    private static volatile BankRepository instance;
    private String result = "";

    private MutableLiveData<DefaultResponse> defaultResponseMutableLiveData;

    private MutableLiveData<AutenticateResponse> autenticateResponseMutableLiveData;

    private MutableLiveData<NotificationResponse> listMutableLiveData;

    private MutableLiveData<BlackListResponse> blackListResponseMutableLiveData;

    public BankRepository() {

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

        bankService = retrofit.create(BankService.class);

        defaultResponseMutableLiveData = new MutableLiveData<>();

        autenticateResponseMutableLiveData = new MutableLiveData<>();

        blackListResponseMutableLiveData = new MutableLiveData<>();

        listMutableLiveData = new MutableLiveData<>();

    }

    public static BankRepository getInstance() {
        if (instance == null) {
            instance = new BankRepository();
        }
        return instance;
    }

    public void createBank(String username, String password, String uuid, String imei){

        Call<DefaultResponse> call =  bankService.createBank(new BankPhoneData(username, password, uuid, imei));
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code() == 200){
                    DefaultResponse defaultResponse = response.body();
                    defaultResponse.setStatus("200");
                    defaultResponseMutableLiveData.postValue(defaultResponse);
                }else if(response.code() == 400){
                    DefaultResponse defaultResponse = new DefaultResponse(response.message(), "");
                    defaultResponse.setStatus("400");
                    defaultResponseMutableLiveData.postValue(defaultResponse);

                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                DefaultResponse defaultResponse = new DefaultResponse(t.getMessage(), "");
                defaultResponse.setStatus("400");
                defaultResponseMutableLiveData.postValue(defaultResponse);
            }
        });


    }

    public void autenticateBank(String username, String password, String uuid, String imei){
        Call<AutenticateResponse> call =  bankService.autenticateBank(new BankPhoneData(username, password, uuid, imei));
        call.enqueue(new Callback<AutenticateResponse>() {
            @Override
            public void onResponse(Call<AutenticateResponse> call, Response<AutenticateResponse> response) {
                if(response.code() == 200){
                    AutenticateResponse defaultResponse = response.body();
                    defaultResponse.setStatus("200");
                    //guardar el token del banco
                    DebugUtils.debug(BankRepository.class, defaultResponse.getToken());
                    if(!Settings.listAll(Settings.class).isEmpty()){
                        Settings settings = Settings.listAll(Settings.class).get(0);
                        settings.setToken(defaultResponse.getToken());
                        settings.save();
                    }
                    autenticateResponseMutableLiveData.postValue(defaultResponse);
                }else if(response.code() == 400){
                    AutenticateResponse defaultResponse = new AutenticateResponse("", response.message());
                    defaultResponse.setStatus("400");
                    autenticateResponseMutableLiveData.postValue(defaultResponse);

                }
            }

            @Override
            public void onFailure(Call<AutenticateResponse> call, Throwable t) {
                AutenticateResponse defaultResponse = new AutenticateResponse( "", t.getMessage());
                defaultResponse.setStatus("400");
                autenticateResponseMutableLiveData.postValue(defaultResponse);
            }
        });
    }

    public void allNotifications(String estado, String fecha_desde, String fecha_hasta){

        if(!Settings.listAll(Settings.class).isEmpty()){
            final Settings settings = Settings.listAll(Settings.class).get(0);

            Call<NotificationResponse> call =  bankService.obtenerNotificaciones(settings.getToken(), settings.getDevice_uuid(), estado, fecha_desde, fecha_hasta);
            call.enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                    if(response.code() == 200){
                        NotificationResponse notificationResponse = response.body();
                        notificationResponse.setCode("200");
                        listMutableLiveData.postValue(notificationResponse);
                    }else if(response.code() == 400){
                        DebugUtils.debug(BankRepository.class,"400");
                        NotificationResponse notificationResponse = new NotificationResponse();
                        notificationResponse.setCode("400");
                        listMutableLiveData.postValue(notificationResponse);
                    }else if(response.code() == 401){
                        //ver a q hacer aqui
                        DebugUtils.debug(BankRepository.class,"401");
                        NotificationResponse notificationResponse = new NotificationResponse();
                        notificationResponse.setCode("401");
                        listMutableLiveData.postValue(notificationResponse);
                    }
                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {
                    //error
                    NotificationResponse notificationResponse = new NotificationResponse();
                    notificationResponse.setCode("400");
                    listMutableLiveData.postValue(notificationResponse);
                }
            });
        }

    }

    public void addDeviceToBlackList(String device, String imei){
        final Settings settings = Settings.listAll(Settings.class).get(0);
        Call<BlackListResponse> call =  bankService.adicionarDispABlackList(settings.getToken(), settings.getDevice_uuid(), new BlackList(device, imei));
        call.enqueue(new Callback<BlackListResponse>() {
            @Override
            public void onResponse(Call<BlackListResponse> call, Response<BlackListResponse> response) {
                if(response.code() == 200){
                    BlackListResponse notificationResponse = response.body();
                    notificationResponse.setCode(200);
                    blackListResponseMutableLiveData.postValue(notificationResponse);
                }else if(response.code() == 400){
                    //DebugUtils.debug(BankRepository.class,"400");
                    BlackListResponse notificationResponse = new BlackListResponse();
                    notificationResponse.setMessage("Error: El dispositivo ya está en la lista negra");
                    notificationResponse.setCode(400);
                    blackListResponseMutableLiveData.postValue(notificationResponse);
                }else if(response.code() == 401){
                    //ver a q hacer aqui
                    DebugUtils.debug(BankRepository.class,"401");
                    BlackListResponse notificationResponse = new BlackListResponse();
                    notificationResponse.setCode(401);
                    blackListResponseMutableLiveData.postValue(notificationResponse);
                }
            }

            @Override
            public void onFailure(Call<BlackListResponse> call, Throwable t) {
                //error
                BlackListResponse notificationResponse = new BlackListResponse();
                notificationResponse.setCode(400);
                blackListResponseMutableLiveData.postValue(notificationResponse);
            }
        });
    }

    public Call<DefaultResponse> actualizarEstadoNotificacion(Integer id){
        final Settings settings = Settings.listAll(Settings.class).get(0);
        return bankService.actualizarNotificacion(settings.getToken(), settings.getDevice_uuid(), id);
    }

    public Call<DefaultResponse> sendLimitados(){
        final Settings settings = Settings.listAll(Settings.class).get(0);
        List<Config_Limitados> config_limitados = Config_Limitados.listAll(Config_Limitados.class);

        LimitadosRequest limitadosRequest = new LimitadosRequest(config_limitados.get(0).getLimitados_bola_string(), config_limitados.get(0).getLimitados_parlet_string());

        return bankService.sendLimitados(settings.getToken(), settings.getDevice_uuid(), limitadosRequest);
    }

    public Call<DefaultResponse> sendNoJuegan(){
        final Settings settings = Settings.listAll(Settings.class).get(0);
        List<Config_No_Juegan> config_limitados = Config_No_Juegan.listAll(Config_No_Juegan.class);

        NoJueganRequest limitadosRequest = new NoJueganRequest(config_limitados.get(0).getLimitados_no_juegan_string());

        return bankService.sendNoJuegan(settings.getToken(), settings.getDevice_uuid(), limitadosRequest);
    }

    public Call<PrizesResponse> getPrizes(String tiro_id){
        final Settings settings = Settings.listAll(Settings.class).get(0);

        return bankService.getPrizes(settings.getToken(), settings.getDevice_uuid(), tiro_id);
    }

    public Call<TiroResponse> getPicks(String fecha_desde, String fecha_hasta, boolean estado){
        final Settings settings = Settings.listAll(Settings.class).get(0);

        return bankService.getPicks(settings.getToken(), settings.getDevice_uuid(), fecha_desde, fecha_hasta, estado);
    }

    public Call<PrizesColectorResponse> getPrizeByColector(String colector_id, String tiro_id){
        final Settings settings = Settings.listAll(Settings.class).get(0);

        return bankService.getPrizeByColector(settings.getToken(), settings.getDevice_uuid(), colector_id, tiro_id);
    }

    public Call<PrizesListasResponse> getPrizeListasByLister(String lister_id, String tiro_id){
        final Settings settings = Settings.listAll(Settings.class).get(0);

        return bankService.getPrizeListasByLister(settings.getToken(), settings.getDevice_uuid(), lister_id, tiro_id);
    }

    public Call<DefaultResponse> setPick(TiroRequest tiroRequest){
        final Settings settings = Settings.listAll(Settings.class).get(0);

        return bankService.setPick(settings.getToken(), settings.getDevice_uuid(), tiroRequest);
    }

    public Call<AutenticateResponse> autenticatBankSecondMethod(){
        final Settings settings = Settings.listAll(Settings.class).get(0);

        return bankService.autenticateBank(new BankPhoneData(settings.getUsername(), settings.getPassword(), settings.getDevice_uuid(), settings.getDevice_id()));
    }



    public MutableLiveData<DefaultResponse> getDefaultResponseMutableLiveData() {
        return defaultResponseMutableLiveData;
    }

    public MutableLiveData<NotificationResponse> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public MutableLiveData<AutenticateResponse> getAutenticateResponseMutableLiveData() {
        return autenticateResponseMutableLiveData;
    }

    public MutableLiveData<BlackListResponse> getBlackListResponseMutableLiveData() {
        return blackListResponseMutableLiveData;
    }
}
