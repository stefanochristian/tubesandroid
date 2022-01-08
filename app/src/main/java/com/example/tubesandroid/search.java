package com.example.tubesandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class search extends AppCompatActivity {
    public static final String TAG = "TAG";
    FloatingActionButton searchBtn;
    ImageView catImageView, imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchBtn = findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage(getResources().getString(R.string.api_search));

//                if (status == 1) {
//                    imageView.setImageResource(R.drawable.ic_baseline_favorite_24_blacky);
//                    status = 0;
//                }
            }
        });
    }
    public void getImage(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

//        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//        new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                JSONObject kucingData = null;
//                try {
//                    kucingData = response.getJSONObject(0);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                String catUrl = null;
//                try {
//                    catUrl = kucingData.getString("url");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.d(TAG, "onResponse: " + catUrl);
//                Picasso.get().load(catUrl).into(catImageView);
//            }
//        });
    }


}