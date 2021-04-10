package com.example.covidstatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidstatus.Adapters.StateWiseAdapter;
import com.example.covidstatus.Models.StateWiseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StateWiseDataActivity extends AppCompatActivity {
    private RecyclerView rv_state_wise;
    private StateWiseAdapter stateWiseAdapter;
    private ArrayList<StateWiseModel> stateWiseModelArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;


    private String str_state, str_confirmed, str_confirmed_new, str_active, str_active_new, str_recovered, str_recovered_new,
            str_death, str_death_new, str_lastupdatedate;

    private MainActivity activity = new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_wise_data);
        Init();
        FetchStateWiseData();
        getSupportActionBar().setTitle("STATE WISE DATA");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchStateWiseData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });




    }

    private void FetchStateWiseData() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiURL = "https://api.covid19india.org/data.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("statewise");
                            stateWiseModelArrayList.clear();

                            for (int i = 1; i < jsonArray.length() ; i++){
                                JSONObject statewise = jsonArray.getJSONObject(i);

                                //After fetching, storing the data into strings
                                str_state = statewise.getString("state");

                                str_confirmed = statewise.getString("confirmed");
                                str_confirmed_new = statewise.getString("deltaconfirmed");

                                str_active = statewise.getString("active");

                                str_death = statewise.getString("deaths");
                                str_death_new = statewise.getString("deltadeaths");

                                str_recovered = statewise.getString("recovered");
                                str_recovered_new = statewise.getString("deltarecovered");
                                str_lastupdatedate = statewise.getString("lastupdatedtime");

                                //Creating an object of our statewise model class and passing the values in the constructor
                                StateWiseModel stateWiseModel = new StateWiseModel(str_state, str_confirmed, str_confirmed_new, str_active,
                                        str_death, str_death_new, str_recovered, str_recovered_new, str_lastupdatedate);
                                //adding data to our arraylist
                                stateWiseModelArrayList.add(stateWiseModel);
                            }

                            Handler makeDelay = new Handler();
                            makeDelay.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    stateWiseAdapter.notifyDataSetChanged();

                                }
                            }, 1000);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonObjectRequest);


    }

    private void Init() {
        swipeRefreshLayout = findViewById(R.id.activity_state_wise_swipe_refresh_layout);
        rv_state_wise = findViewById(R.id.activity_state_wise_recyclerview);
        rv_state_wise.setHasFixedSize(true);
        rv_state_wise.setLayoutManager(new LinearLayoutManager(this));

        stateWiseModelArrayList = new ArrayList<>();
        stateWiseAdapter = new StateWiseAdapter(StateWiseDataActivity.this, stateWiseModelArrayList);
        rv_state_wise.setAdapter(stateWiseAdapter);
    }
}