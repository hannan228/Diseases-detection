package jav.app.diseasedetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Diarrhoea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarrhoea);



    }

    public void previous(View view) {
        startActivity(new Intent(getApplicationContext(), Fever.class));
    }

    public void next(View view) {
        startActivity(new Intent(getApplicationContext(),Symptom4.class));
    }

}