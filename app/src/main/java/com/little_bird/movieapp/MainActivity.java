package com.little_bird.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edtNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView yxyy = findViewById(R.id.txt);
        callPopUpSignIn();
    }

    private void callPopUpSignIn (){
        Dialog mdialog = new Dialog(this);
        mdialog.setContentView(R.layout.pop_up_sign_in);
        Button btnContinue = findViewById(R.id.btnContinue);

        edtNumber = findViewById(R.id.edtNumber);
        mdialog.show();

        ImageView img = findViewById(R.id.btnClose);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Sms.class).putExtra(edtNumber.getText().toString().trim(),"User_Number"));
            }
        });

    }


}