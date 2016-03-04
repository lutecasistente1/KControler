package com.krotic.lutec.kcontroler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ActivityStarter extends AppCompatActivity {

    private NetworkInfo networkInfo;

    private String regex = "(\\w|\\W)*ESP8266(\\w|\\W)*";

    private boolean isWiFiOK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

         String actualWiFi = networkInfo.getExtraInfo();

        if(actualWiFi.matches(regex)){
            Toast toast = Toast.makeText(this, "Red correcta", Toast.LENGTH_SHORT);
            toast.show();
            isWiFiOK = true;
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("La red WiFi en la que se encuentra no es apta para controlar el robot");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isWiFiOK = false;
                }
            });

            builder.create().show();
        }
    }

    public void toControlAct(View view){

        String actualWiFi = networkInfo.getExtraInfo();

        Log.v("ActivityStarter", actualWiFi);

        if(actualWiFi.matches(regex)){
            Toast toast = Toast.makeText(this, "Red correcta", Toast.LENGTH_SHORT);
            toast.show();
            isWiFiOK = true;
        }


        if(isWiFiOK){
            Intent intent = new Intent(this, ActivityControl.class);
            startActivity(intent);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("La red WiFi en la que se encuentra no es apta para controlar el robot");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isWiFiOK = false;
                }
            });

            builder.create().show();
        }

    }
}
