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
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

private EditText mPhoneNumber,mCode,mName;
private Button mSend,mverify;
int randomNumber;
//private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

   //String mVerificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FirebaseApp.initializeApp(this);

      //  userIsLoggedIn();

        mName = findViewById(R.id.Name);
        mPhoneNumber = findViewById(R.id.phoneNumber);
        mCode = findViewById(R.id.code);
        mSend = findViewById(R.id.Send);
        mverify = findViewById(R.id.verify);


        mSend.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         try {
                                             // Construct data
                                             String apiKey = "apikey=" + "M0Zjbg+6dNE-n5GN0DhRZEeUq5RJOSuLdiDXA8xHMF";
                                             Random random= new Random();
                                             randomNumber=random.nextInt(999999);
                                             String message = "&message=" + " Hey " + mName.getText().toString()+ " Your OTP Is " + randomNumber;
                                             String sender = "&sender=" + "TXTLCL";
                                             String numbers = "&numbers=" +mPhoneNumber.getText().toString();

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
                                                 Toast.makeText(getApplicationContext(),"OTP SENT SUCCESSFULLY",Toast.LENGTH_LONG).show();

                                             }
                                             rd.close();


                                         } catch (Exception e) {
                                             Toast.makeText(getApplicationContext(),"ERROR IN SENDING SMS"+e,Toast.LENGTH_LONG).show();
                                             Toast.makeText(getApplicationContext(),"ERROR"+e,Toast.LENGTH_LONG).show();

                                         }
                                     }
                                 });
        mverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(randomNumber==Integer.valueOf(mCode.getText().toString())){
                    Toast.makeText(getApplicationContext(),"You are loggedin successfully",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                    startActivity(intent);



                }else{
                    Toast.makeText(getApplicationContext(),"You have entered an incorrect OTP",Toast.LENGTH_LONG).show();
                }
            }
        });

        StrictMode.ThreadPolicy st=new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(st);
    }
}
                /*if(mVerificationId != null)
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
//hi*/
