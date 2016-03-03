package com.krotic.lutec.kcontroler;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Andres on 2/29/2016.
 */
public class SendInstruction extends AsyncTask<String, Void, Void> {

    private static String WifiRed = "sparkfun";
    private static int delay = 10;

    public static final String LED_ON = "http://192.168.4.1/led/1";
    public static final String LED_OFF = "http://192.168.4.1/led/0";

    public static final String FORWARD = "http://192.168.4.1/ad";
    public static final String BACKWARD = "http://192.168.4.1/at";
    public static final String RIGHT = "http://192.168.4.1/der";
    public static final String LEFT = "http://192.168.4.1/izq";
    public static final String STOP = "http://192.168.4.1/halt";

    private static boolean doAction = true;


    public static String getWifiRed(){
        return WifiRed;
    }

    public static void setWifiRed(String wifiRed){
        WifiRed = wifiRed;
    }

    @Override
    protected Void doInBackground(String... params) {

        String command = params[0];

        if((command.compareTo(LED_ON) == 0) || (command.compareTo(LED_OFF) == 0)){
            light(command);
        }
        else if(command.compareTo(STOP) == 0){
            halt(command);
        }
        else{
            movement(command);
        }

        return null;
    }

    private void light(String light_state){
        sendMessage(light_state);
    }

    private void movement(String moveCommand){
        doAction = true;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(doAction){
            sendMessage(moveCommand);
        }
    }

    private void halt(String haltCommand){
        doAction = false;
        sendMessage(haltCommand);
    }

    private void sendMessage(String str_url){
        String result = "";

        try {

            URL url = new URL(str_url);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            Log.d("AsyncTask", "Respuesta del request: " + Integer.toString(response));

            InputStream in = conn.getInputStream();
            result = readIt(in, 500);
            Log.d("AsyncTask", "Texto devuelto por el request: " + result);

            conn.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
