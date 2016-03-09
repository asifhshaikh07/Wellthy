package com.gmail.asifhshaikh07.wellthywords;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private List<WordItem> worditem;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private final static String JSONURL = "http://appsculture.com/vocab/words.json";
    private final static String IMAGE_URL = "http://appsculture.com/vocab/images/";
    private final static String IMAGE_EXT = ".png";


    WordsDataSource datasource;
    List<WordItem> itemsfromdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        datasource = new WordsDataSource(MainActivity.this);

        datasource.Open();
        itemsfromdb =datasource.findAll();
        if(itemsfromdb.size()== 0){
            new AsyncHttpTask().execute(JSONURL);
        }else {
            progressBar.setVisibility(View.GONE);
            adapter = new MyRecyclerAdapter(MainActivity.this, itemsfromdb);
            mRecyclerView.setAdapter(adapter);
        }
        // Downloading data from below url


    }
    @Override
    protected void onResume() {
        super.onResume();
        datasource.Open();
      //  new AsyncHttpTask().execute(JSONURL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        datasource.Close();
    }



    public class AsyncHttpTask extends AsyncTask <String, Void, Integer>{

        Integer result = 0;
        @Override
        protected void onPreExecute() {
           setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {

            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer integer) {
            // Download complete. Let us update UI
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                itemsfromdb =datasource.findAll();
                adapter = new MyRecyclerAdapter(MainActivity.this, itemsfromdb);
                mRecyclerView.setAdapter(adapter);
            } else if(result == 0) {
                Toast.makeText(MainActivity.this, "Failed to fetch data!!!!!!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void parseResult(String result) {
        try {

            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("words");
            WordItem item = new WordItem();
            worditem = new ArrayList<>();
            long ratiovalue;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);

                ratiovalue=post.optLong("ratio");
                if(ratiovalue >= 0) {
                    item.setId(post.optLong("id"));
                    item.setTitle(post.optString("word"));
                    item.setMeaning(post.optString("meaning"));
                    item.setThumbnail(IMAGE_URL + item.getId() + IMAGE_EXT);
                    datasource.create(item);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

   }


