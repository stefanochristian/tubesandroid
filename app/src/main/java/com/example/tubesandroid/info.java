package com.example.tubesandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class info extends Activity {

    ImageView catImage;
    TextView catName,catOrigin,catDesc,catTemp;
    Button wikiInfo,moreInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent data = getIntent();

        catImage = findViewById(R.id.catImage);
        catName = findViewById(R.id.catName);
        catOrigin = findViewById(R.id.catOrigin);
        catDesc = findViewById(R.id.catDescription);
        catTemp = findViewById(R.id.catTemperament);

        wikiInfo = findViewById(R.id.wikiBtn);
        moreInfo = findViewById(R.id.moreInfoBtn);

        // menyimpan data untuk ke ui

        catName.setText(data.getStringExtra("name"));
        catOrigin.setText(data.getStringExtra("origin"));
        catDesc.setText(data.getStringExtra("desc"));
        catTemp.setText(data.getStringExtra("temp"));

        Picasso.get().load(data.getStringExtra("imageUrl")).into(catImage);

        wikiInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri catUri = Uri.parse(data.getStringExtra("wikiUrl"));
                Intent browser = new Intent(Intent.ACTION_VIEW,catUri);
                startActivity(browser);
            }
        });

        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri catUri = Uri.parse(data.getStringExtra("moreLink"));
                Intent browser = new Intent(Intent.ACTION_VIEW,catUri);
                startActivity(browser);
            }
        });

    }
}