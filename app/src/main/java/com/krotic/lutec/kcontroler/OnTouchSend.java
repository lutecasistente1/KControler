package com.krotic.lutec.kcontroler;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Andres on 2/29/2016.
 */
public class OnTouchSend implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            switch (v.getId()){
                case R.id.btn_forward:
                    changeImage(v,R.drawable.forward_pressed);
                    new SendInstruction().execute(SendInstruction.FORWARD);
                    break;
                case R.id.btn_backward:
                    changeImage(v,R.drawable.backward_pressed);
                    new SendInstruction().execute(SendInstruction.BACKWARD);
                    break;
                case R.id.btn_left:
                    changeImage(v,R.drawable.counterclockwise_pressed);
                    new SendInstruction().execute(SendInstruction.LEFT);
                    break;
                case R.id.btn_right:
                    changeImage(v,R.drawable.clockwise_pressed);
                    new SendInstruction().execute(SendInstruction.RIGHT);
                    break;
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            new SendInstruction().execute(SendInstruction.STOP);
            switch (v.getId()){
                case R.id.btn_forward:
                    changeImage(v,R.drawable.forward);
                    break;
                case R.id.btn_backward:
                    changeImage(v,R.drawable.backward);
                    break;
                case R.id.btn_left:
                    changeImage(v,R.drawable.counterclockwise);
                    break;
                case R.id.btn_right:
                    changeImage(v,R.drawable.clockwise);
                    break;
            }
        }

        return false;
    }

    public void changeImage(View view, int img){
        ImageButton button = (ImageButton) view;
        button.setImageResource(img);
    }
}
