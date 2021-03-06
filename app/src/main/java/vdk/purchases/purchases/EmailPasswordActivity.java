package vdk.purchases.purchases;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class EmailPasswordActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETemail;
    private EditText ETpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.signin);

        //InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
       // imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent intent = new Intent(EmailPasswordActivity.this,Profile.class);
                    startActivity(intent);

                } else {
                    // User is signed out

                }

            }
        };

        ETemail = (EditText) findViewById(R.id.email);
        ETpassword = (EditText) findViewById(R.id.password);


        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.sign_up).setOnClickListener(this);

        //переходим сразу во вторую активность, если пользователь уже проходил авторизацию
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            Intent intent = new Intent(EmailPasswordActivity.this, Profile.class);
            startActivity(intent);
        }
    }
//обработка кнопок регистарции и авторизации
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_sign_in)
        {
            if(ETemail.getText().length()==0 || ETpassword.getText().length()==0){
                Toast.makeText(EmailPasswordActivity.this, "Enter email and password", Toast.LENGTH_SHORT).show();
            }
            else{
            signin(ETemail.getText().toString(),ETpassword.getText().toString());}
        }
        else if (view.getId() == R.id.sign_up)
        {
            Intent i = new Intent(EmailPasswordActivity.this, Registration.class);
            startActivity(i);
        }

    }
//авторизация
    public void signin(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(EmailPasswordActivity.this, "Authorization succsessful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EmailPasswordActivity.this, Profile.class);
                    startActivity(i);
                }else
                    Toast.makeText(EmailPasswordActivity.this, "Authorization failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
    //регистрация

    /*public void registration(){
       findViewById(R.id.btn_registration).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(EmailPasswordActivity.this, Registration.class);
               startActivity(i);
           }
       });
    }*/

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

