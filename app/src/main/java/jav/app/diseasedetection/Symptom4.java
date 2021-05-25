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

public class Symptom4 extends AppCompatActivity {

    private RadioButton radioForNausea , radioForHeadache;
    private RadioGroup groupForNausea, groupForHeadache;
    ProgressDialog progressBar;
    Intent globalIntent;
    String headacheDisease;
    String nausea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom4);

        progressBar = new ProgressDialog(this);
        globalIntent = getIntent();

        groupForNausea = findViewById(R.id.symptomNauseaGroup);
        groupForHeadache = findViewById(R.id.symptomHeadacheGroup);

    }


    public void result(View view) {

        int selectedIdNausea = groupForNausea.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioForNausea = (RadioButton) findViewById(selectedIdNausea);
        nausea = radioForNausea.getText().toString();

        int selectedIdHeadache = groupForHeadache.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioForHeadache = (RadioButton) findViewById(selectedIdHeadache);
        String headache = radioForHeadache.getText().toString();

        retrieveDataHeadache(headache);

        Log.d("Result"," time "+nausea +" ,type:"+headache);

    }

    private void retrieveDataHeadache(String typee){
        progressBar.setMessage("Fetching Results...");
        progressBar.show();

        String url= "https://diseasedetectionhospital.000webhostapp.com/headacheScript.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("headache");

                    if (success.equals("1")){
                        for(int i=0; i<= jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("S_ID");
                            String type = object.getString("TYPE");
                            String disease = object.getString("DISEASE");

                            if (type.equalsIgnoreCase(""+typee)){
                                Log.d("Result", " Headache disease: " + disease);
                                headacheDisease = disease;
                                retrieveDataNausea(nausea);
                                break;

                            }else {

                            }

//                            Log.d("Result"," id: "+id + " type: "+type + "disease" + disease);
                        }
                        progressBar.dismiss();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Symptom4.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void retrieveDataNausea(String typee){
        progressBar.setMessage("Fetching Results...");
        progressBar.show();
        Intent intent = new Intent(getApplicationContext(),Resultt.class);

        String url= "https://diseasedetectionhospital.000webhostapp.com/nauseaScript.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("nausea");

                    if (success.equals("1")){
                        for(int i=0; i<= jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("S_ID");
                            String type = object.getString("TYPE");
                            String disease = object.getString("DISEASE");

                            if (type.equalsIgnoreCase(""+typee)){
                                Log.d("Result","Nausea disease: " + disease);
                                intent.putExtra("name",""+globalIntent.getStringExtra("name"));
                                intent.putExtra("age",""+globalIntent.getStringExtra("age"));
                                intent.putExtra("gender",""+globalIntent.getStringExtra("gender"));
                                intent.putExtra("pain",""+globalIntent.getStringExtra("pain"));
                                intent.putExtra("fever",""+globalIntent.getStringExtra("fever"));
                                intent.putExtra("diarrhoea",""+globalIntent.getStringExtra("diarrhoea"));
                                intent.putExtra("nausea",""+disease);
                                intent.putExtra("headache",""+headacheDisease);
                                break;
                            }else {

                            }

//                            Log.d("Result"," id: "+id + " type: "+type + "disease" + disease);
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
                Toast.makeText(Symptom4.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}