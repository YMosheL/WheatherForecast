package com.example.moshel.wheatherforecast;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button buttonWeather;
    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpGui();
    }

    private void setUpGui() {
        editText = (EditText)findViewById(R.id.editTextCityName);
        buttonWeather = (Button)findViewById(R.id.buttonWeather);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        forecastAdapter = new ForecastAdapter();

        recyclerView.setAdapter(forecastAdapter);
    }
    private void updateRecyclerAdapter(List<ForecastPrediction>lookupList) {
        forecastAdapter.swapLookup(lookupList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickButton(View view) {(new AsyncWeatherDownloader(new LoadingCallbacks() {
        @Override
        public void onLoad() {

        }

        @Override
        public void onFinishLoading(List<ForecastPrediction> forecastPredictionsList) {
            if(!forecastPredictionsList.isEmpty()){
                Toast.makeText(MainActivity.this,"There is information to be get",Toast.LENGTH_SHORT).show();
                Log.d(TAG,"There is information to be get"+forecastPredictionsList);
                updateRecyclerAdapter(forecastPredictionsList);
            }else {
                Toast.makeText(MainActivity.this, "No definition found", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "No definition found" + forecastPredictionsList);
            }
        }
    })).execute(editText.getText().toString());
    }

    protected static class AsyncWeatherDownloader extends AsyncTask<String, Void, List<ForecastPrediction>> {

        private LoadingCallbacks myLoadingCallbacks;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            myLoadingCallbacks.onLoad();
        }

        public AsyncWeatherDownloader(LoadingCallbacks callback){//constructor
            myLoadingCallbacks = callback;

        }

        @Override
        protected List<ForecastPrediction> doInBackground(String... params) {//this is a forced to implement method because it extends AsyncTask
            List <ForecastPrediction> list = new ArrayList<>();
            URL url = null;
            try {
                url = new URL("https://george-vustrey-weather.p.mashape.com/api.php?location="+params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestProperty("X-Mashape-Key", "Vffxx8oInVmshkY5OL2GnULszZAIp1TWJ6RjsnuyfW451lfGZT");
                connection.setRequestProperty("Accept", "application/json");

                Log.d(TAG, "ready to request data");

                String apiString = readStream(connection.getInputStream());

                Log.d(TAG, "API returned "+apiString);

                JSONObject myJsonObject = new JSONObject(apiString);

                JSONArray myArray =  myJsonObject.getJSONArray("definitions");
                JSONObject item;
                for (int i = 0 ; i <myArray.length(); i++) {


                    item = myArray.getJSONObject(i);

                    list.add(new ForecastPrediction(item.getString("condition"), item.getString("day_of_week"),item.getString("high"), item.getString("low")));
                                    }


                return list;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG,"MalformedURLException "+e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG,"IOException "+ e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG,"JSONException "+ e.getMessage());
            }

            return list;

        }

        @Override
        protected void onPostExecute(List<ForecastPrediction> forecastPredictionsList) {
            super.onPostExecute(forecastPredictionsList);
            Log.d(TAG, "onPostExecute " + forecastPredictionsList);
            myLoadingCallbacks.onFinishLoading(forecastPredictionsList);
        }

        private String readStream(InputStream in) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return stringBuilder.toString();
        }
    }

    public interface LoadingCallbacks{
        public void onLoad();
        public void onFinishLoading(List<ForecastPrediction> forecastPredictionsList);
    }

}
