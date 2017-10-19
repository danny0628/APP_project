package com.example.administrator.mjproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button mbuttonDailyActivity;
    private Button mbuttonTrainActivity;
    private Button mbuttonGymActivity;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(this);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                Intent intent1 = new Intent(this, DailyActivity.class);
                startActivity(intent1);
                break;
            case R.id.button2:
                Intent intent2 = new Intent(this, GymActivity.class);
                startActivity(intent2);
                break;
            case R.id.button3:
                Intent intent3 = new Intent(this, TrainActivity.class);
                startActivity(intent3);
                break;
        }

    }

}
