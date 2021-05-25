package jav.app.diseasedetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AboutPatient extends AppCompatActivity {
    Spinner spin;
    EditText name,age;

    String spin_val;
    String gender2;
    String[] gender = { "Male", "Female" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_patient);

        name = findViewById(R.id.name);
        age = findViewById(R.id.ageText);
        spin = (Spinner) findViewById(R.id.spinner_id);//fetching view's id

//Register a callback to be invoked when an item in this AdapterView has been selected

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> arg0, View arg1, int  position, long id) {

                // TODO Auto-generated method stub

                spin_val = gender[position];//saving the value selected
                Log.d("spinner valuee: ",""+spin_val.toString());
                gender2 = spin_val.toString();

            }

            @Override

            public void onNothingSelected(AdapterView<?> arg0) {
                gender2 = "male";
                // TODO Auto-generated method stub
            }

        });

//setting array adaptors to spinners

//ArrayAdapter is a BaseAdapter that is backed by an array of arbitrary objects

        ArrayAdapter<String> spin_adapter = new ArrayAdapter<String>(AboutPatient.this, android.R.layout.simple_spinner_item, gender);

// setting adapters to spinners

        spin.setAdapter(spin_adapter);


    }

    public void next(View view) {

        String names = name.getText().toString();
        String ages = age.getText().toString();

        if (! (names.isEmpty() && ages.isEmpty())){

            Intent intent = new Intent(getApplicationContext(),Pain.class);
            intent.putExtra("name",""+names);
            intent.putExtra("age",""+ages);
            intent.putExtra("gender",""+gender2);

        startActivity(intent);

        }else {
            Toast.makeText(AboutPatient.this, "Please fill these credentials", Toast.LENGTH_SHORT).show();
        }


    }
}
