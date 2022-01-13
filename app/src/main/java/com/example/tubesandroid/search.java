package com.example.tubesandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity implements RecyclerViewClickInterface {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<catmodel> catList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        catList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_search);
        swipeRefreshLayout = findViewById(R.id.swf_search);

        generateData();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                recyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, catList.get(position).name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                //Toast.makeText(search.this, newText, Toast.LENGTH_LONG).show();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void generateData() {
        String url = "https://api.thecatapi.com/v1/breeds/";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONArray response) {
                ///Log.d(TAG, "onResponse: " + response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject kucingData = response.getJSONObject(i);
                        String name = kucingData.getString("name");
                        JSONObject image;
                        String url;
                        try {
                            image = kucingData.getJSONObject("image");
                            url = image.getString("url");
                        } catch (JSONException e) {
                            continue;
                        }

                        catmodel cat = new catmodel();
                        cat.name = name;
                        cat.photo = url;
                        catList.add(cat);
                    }
                    recyclerAdapter = new RecyclerAdapter(catList, search.this);
                    recyclerView.setAdapter(recyclerAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(search.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(arrayRequest);
    }


}