package com.psj.saravana.weatherapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by s.e.subramaniam on 6/21/2015.
 */
public class WeatherAsyncask extends AsyncTask<String, String, JSONObject> {
    ProgressDialog  pDialog;
    private Context ctx;
    private String city;

    public WeatherAsyncask(Context context, String cityValue){
        ctx=context;
        city=cityValue;
    }


    @Override
    protected JSONObject doInBackground(String... aUrl) {
        HttpGet httppost = new HttpGet("http://api.openweathermap.org/data/2.5/weather?q="+city);
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        String resJson = "";
        JSONObject jsonObj = null;
        try {
            response = httpclient.execute(httppost);
            InputStream content = response.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                resJson += s;
            }

            try {
                jsonObj = new JSONObject(resJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        super.onPostExecute(response);
        Log.i("saravana", "response  " + response);

        try {
            String errorCode=response.getString("cod");
            Log.i("saravana","error code"+errorCode);
            if (errorCode.equals("404"))
            {
                //Toast.makeText(ctx,"City Not Found",Toast.LENGTH_LONG).show();
                //Log.i("saravana", "response  404");
                //return;
                AlertDialog.Builder dialog= new AlertDialog.Builder(ctx);
                dialog.setTitle("Error");
                dialog.setMessage("City Not Found");
                dialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dg, int which) {
                        // continue with delete
                        dg.dismiss();
                    }
                });
                dialog.show();
            }
            else if (errorCode.equals("200"))
            {
                String temp= response.getJSONObject("main").getString("temp");
                //Toast.makeText(ctx,"City  Found",Toast.LENGTH_LONG).show();
                String[] detailsArray=new String[] {response.getString("name"),
                        response.getJSONObject("main").getString("temp"),
                        response.getJSONObject("main").getString("pressure"),
                        response.getJSONObject("main").getString("humidity")};
                Intent intent=new Intent(ctx,WeatherActivity.class);
                intent.putExtra("details",detailsArray);
                ctx.startActivity(intent);
                //Log.i("saravana", "response  200");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        pDialog.dismiss();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Getting Data...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }


}
