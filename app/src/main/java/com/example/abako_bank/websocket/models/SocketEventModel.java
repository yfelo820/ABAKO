package com.example.abako_bank.websocket.models;

import com.example.abako_bank.models.Notification;
import com.example.abako_bank.websocket.App;

public class SocketEventModel extends BaseModel {
    public static final String EVENT_OFFLINE = "offline";
    public static final String EVENT_ERROR = "error";
    public static final String EVENT_MESSAGE = "message";

    //private String mensaje;
    private Integer pendings;
    //private Object notification;

    public SocketEventModel(String message, Object payload) {
        try{
            this.pendings = (Integer) payload;
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }

    }


    public String getEvent() {
        return "mensaje";
    }

    public Object getPayload() {
        return pendings;
    }

    public String getPayloadAsString() {
        String payloadJson = App.getGson().toJson(pendings);
        if (payloadJson.startsWith("{") && payloadJson.endsWith("}"))
            return payloadJson;
        return String.valueOf(pendings);
    }

    public <T>T payloadToJson(Class<T>typeOf) {
        return App.getGson().fromJson(getPayloadAsString(), typeOf);
    }

}
