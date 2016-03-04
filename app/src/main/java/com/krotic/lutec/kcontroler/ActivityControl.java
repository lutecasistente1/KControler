package com.krotic.lutec.kcontroler;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class ActivityControl extends AppCompatActivity {
    
    private boolean isLight = false;

    private ImageButton forward, backward, light, left, right, settings;

    private ActivityControl instance = this;

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
                    new SendInstruction().execute(SendInstruction.LED);
                }else{
                    light.setImageResource(R.drawable.light_pressed);
                    isLight = !isLight;
                    new SendInstruction().execute(SendInstruction.LED);
                }
            }
        });

        settings = (ImageButton) findViewById(R.id.btn_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(instance);

                builder.setTitle("Definir tiempo de respuesta");

                builder.setItems(R.array.calibrate_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 1:
                                SendInstruction.realTime();
                                Log.v("DialogPicker", "indice: " + Integer.toString(which));
                                break;
                            case 2:
                                SendInstruction.mediumDelay();
                                Log.v("DialogPicker", "indice: " + Integer.toString(which));
                                break;
                            case 3:
                                SendInstruction.maxDelay();
                                Log.v("DialogPicker", "indice: " + Integer.toString(which));
                                break;
                            default:
                                Log.v("DialogPicker", "indice: "+ Integer.toString(which));
                        }
                    }
                });

                builder.create().show();
            }
        });

    }

}
