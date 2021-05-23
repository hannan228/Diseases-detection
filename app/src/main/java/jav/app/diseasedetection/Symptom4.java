package jav.app.diseasedetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Symptom4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom4);
    }


    public void result(View view) {
        startActivity(new Intent(getApplicationContext(), Resultt.class));
    }
}