package jav.app.diseasedetection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

public class Pain extends AppCompatActivity {

    private RadioButton radioForWhere , radioForPosition, radioforType;
    private RadioGroup groupForWhere, groupForPosition, groupForType;
    ProgressDialog progressBar;

    Intent globalIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain);


        progressBar = new ProgressDialog(this);
        groupForWhere = findViewById(R.id.painWhereGroup);
        groupForPosition = findViewById(R.id.painPositionGroup);
        groupForType = findViewById(R.id.painTypeGroup);

        globalIntent = getIntent();
        String name = globalIntent.getStringExtra("name");
        String age = globalIntent.getStringExtra("age");
        String gender = globalIntent.getStringExtra("gender");
    }

    public void previous(View view) {

        startActivity(new Intent(getApplicationContext(), AboutPatient.class));

    }

    public void next(View view) {

        int selectedIdWhere = groupForWhere.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioForWhere = (RadioButton) findViewById(selectedIdWhere);
        String where = radioForWhere.getText().toString();

        int selectedIdPosition = groupForPosition.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioForPosition = (RadioButton) findViewById(selectedIdPosition);
        String position = radioForPosition.getText().toString();

        int selectedIdType = groupForType.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioforType = (RadioButton) findViewById(selectedIdType);
        String type = radioforType.getText().toString();

        Log.d("Result"," where "+where +" ,position: "+position+ " ,type: "+type);

        retrieveData(where,position,type);



    }


    private void retrieveData(String where, String position, String typee){
        progressBar.setMessage("Fetching Results...");
        progressBar.show();
        Intent intent = new Intent(getApplicationContext(),Fever.class);

        String url= "https://diseasedetectionhospital.000webhostapp.com/painScript.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("pain");

                    if (success.equals("1")){
                        for(int i=0; i<= jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String id = object.getString("S_ID");
                            String type = object.getString("TYPE");
                            String where1 = object.getString("WHERE1");
                            String position1 = object.getString("POSITION");
                            String disease = object.getString("DISEASE");

                            if (where1.equalsIgnoreCase(""+where) && type.equalsIgnoreCase(""+typee)
                            && position1.equalsIgnoreCase(""+position)){
                                Log.d("Result", "Pain disease " + disease);

                                intent.putExtra("name",""+globalIntent.getStringExtra("name"));
                                intent.putExtra("age",""+globalIntent.getStringExtra("age"));
                                intent.putExtra("gender",""+globalIntent.getStringExtra("gender"));
                                intent.putExtra("pain",""+disease);

                                break;

                            }

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
                Toast.makeText(Pain.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}