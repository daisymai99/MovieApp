package com.little_bird.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import static android.content.ContentValues.TAG;

public class Sms extends AppCompatActivity {

    PinView pinView;
    FirebaseAuth mAuth;
    String getNumberUser,SmsID, SmsCode;
    TextView _numberUser, txtResendSms;
    Button btnConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        addview();
        mAuth = FirebaseAuth.getInstance();

        getNumberUser = getIntent().getStringExtra("User_Number");
        _numberUser.setText(getNumberUser);

        addEvents();
        sendVertificationCode(getNumberUser);

        Log.d("sms",getNumberUser+"");
        FirebaseAuth.getInstance().getFirebaseAuthSettings().forceRecaptchaFlowForTesting(true);

    }

    void addview(){
        pinView = findViewById(R.id.pinView);
        _numberUser = findViewById(R.id.txtNumberUser);
        btnConfirm = findViewById(R.id.btnConfirm);
        txtResendSms = findViewById(R.id.txtResendSms);
//        btnBack = findViewById(R.id.btnBack);
    }

    void addEvents(){

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Sms.super.onBackPressed();
//            }
//        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = pinView.getText().toString();
                if (data.equals(SmsCode)) {

                    verifySmsCode(data, getNumberUser);
                    startActivity(new Intent(getApplicationContext(),SetPassword.class).putExtra("sdt",data));

                } else {
                    Toast.makeText(Sms.this, "Mã xác nhận không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    // verify with sms code from firebase
    private void verifySmsCode(String code, String phoneNumber){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(SmsID,code);
        signInWithPhoneAuthCredential(credential,phoneNumber);
    }


    // getting otp on user phone
    private void sendVertificationCode(String phone){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+getNumberUser)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallback)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // events when you add smsCode on PinView
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            SmsID = s;

            Log.d("xxxxxxxxxxxxxx", "send success");
        }


        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            SmsCode = phoneAuthCredential.getSmsCode();

            if(SmsCode != null){
                pinView.setText(SmsCode);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d("xxxxxxxxxxxxxx", e.toString());

            Toast.makeText(Sms.this, "Đã xảy ra lỗi với mã xác nhận của bạn" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    };


    // inside this method we are checking if the code entered is correct or not.
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential,String phone){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            startActivity(new Intent(getApplicationContext(),SetPassword.class));
                        }
                        else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());


                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(Sms.this, "Mã xác nhận không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


    }
}