package com.example.ticketmasterapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class EventInfoActivity extends AppCompatActivity {
    public ImageView ivEventImg;
    public TextView tvTitleEvent;
    public TextView tvDescriptionEvent;
    public Button btnBack;

    private String imgURL;


    /**
     * 1.) Retrieve the event description and poster URL from the intent extras
     * 2.) Set up the ImageView, TextView, and Button
     * 3.) Set up Button so that when it's clicked the user is taken back to the start page
     * 4.) Set the TextView to the description that was passed in
     * 5.) Execute a DownloadImageTask with the poster URL that was passed in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_info);

        Bundle extras = getIntent().getExtras();

        String title = extras.getString("name");
        String description = extras.getString("description");
        imgURL = extras.getString("url");

        ivEventImg = (ImageView) findViewById(R.id.ivEventImg);
        tvTitleEvent = (TextView) findViewById(R.id.tvTitleEvent);
        tvDescriptionEvent = (TextView) findViewById(R.id.tvDescriptionEvent);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        tvDescriptionEvent.setText(description);
        tvTitleEvent.setText(title);
        new DownloadImageTask(ivEventImg).execute(imgURL);
    }

    /**
     * 1.) Retrieve the image from the URL as an InputStream
     * 2.) Use a BitmapFactory to decode the InputStream
     * 3.) Set ImageView to the Bitmap
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
