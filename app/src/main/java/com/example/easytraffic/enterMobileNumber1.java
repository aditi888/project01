package com.example.easytraffic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class enterMobileNumber1 extends AppCompatActivity {
    EditText enternumber;
    Button getotpbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enternumber = findViewById(R.id.input_mobile_number);
        getotpbutton = findViewById(R.id.buttonGetOtp);

        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (!enternumber.getText().toString().trim().isEmpty()) {

                        if ((enternumber.getText().toString().trim()).length() == 10) {

                              progressBar.setVisibility(View.VISIBLE);
                              getotpbutton.setVisibility(View.INVISIBLE);

                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+91" + enternumber.getText().toString(),
                                    60,
                                    TimeUnit.SECONDS,
                                    enterMobileNumber1.this,



                                  new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                            progressBar.setVisibility(View.GONE);
                                            getotpbutton.setVisibility(View.VISIBLE);

                                            Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                           startActivity(intent);

                                           // signInWithCredential(phoneAuthCredential);
//                                            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential);

                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            progressBar.setVisibility(View.GONE);
                                            getotpbutton.setVisibility(View.VISIBLE);
                                            Toast.makeText(enterMobileNumber1.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                           // super.onCodeSent(s, forceResendingToken);
                                            progressBar.setVisibility(View.GONE);
                                            getotpbutton.setVisibility(View.VISIBLE);
                                            Intent intent = new Intent(getApplicationContext(), verifyotp2.class);
                                            intent.putExtra("mobile", enternumber.getText().toString());
                                            intent.putExtra("backendotp",backendotp);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                            );
//

                        } else {
                            Toast.makeText(enterMobileNumber1.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(enterMobileNumber1.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}