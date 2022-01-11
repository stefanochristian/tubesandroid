package com.example.tubesandroid;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    FloatingActionButton infoBtn, searchBtn, refreshBtn, favoriteBtn;
    ImageView catImageView, imageView;
    public int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoBtn = findViewById(R.id.infoBtn);
        searchBtn = findViewById(R.id.searchBtn);
        refreshBtn = findViewById(R.id.refreshBtn);
        favoriteBtn = findViewById(R.id.favoriteBtn);
        imageView = findViewById(R.id.imageView);

        catImageView = findViewById(R.id.kucing);

//        getImage(getResources().getString(R.string.api_kucing));

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage(getResources().getString(R.string.api_kucing));

                if (status == 1) {
                    imageView.setImageResource(R.drawable.ic_baseline_favorite_24_blacky);
                    status = 0;
                }
            }
        });

//        BottomNavigationView btnNav = findViewById(R.id.bottomNavigationView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (status == 0) {
                    imageView.setImageResource(R.drawable.ic_baseline_favorite_24);
                    status = 1;
                } else {
                    imageView.setImageResource(R.drawable.ic_baseline_favorite_24_blacky);
                    status = 0;
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearch();
            }
        });

    }

    public void getImage(String url) {
        // mengextract data json

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ///Log.d(TAG, "onResponse: " + response.toString());
                try {

                    JSONObject kucingData = response.getJSONObject(0);
                    String catUrl = kucingData.getString("url");
                    Log.d(TAG, "onResponse: " + catUrl);
                    Picasso.get().load(catUrl).into(catImageView);

                    searchBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openSearch();
                        }
                    });

                    infoBtn.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View v) {
                            try {
                                JSONArray breedsInfo = kucingData.getJSONArray("breeds");
                                if (breedsInfo.isNull(0)) {
                                    Toast.makeText(MainActivity.this, "Data Not Found.", Toast.LENGTH_SHORT).show();
                                } else {
                                    JSONObject breedsData = breedsInfo.getJSONObject(0);
                                    Intent i = new Intent(getApplicationContext(), IDNA.Info.class);
                                    i.putExtra("name", breedsData.getString("name"));
                                    i.putExtra("origin", breedsData.getString("origin"));
                                    i.putExtra("desc", breedsData.getString("description"));
                                    i.putExtra("temp", breedsData.getString("temperament"));
                                    i.putExtra("wikiUrl", breedsData.getString("wikipedia_url"));
                                    i.putExtra("moreLink", breedsData.getString("vcahospitals_url"));
                                    i.putExtra("imageUrl", catUrl);
                                    startActivity(i);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    favoriteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openFavorite();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(arrayRequest);

    }

    public void openFavorite() {
        Intent intent = new Intent(this, favorites.class);
        startActivity(intent);
    }

    public void openSearch() {
        Intent intent = new Intent(this, search.class);
        startActivity(intent);
    }
}