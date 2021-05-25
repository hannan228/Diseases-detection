package jav.app.diseasedetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText emailText,passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

    }

    public void login(View view) {

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        if (email.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")){
            startActivity(new Intent( getApplicationContext(), AboutPatient.class ));
            finish();
        }else {
            Toast.makeText(this, "you enter wrong credential", Toast.LENGTH_SHORT).show();
        }

    }


}