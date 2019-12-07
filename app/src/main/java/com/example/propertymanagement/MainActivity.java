package com.example.propertymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

private EditText mPhoneNumber,mCode;
private Button mSend;
private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

   String mVerificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        userIsLoggedIn();


        mPhoneNumber = findViewById(R.id.phoneNumber);
        mCode = findViewById(R.id.code);

        mSend = findViewById(R.id.Send);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Construct data
                    String apiKey = "apikey=" + "d3V5v1c+Se0-ue1Vd6wXLUJG2T1e14dQD1A1KYoUYh";
                    String message = "&message=" + mCode.getText().toString();
                    String sender = "&sender=" + "TXTLCL";
                    String numbers = "&numbers=" + mPhoneNumber.getText().toString();

                    // Send data
                    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                    String data = apiKey + numbers + message + sender;
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.getOutputStream().write(data.getBytes("UTF-8"));
                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = rd.readLine()) != null) {
                       Toast.makeText(MainActivity.this,line.toString(),Toast.LENGTH_SHORT).show();
                    }
                    rd.close();


                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();

                }
                if(mVerificationId != null)
                    verifyPhoneNumberWithCode();
                    else
                        startPhoneNumberVerification();

                startPhoneNumberVerification();
            }
        });
        StrictMode.ThreadPolicy st=new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(st);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {


            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);

                mVerificationId = verificationId;
                mSend.setText("Verify Code");
            }
        };
    }
    private void verifyPhoneNumberWithCode(){
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(mVerificationId,mCode.getText().toString());
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    userIsLoggedIn();
            }
        });
    }

    private void userIsLoggedIn() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
            finish();
            return;
        }

    }

    private void startPhoneNumberVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber.getText().toString(),
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);




        }
    }

