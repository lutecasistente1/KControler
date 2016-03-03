package com.krotic.lutec.kcontroler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ActivityControl extends AppCompatActivity {

    private ActivityControl activity = this;
    private NetworkInfo networkInfo;
    
    private boolean isLight = false;

    private ImageButton forward, backward, light, left, right;

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

        light = (ImageButton) findViewById(R.id.btn_light);
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLight){
                    light.setImageResource(R.drawable.light);
                    isLight = !isLight;
                }else{
                    light.setImageResource(R.drawable.light_pressed);
                    isLight = !isLight;
                }
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

    }

}
