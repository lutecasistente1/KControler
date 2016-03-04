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

    private static int delay = 1;

    private static final String HOST = "http://192.168.4.1/";

    public static final String LED = HOST + "led";

    public static final String FORWARD = HOST + "ad";
    public static final String BACKWARD = HOST + "at";
    public static final String RIGHT = HOST + "der";
    public static final String LEFT = HOST + "izq";
    public static final String STOP = HOST + "halt";

    private static boolean doAction = true;

    public static void realTime(){
        delay = 1;
    }

    public static void mediumDelay(){
        delay = 500;
    }

    public static void maxDelay(){
        delay = 1000;
    }

    @Override
    protected Void doInBackground(String... params) {

        String command = params[0];

        if((command.compareTo(LED) == 0)) {
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
