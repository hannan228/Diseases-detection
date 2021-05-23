package jav.app.diseasedetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Pain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain);
    }

    public void previous(View view) {

        startActivity(new Intent(getApplicationContext(), AboutPatient.class));

    }

    public void next(View view) {

        startActivity(new Intent(getApplicationContext(), Fever.class));

    }

}