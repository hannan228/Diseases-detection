package jav.app.diseasedetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Resultt extends AppCompatActivity {
    Intent globalIntent;

    String name, age,  gender, pain, fever, diarrhoea, nausea, headache;

    TextView nameT, ageT, genderT, painT, feverT, diaarhoeaT, nauseaT, headacheT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultt);

        globalIntent = getIntent();

        nameT = findViewById(R.id.textPatientName);
        ageT = findViewById(R.id.textPatientAge);
        genderT = findViewById(R.id.textGender);
        painT = findViewById(R.id.textPain);
        feverT = findViewById(R.id.textfever);
        diaarhoeaT = findViewById(R.id.textDiarrhoea);
        headacheT = findViewById(R.id.textHeadache);
        nauseaT = findViewById(R.id.textNausea);


         name = globalIntent.getStringExtra("name");
         age = globalIntent.getStringExtra("age");
         gender = globalIntent.getStringExtra("gender");
         pain = globalIntent.getStringExtra("pain");
         fever = globalIntent.getStringExtra("fever");
         diarrhoea = globalIntent.getStringExtra("diarrhoea");
         nausea = globalIntent.getStringExtra("nausea");
         headache = globalIntent.getStringExtra("headache");

         nameT.setText(""+name);
         ageT.setText(""+age);
         genderT.setText(""+gender);
         painT.setText(""+pain);
         feverT.setText(""+fever);
         diaarhoeaT.setText(""+diarrhoea);
         nauseaT.setText(""+nausea);
         headacheT.setText(""+headache);

        Log.d("result",""+name);
        Log.d("result",""+age);
        Log.d("result",""+gender);
        Log.d("result",""+pain);
        Log.d("result",""+fever);
        Log.d("result",""+diarrhoea);
        Log.d("result",""+nausea);
        Log.d("result",""+headache);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_start:
                    startActivity(new Intent(Resultt.this,AboutPatient.class));
                break;

            case R.id.action_signout:
                    startActivity(new Intent(Resultt.this, Login.class));
                    finish();

        }
        return super.onOptionsItemSelected(item);
    }

}