package com.krotic.lutec.kcontroler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityStarter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
    }

    public void toControlAct(View view){
        Intent intent = new Intent(this, ActivityControl.class);
        startActivity(intent);
    }
}
