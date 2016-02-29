package com.krotic.lutec.kcontroler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ActivityControl extends AppCompatActivity {


    private final String LED_ON = "http://192.168.4.1/led/1";
    private final String LED_OFF = "http://192.168.4.1/led/0";

    private boolean isOn = false;

    private ActivityControl activity = this;
    private NetworkInfo networkInfo;

    private Button buttonOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        buttonOnOff = (Button) findViewById(R.id.btn_led_on_off);
        buttonOnOff.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        isOn = true;
                        buttonOnOff.setText(R.string.btn_led_on);
                        new SendCommand().execute(LED_ON);
                        break;
                    case MotionEvent.ACTION_UP:
                        isOn = false;
                        buttonOnOff.setText(R.string.btn_led_off);
                        new SendCommand().execute(LED_OFF);
                        break;
                }

                return false;
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connMgr.getActiveNetworkInfo();

    }

    private class SendCommand extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String result = "";

            try {

                if(params[0].compareTo(LED_ON)==0){
                    Thread.sleep(1200);
                    if(isOn){
                        URL url = new URL(params[0]);
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        conn.setReadTimeout(10000 /* milliseconds */);
                        conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("GET");
                        conn.setDoInput(true);
                        conn.connect();

                        int response = conn.getResponseCode();
                        Log.v("AsyncTask", "Respuesta del request: " + Integer.toString(response));

                        InputStream in = conn.getInputStream();
                        result = readIt(in, 500);

                        conn.disconnect();
                    }
                }
                else{
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    int response = conn.getResponseCode();
                    Log.v("AsyncTask", "Respuesta del request: " + Integer.toString(response));

                    InputStream in = conn.getInputStream();
                    result = readIt(in, 500);

                    conn.disconnect();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast toast = Toast.makeText(activity, s, Toast.LENGTH_LONG);
            //toast.show();
        }

        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }

}
