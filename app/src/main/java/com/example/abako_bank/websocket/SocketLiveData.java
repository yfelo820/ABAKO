package com.example.abako_bank.websocket;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.abako_bank.R;
import com.example.abako_bank.websocket.models.SocketEventModel;
import com.example.abako_bank.websocket.utils.DebugUtils;
import com.example.abako_bank.websocket.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicBoolean;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketLiveData extends LiveData<SocketEventModel> {
    private static SocketLiveData instance = new SocketLiveData();
    private static AtomicBoolean disconnected = new AtomicBoolean(false);
    private Socket mSocket;

    private SocketLiveData() {

    }

    public static SocketLiveData get() {
        return instance;
    }

    @Override
    protected synchronized void onActive() {
        super.onActive();
        connect();
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super SocketEventModel> observer) {
        super.observe(owner, observer);
        DebugUtils.debug(SocketLiveData.class, "Observing");
        connect();
    }

    public synchronized void connect() {
        try {
            DebugUtils.debug(SocketLiveData.class, "Attempting to connect");
            if (disconnected.get() == false) {

                DebugUtils.debug(SocketLiveData.class, "Connecting...");

                mSocket = IO.socket("http://96.47.228.226:4000");

                mSocket.on(Socket.EVENT_CONNECT, onConnected);
                mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onSocketConnectTimeout);
                mSocket.on(Socket.EVENT_DISCONNECT, onSocketDisconnect);
                mSocket.on("notification", onNewMessage);

                mSocket.connect();
                disconnected.set(false);

            }
        }catch (Exception ex) {
            //error de coneccion
            disconnected.set(true);
            DebugUtils.debug(SocketLiveData.class, ex.getMessage());
            postValue(new SocketEventModel(SocketEventModel.EVENT_ERROR, ex.getMessage()));

            SocketReconnectionScheduler.schedule();//para reiniciar conx
            ex.printStackTrace();
        }
    }

    public void sendEvent(SocketEventModel eventModel) {
        if (mSocket == null)return;
        setData(eventModel);
        mSocket.send(eventModel.toString());
    }

    public void setData(SocketEventModel socketEventModel) {
        if (socketEventModel == null || TextUtils.isEmpty(socketEventModel.getEvent())) return;
        postValue(socketEventModel);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            handleEvent(args[0].toString());
        }
    };

    private Emitter.Listener onSocketConnectTimeout = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            disconnected.set(true);
            DebugUtils.debug(SocketLiveData.class, "Connection Time out");
            postValue(new SocketEventModel(SocketEventModel.EVENT_OFFLINE, "Connection Time out"));
            SocketReconnectionScheduler.schedule();//para reiniciar conx
        }
    };

    private Emitter.Listener onConnected = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            DebugUtils.debug(SocketLiveData.class, "Conectado....");
            disconnected.set(false);
        }
    };

    private Emitter.Listener onSocketDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            disconnected.set(true);
            DebugUtils.debug(SocketLiveData.class, "Desconectado");
            postValue(new SocketEventModel(SocketEventModel.EVENT_OFFLINE, "Desconectado"));
            SocketReconnectionScheduler.schedule();//para reiniciar conx
        }
    };


    private synchronized void handleEvent(String message){
        try {
            SocketEventModel eventModel = SocketEventModel.fromJson(message, SocketEventModel.class);
            DebugUtils.debug(SocketLiveData.class, "Handling event: "+message);
            if (TextUtils.isEmpty(eventModel.getEvent()))
                throw new Exception("Invalid event model");
            processEvent(eventModel);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private synchronized void processEvent(SocketEventModel eventModel) throws Exception{
        DebugUtils.debug(SocketLiveData.class, "Processing event: "+eventModel.toString());
        postValue(eventModel);

    }
    @Override
    protected void onInactive() {
        super.onInactive();
        disconnect();
        DebugUtils.debug(SocketLiveData.class, "Inactive. Has observers observers? "+hasActiveObservers());
    }

    public boolean isDisconnected() {
        return disconnected.get();
    }

    public void disconnect() {
        if (!hasActiveObservers())
            if (mSocket != null) {
                mSocket.close();
                mSocket.off("notification", onNewMessage);
            }
    }

}