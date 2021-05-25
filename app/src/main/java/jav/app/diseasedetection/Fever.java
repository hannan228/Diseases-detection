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


public class Fever extends AppCompatActivity {

    private RadioButton radioForTime , radioForType, radioforShivering;
    private RadioGroup groupForTime, groupForType, groupForShivering;
    ProgressDialog progressBar;

    Intent globalIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fever);

        progressBar = new ProgressDialog(this);

        globalIntent = getIntent();

        groupForTime = findViewById(R.id.feverTimeGroup);
        groupForType = findViewById(R.id.feverTypeGroup);
        groupForShivering = findViewById(R.id.feverShiveringGroup);


    }


    public void previous(View view) {
        startActivity(new Intent(getApplicationContext(), Pain.class));
    }

    public void next(View view) {

        int selectedIdTime = groupForTime.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioForTime = (RadioButton) findViewById(selectedIdTime);
        String time = radioForTime.getText().toString();

        int selectedIdType = groupForType.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioForType = (RadioButton) findViewById(selectedIdType);
        String type = radioForType.getText().toString();

        int selectedIdShivering = groupForShivering.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioforShivering = (RadioButton) findViewById(selectedIdShivering);
        String shivering = radioforShivering.getText().toString();

        Log.d("Result"," time "+time +" ,type:"+type+ " ,shiveing"+shivering);
        retrieveData(time,type,shivering);


    }

    private void retrieveData(String timee, String typee, String shiveringg  ){
        progressBar.setMessage("Fetching Results...");
        progressBar.show();

        Intent intent = new Intent(getApplicationContext(), Diarrhoea.class);

        String url= "https://diseasedetectionhospital.000webhostapp.com/feverScript.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("fever");

                    if (success.equals("1")){
                        for(int i=0; i<= jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("S_ID");
                            String time = object.getString("TIME");
                            String type = object.getString("TYPE");
                            String shivering = object.getString("SHIVERING");
                            String disease = object.getString("DISEASE");

                            if (time.equalsIgnoreCase(""+timee) && type.equalsIgnoreCase(""+typee)
                            && shivering.equalsIgnoreCase(""+shiveringg)){
                                Log.d("Result", " Fever disease: " + disease);

                                intent.putExtra("name",""+globalIntent.getStringExtra("name"));
                                intent.putExtra("age",""+globalIntent.getStringExtra("age"));
                                intent.putExtra("gender",""+globalIntent.getStringExtra("gender"));
                                intent.putExtra("pain",""+globalIntent.getStringExtra("pain"));
                                intent.putExtra("fever",""+disease);

                                break;

                            }else {

                            }

//                            Log.d("Result"," id: "+id +" time: "+time+ " type: "+type +" shivering "+shivering+ " disease" + disease);
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
                Toast.makeText(Fever.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}