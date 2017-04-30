package com.andura.campina.launch;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andura.campina.R;
import com.andura.campina.image.Image;
import com.andura.campina.main.MainActivity;
import com.andura.campina.model.GDP;
import com.andura.campina.model.Vegetation;
import com.andura.campina.repository.ImagemRepository;
import com.andura.campina.util.AppVar;
import com.andura.campina.util.Util;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

/**
 * Created by amaro on 03/12/16.
 */

public class SplashScreenActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    NumberProgressBar progressBar;
    AsyncHttpClient client;
    GoogleApiClient mGoogleApiClient;
    int increment;
    double lat;
    double lgn;

    private static String[] PERMISSIONS_GPS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 2;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        increment = 0;
        progressBar = (NumberProgressBar) findViewById(R.id.number_progress_bar);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, PERMISSIONS_GPS,
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
        }else{

            if(mGoogleApiClient.isConnected()){
                //downloadData();
            }

        }

        TextView title =(TextView) findViewById(R.id.title);
        Typeface typeFace= Typeface.createFromAsset(getAssets(),"fonts/Sho-CardCapsNF.ttf");
        title.setTypeface(typeFace);


    }

    private void downloadData(){
        if(Util.checkInternetConnection()) {

            ImagemRepository repository = ImagemRepository.getInstance();
            repository.clean();

            CityTask city = new CityTask("2017","http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lgn);
            city.execute();

            VegetationTask veg = new VegetationTask("2016","https://stormy-springs-29754.herokuapp.com/cities/261160/vegetation-index?format=json");
            veg.execute();

            GDPTask gdp = new GDPTask("2016","https://stormy-springs-29754.herokuapp.com/cities/261160/gdp?format=json");
            gdp.execute();



            BackgroundLoginTask task = new BackgroundLoginTask("2017","https://api.nasa.gov/planetary/earth/imagery?lon="+lgn+"&lat="+lat+"&date=2017-04-01&cloud_score=True&api_key=xK1JkszDw0Y5kuwOn0BABXGxamiJFhLlNPv6uIbO");
            //task.execute();

            BackgroundLoginTask task2 = new BackgroundLoginTask("2016","https://api.nasa.gov/planetary/earth/imagery?lon="+lgn+"&lat="+lat+"&date=2016-11-20&cloud_score=True&api_key=xK1JkszDw0Y5kuwOn0BABXGxamiJFhLlNPv6uIbO");
            task2.execute();

            BackgroundLoginTask task4 = new BackgroundLoginTask("2015","https://api.nasa.gov/planetary/earth/imagery?lon="+lgn+"&lat="+lat+"&date=2015-04-10&cloud_score=True&api_key=xK1JkszDw0Y5kuwOn0BABXGxamiJFhLlNPv6uIbO");
            task4.execute();

            BackgroundLoginTask task3 = new BackgroundLoginTask("2014","https://api.nasa.gov/planetary/earth/imagery?lon="+lgn+"&lat="+lat+"&date=2014-04-05&cloud_score=True&api_key=xK1JkszDw0Y5kuwOn0BABXGxamiJFhLlNPv6uIbO");
            task3.execute();
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PROGRESS", "SEM PERMISSAO");
            return;
        }

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

            lat = mLastLocation.getLatitude();
            lgn = mLastLocation.getLongitude();

            Log.d(AppVar.DEBUG,"lat "+lat+" / lng "+lgn);

            downloadData();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class BackgroundLoginTask extends AsyncTask<Void, Void, Boolean> {

        private ProgressDialog dialog;
        private String url;
        private String year;
        private ImagemRepository repository;



        public BackgroundLoginTask(String year, String url){
            this.url = url;
            this.year = year;
        }

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(SplashScreenActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Acessando...");
            dialog.setMessage("Espere um pouco...");
            dialog.setIndeterminate(true);
            //dialog.show();

            repository = ImagemRepository.getInstance();


        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Util.trustEveryone();
            final String USER_AGENT = "Mozilla/5.0";

            try {
                //String url = "https://api.nasa.gov/planetary/earth/imagery?lon=-40.501841&lat=-7.584989&date=2014-08-01&cloud_score=True&api_key=xK1JkszDw0Y5kuwOn0BABXGxamiJFhLlNPv6uIbO";

                URL obj = new URL(url);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                //add reuqest header
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

                int responseCode = con.getResponseCode();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Log.d(AppVar.DEBUG,"RESULT : "+response.toString());

                if (responseCode == 200) {

                    JSONObject x = new JSONObject(response.toString());

                    Image image = new Image(year,x.getString("url"));

                    repository.save(image);


                    return true;

                }

                return false;


            }catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (ProtocolException e) {
                e.printStackTrace();
                return false;
            }  catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean parsingError) {

            increment = increment + 10;
            progressBar.setProgress(increment);

            if(increment == 60 && parsingError == true){

                if(parsingError){
                Intent it = new Intent();
                it.setClass(SplashScreenActivity.this, MainActivity.class);
                startActivity(it);

                SplashScreenActivity.this.finish();}else{
                    Toast.makeText(SplashScreenActivity.this,"Error to Download Images from Nasa.",Toast.LENGTH_LONG).show();
                }
            }

        }

        @Override
        protected void onCancelled() {

            if(dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    private class CityTask extends AsyncTask<Void, Void, Boolean> {

        private ProgressDialog dialog;
        private String url;
        private String year;
        private ImagemRepository repository;


        public CityTask(String year, String url) {
            this.url = url;
            this.year = year;
        }

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(SplashScreenActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Acessando...");
            dialog.setMessage("Espere um pouco...");
            dialog.setIndeterminate(true);
            //dialog.show();

            repository = ImagemRepository.getInstance();

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Util.trustEveryone();
            final String USER_AGENT = "Mozilla/5.0";

            try {
                //String url = "https://api.nasa.gov/planetary/earth/imagery?lon=-40.501841&lat=-7.584989&date=2014-08-01&cloud_score=True&api_key=xK1JkszDw0Y5kuwOn0BABXGxamiJFhLlNPv6uIbO";

                URL obj = new URL(url);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                //add reuqest header
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

                int responseCode = con.getResponseCode();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Log.d(AppVar.DEBUG, "RESULT : " + response.toString());

                if (responseCode == 200) {

                    JSONObject x = new JSONObject(response.toString());

                    JSONArray result = x.getJSONArray("results");

                    JSONObject adress = result.getJSONObject(0);

                    JSONArray adress2 = adress.getJSONArray("address_components");

                    JSONObject data = adress2.getJSONObject(3);

                    repository.setCity(data.getString("long_name"));


                    return true;

                }

                return false;


            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (ProtocolException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean parsingError) {

            increment = increment + 10;
            progressBar.setProgress(increment);

            WikiTask wiki = new WikiTask(repository.getCity());
            wiki.execute();

        }

    }

    private class VegetationTask extends AsyncTask<Void, Void, Boolean> {

            private ProgressDialog dialog;
            private String url;
            private String year;
            private ImagemRepository repository;


            public VegetationTask(String year, String url) {
                this.url = url;
                this.year = year;
            }

            @Override
            protected void onPreExecute() {

                dialog = new ProgressDialog(SplashScreenActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setTitle("Acessando...");
                dialog.setMessage("Espere um pouco...");
                dialog.setIndeterminate(true);
                //dialog.show();

                repository = ImagemRepository.getInstance();

            }

            @Override
            protected Boolean doInBackground(Void... params) {

                Util.trustEveryone();
                final String USER_AGENT = "Mozilla/5.0";

                try {
                    //String url = "https://api.nasa.gov/planetary/earth/imagery?lon=-40.501841&lat=-7.584989&date=2014-08-01&cloud_score=True&api_key=xK1JkszDw0Y5kuwOn0BABXGxamiJFhLlNPv6uIbO";

                    URL obj = new URL(url);

                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                    //add reuqest header
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", USER_AGENT);
                    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                    con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

                    int responseCode = con.getResponseCode();

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    Log.d(AppVar.DEBUG, "RESULT : " + response.toString());

                    if (responseCode == 200) {

                        JSONArray result = new JSONArray(response.toString());

                        for (int i = 0; i < result.length(); i++) {

                            JSONObject object = result.getJSONObject(i);

                            Vegetation veg = new Vegetation();
                            veg.setYear(object.getString("year"));
                            veg.setVegetation(object.getDouble("value"));

                            repository.save(veg);

                        }


                        return true;

                    }

                    return false;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return false;
                } catch (ProtocolException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return false;
            }

            @Override
            protected void onPostExecute(Boolean parsingError) {

                increment = increment + 10;
                progressBar.setProgress(increment);

                WikiTask wiki = new WikiTask(repository.getCity());
                //wiki.execute();

            }

            @Override
            protected void onCancelled() {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        }

    private class GDPTask extends AsyncTask<Void, Void, Boolean> {

        private ProgressDialog dialog;
        private String url;
        private String year;
        private ImagemRepository repository;


        public GDPTask(String year, String url) {
            this.url = url;
            this.year = year;
        }

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(SplashScreenActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Acessando...");
            dialog.setMessage("Espere um pouco...");
            dialog.setIndeterminate(true);
            //dialog.show();

            repository = ImagemRepository.getInstance();

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Util.trustEveryone();
            final String USER_AGENT = "Mozilla/5.0";

            try {
                //String url = "https://api.nasa.gov/planetary/earth/imagery?lon=-40.501841&lat=-7.584989&date=2014-08-01&cloud_score=True&api_key=xK1JkszDw0Y5kuwOn0BABXGxamiJFhLlNPv6uIbO";

                URL obj = new URL(url);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                //add reuqest header
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

                int responseCode = con.getResponseCode();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Log.d(AppVar.DEBUG, "RESULT : " + response.toString());

                if (responseCode == 200) {

                    JSONArray result = new JSONArray(response.toString());

                    for (int i = 0; i < result.length(); i++) {

                        JSONObject object = result.getJSONObject(i);

                        GDP veg = new GDP();
                        veg.setYear(object.getString("year"));
                        veg.setGdp(object.getDouble("value"));

                        repository.save(veg);

                    }


                    return true;

                }

                return false;


            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (ProtocolException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean parsingError) {

            increment = increment + 10;
            progressBar.setProgress(increment);

            WikiTask wiki = new WikiTask(repository.getCity());
            //wiki.execute();

        }

        @Override
        protected void onCancelled() {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    private class WikiTask extends AsyncTask<Void, Void, Boolean> {

            private ProgressDialog dialog;
            //private String url;
            private String year;
            private ImagemRepository repository;


            public WikiTask(String year) {
                //this.url = url;
                this.year = year;
            }

            @Override
            protected void onPreExecute() {

                dialog = new ProgressDialog(SplashScreenActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setTitle("Acessando...");
                dialog.setMessage("Espere um pouco...");
                dialog.setIndeterminate(true);
                //dialog.show();

                repository = ImagemRepository.getInstance();

            }

            @Override
            protected Boolean doInBackground(Void... params) {

                Util.trustEveryone();
                final String USER_AGENT = "Mozilla/5.0";

                try {
                    String url = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=" + year + "&formatversion=2";

                    Log.d(AppVar.DEBUG, "URL : " + url);

                    URL obj = new URL(url);

                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                    //add reuqest header
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", USER_AGENT);
                    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                    con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

                    int responseCode = con.getResponseCode();

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    Log.d(AppVar.DEBUG, "RESULT : " + response.toString());

                    if (responseCode == 200) {

                        JSONObject x = new JSONObject(response.toString());

                        JSONObject x2 = x.getJSONObject("query");

                        JSONArray x3 = x2.getJSONArray("pages");


                        JSONObject x4 = x3.getJSONObject(0);

                        String text = x4.getString("extract");


                        repository.setWikipedia(text);


                        return true;

                    }

                    return false;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return false;
                } catch (ProtocolException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return false;
            }

            @Override
            protected void onPostExecute(Boolean parsingError) {

                increment = increment + 10;
                progressBar.setProgress(increment);

            }

            @Override
            protected void onCancelled() {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {

            switch (requestCode) {
                case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                        Log.d(AppVar.DEBUG, "DENIED");

                   /* Snackbar.make(getCurrentFocus(), R.string.permission_fine_location_rationale,
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                   /* final Intent i = new Intent();
                                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    i.addCategory(Intent.CATEGORY_DEFAULT);
                                    i.setData(Uri.parse("package:" + getApplication().getPackageName()));
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    getApplication().startActivity(i);

                                }
                            })
                            .show();*/

                    } else {

                        downloadData();

                    }
                }

            }
        }

}
