package com.example.abako_bank;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.BankUser;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.NotificationResponse;
import com.example.abako_bank.api.response.SocketResponse;
import com.example.abako_bank.dialogs.LoginDialogFragment;
import com.example.abako_bank.viewmodels.LoginViewModel;
import com.example.abako_bank.viewmodels.LoginViewModelFactory;
import com.example.abako_bank.websocket.App;
import com.example.abako_bank.websocket.models.SocketEventModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.provider.Settings;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    private LoginViewModel loginViewModel;
    private ProgressBar progressBar;
    private Activity activity;

    public static App app;

    private Context context;

    private int badgeCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);

        activity = this;
        context = getApplicationContext();

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            return;
        }


        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);



        if(loginViewModel.isCreated()){
            //autenticarme
            com.example.abako_bank.models.Settings settings = loginViewModel.getSetting();
            loginViewModel.autenticateBank(settings.getUsername(), settings.getPassword(), settings.getDevice_uuid(), settings.getDevice_id());
            progressBar.setVisibility(View.VISIBLE);
            loginViewModel.getAutenticateResponseLiveData().observe(this, new Observer<AutenticateResponse>() {
                @Override
                public void onChanged(AutenticateResponse defaultResponse) {
                    progressBar.setVisibility(View.INVISIBLE);
                    System.out.println(defaultResponse.getMessage());
                    if(defaultResponse.getStatus().equals("400")){
                        Toast.makeText(getApplicationContext(),defaultResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        activity.finish();
                        System.exit(0);
                    }else if(defaultResponse.getStatus().equals("200")){
                        //todo ok
                        App.onCreate(getApplicationContext());
                        App.setInForeground(true);
                        loginViewModel.getSocketLiveData().connect();
                    }

                }
            });



        }else{
            String finger_print = Build.FINGERPRINT;
            String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            FragmentManager fm = getSupportFragmentManager();
            LoginDialogFragment editNameDialogFragment = LoginDialogFragment.newInstance("Some Title", finger_print, androidID);
            editNameDialogFragment.show(fm, "fragment_login_name");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

        //ActionItemBadge.update(menu.findItem(R.id.action_notification), 2);
        ActionItemBadge.update(this, menu.findItem(R.id.action_notification), getDrawable(R.drawable.ic_baseline_notifications_24), ActionItemBadge.BadgeStyles.RED, badgeCount);
        //esto es para actualizar el badge
        //ActionItemBadge.update(item, badgeCount);


        loginViewModel.getBankRepository().allNotifications("0", "", "");

        loginViewModel.getSocketLiveData().observe(this, new Observer<SocketEventModel>() {
            @Override
            public void onChanged(SocketEventModel socketEventModel) {
                if(socketEventModel.getPayload() != null){
                    badgeCount = (Integer) socketEventModel.getPayload();
                    ActionItemBadge.update(menu.findItem(R.id.action_notification), badgeCount);
                }

            }
        });

        loginViewModel.getListLiveData().observe(this, new Observer<NotificationResponse>() {
            @Override
            public void onChanged(NotificationResponse notificationResponse) {

                if(notificationResponse.getData() != null){
                    badgeCount= notificationResponse.getData().size();
                    ActionItemBadge.update(menu.findItem(R.id.action_notification), badgeCount);
                }

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {

            }
        }
    }

    public void login(String username, String password){
        String finger_print = Build.FINGERPRINT;
        String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        loginViewModel.createBank(username, password, finger_print, androidID);
        progressBar.setVisibility(View.VISIBLE);
        loginViewModel.getDefaultResponseLiveData().observe(this, new Observer<DefaultResponse>() {
            @Override
            public void onChanged(DefaultResponse defaultResponse) {
                progressBar.setVisibility(View.INVISIBLE);
                System.out.println(defaultResponse.getMessage());
                if(defaultResponse.getStatus().equals("400")){
                    Toast.makeText(getApplicationContext(),defaultResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    activity.finish();
                    System.exit(0);

                }else if(defaultResponse.getStatus().equals("200")){
                    BankUser bankUser = defaultResponse.getBankuser();
                    String finger_print = Build.FINGERPRINT;
                    String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    loginViewModel.saveSettings(bankUser.getUsuario(), bankUser.getContrasena(), finger_print, androidID);

                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.setInForeground(false);
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }


}