package com.krotic.lutec.kcontroler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class ActivityControl extends AppCompatActivity {


    private final String LED_ON = "http://192.168.4.1/led/1";
    private final String LED_OFF = "http://192.168.4.1/led/0";

    private final String FORWARD = "http://192.168.4.1/ad";
    private final String BACKWARD = "http://192.168.4.1/at";
    private final String RIGHT = "http://192.168.4.1/der";
    private final String LEFT = "http://192.168.4.1/izq";
    private final String STOP = "http://192.168.4.1/halt";

    private String WifiRed = "sparkfun";

    private ActivityControl activity = this;
    private NetworkInfo networkInfo;

    private ImageButton forward, backward, stop, left, right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        OnTouchSend listener = new OnTouchSend();

        forward = (ImageButton) findViewById(R.id.btn_forward);
        forward.setOnTouchListener(listener);

        backward = (ImageButton) findViewById(R.id.btn_backward);
        backward.setOnTouchListener(listener);

        left = (ImageButton) findViewById(R.id.btn_left);
        left.setOnTouchListener(listener);

        right = (ImageButton) findViewById(R.id.btn_right);
        right.setOnTouchListener(listener);

        stop = (ImageButton) findViewById(R.id.btn_stop);
        stop.setOnTouchListener(listener);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

    }

}
