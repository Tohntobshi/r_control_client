package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private RobotController rc;

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            Context context = getApplicationContext();
            CharSequence text = (String) message.obj;
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upButton = findViewById(R.id.buttonUp);
        downButton = findViewById(R.id.buttonDown);
        leftButton = findViewById(R.id.buttonLeft);
        rightButton = findViewById(R.id.buttonRight);
        rc = new RobotController();
        upButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        rc.sendCommand(RobotController.MOVE_FORWARD);
                        break;
                    case MotionEvent.ACTION_UP:
                        rc.sendCommand(RobotController.STOP_MOVING);
                        break;
                }
                return false;
            }
        });
        downButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        rc.sendCommand(RobotController.MOVE_BACKWARDS);
                        break;
                    case MotionEvent.ACTION_UP:
                        rc.sendCommand(RobotController.STOP_MOVING);
                        break;
                }
                return false;
            }
        });
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        rc.sendCommand(RobotController.MOVE_LEFT);
                        break;
                    case MotionEvent.ACTION_UP:
                        rc.sendCommand(RobotController.STOP_MOVING);
                        break;
                }
                return false;
            }
        });
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        rc.sendCommand(RobotController.MOVE_RIGHT);
                        break;
                    case MotionEvent.ACTION_UP:
                        rc.sendCommand(RobotController.STOP_MOVING);
                        break;
                }
                return false;
            }
        });
    }
    public void establishConnection(View view) {
        rc.establishConnection(handler);
    }
}
