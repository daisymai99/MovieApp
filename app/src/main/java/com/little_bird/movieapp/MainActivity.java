package com.little_bird.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        callPopUpSignIn();
    }

    private void callPopUpSignIn (){
        Dialog mdialog = new Dialog(this);
        mdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mdialog.setContentView(R.layout.pop_up_sign_in);
        mdialog.setCancelable(true);


        Button btnContinue = mdialog.findViewById(R.id.btnContinue);
        EditText edtNumber = mdialog.findViewById(R.id.edtNumber);
        ImageButton btnClose =(ImageButton) mdialog.findViewById(R.id.btnClose);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"dialog",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Sms.class).putExtra("User_Number",edtNumber.getText().toString().trim()));
            }
        });
        mdialog.show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mdialog.dismiss();
            }
        }, 10000);
    }


}