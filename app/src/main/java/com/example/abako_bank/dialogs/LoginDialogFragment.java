package com.example.abako_bank.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.abako_bank.MainActivity;
import com.example.abako_bank.R;

public class LoginDialogFragment extends DialogFragment {



    public LoginDialogFragment() {


    }

    public static LoginDialogFragment newInstance(String title, String finger_print, String androidID) {



        LoginDialogFragment frag = new LoginDialogFragment();

        Bundle args = new Bundle();

        args.putString("title", title);
        args.putString("finger_print", finger_print);
        args.putString("androidID", androidID);

        frag.setArguments(args);

        return frag;

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.login_dialog, container);

    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        final String finger_print = getArguments().getString("finger_print");
        final String androidID = getArguments().getString("androidID");


        // Get field from view
        Button button = view.findViewById(R.id.button);
        final EditText username = view.findViewById(R.id.username);
        final EditText password = view.findViewById(R.id.password);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).login(username.getText().toString(), password.getText().toString());
                dismiss();
            }
        });


        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


    }


}
