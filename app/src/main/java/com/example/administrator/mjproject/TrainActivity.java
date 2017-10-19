package com.example.administrator.mjproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class TrainActivity extends AppCompatActivity implements OnClickListener{
    void setOnDateChangeListener(CalendarView.OnDateChangeListener listener) {

    }

    void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);


        CalendarView calender = (CalendarView)findViewById(R.id.calendar);
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth){
                Toast.makeText(TrainActivity.this, "" + year + "/" + (month + 1) + "/" + dayOfMonth, 0).show();
            }
        });

    }

    @Override
    public void onClick(View v) {



    }
}
