package jav.app.diseasedetection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Diarrhoea extends AppCompatActivity {

    private RadioButton  radioForWithBlood,radioForStoolType;
    private RadioGroup radioGroupWithBlood, radioGroupStoolType;
    ProgressDialog progressBar;
    Intent globalIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarrhoea);

        progressBar = new ProgressDialog(this);
        globalIntent = getIntent();

        radioGroupWithBlood = findViewById(R.id.diarhoeaWithbloodGroup);
        radioGroupStoolType = findViewById(R.id.diarrhoeStoolType);

    }

    public void previous(View view) {
        startActivity(new Intent(getApplicationContext(), Fever.class));
    }

    public void next(View view) {

        int selectedIdWithBlood = radioGroupWithBlood.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioForWithBlood = (RadioButton) findViewById(selectedIdWithBlood);

        String withBlood = radioForWithBlood.getText().toString();



        int selectedIdStool = radioGroupStoolType.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioForStoolType = (RadioButton) findViewById(selectedIdStool);

        String stoolType = radioForStoolType.getText().toString();

        Log.d("Result"," Withblood "+withBlood +" ,Stooltype:"+stoolType);

        retrieveData(withBlood, stoolType);

    }

    private void retrieveData(String withbloodd, String stoolTypee){
        progressBar.setMessage("Fetching Results...");
        progressBar.show();
        Intent intent = new Intent(getApplicationContext(),Symptom4.class);

        String url= "https://diseasedetectionhospital.000webhostapp.com/diarrhoeScript.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("diarrhoea");

                    if (success.equals("1")){
                        for(int i=0; i<= jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("S_ID");
                            String with_blood = object.getString("WITH_BLOOD");
                            String stool_type = object.getString("STOOL_TYPE");
                            String disease = object.getString("DISEASE");

                            if (with_blood.equalsIgnoreCase(""+withbloodd) && stool_type.equalsIgnoreCase(
                                    ""+stoolTypee)){

                                Log.d("Result", "Diarrhoea disease" + disease);

                                intent.putExtra("name",""+globalIntent.getStringExtra("name"));
                                intent.putExtra("age",""+globalIntent.getStringExtra("age"));
                                intent.putExtra("gender",""+globalIntent.getStringExtra("gender"));
                                intent.putExtra("pain",""+globalIntent.getStringExtra("pain"));
                                intent.putExtra("fever",""+globalIntent.getStringExtra("fever"));
                                intent.putExtra("diarrhoea",""+disease);


                                break;

                            }else {

                            }

//                            Log.d("Result"," id: "+id + " type: "+with_blood +" stool type "+stool_type+ " disease" + disease);
                        }
                        progressBar.dismiss();
                        startActivity(intent);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Diarrhoea.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}