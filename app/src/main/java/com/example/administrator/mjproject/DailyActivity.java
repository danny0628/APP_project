package com.example.administrator.mjproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class DailyActivity extends AppCompatActivity {
    ArrayList<String> Items;
    ArrayAdapter<String> Adapter;
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Items = new ArrayList<String>();
        Items.add("First");
        Items.add("Second");
        Items.add("Third");
        Items.add("Fourth");
        Items.add("Fifth");

        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, Items);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(Adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public void mOnCilck(View v){
        EditText ed = (EditText)findViewById(R.id.newitem);
        switch (v.getId()){
            case R.id.add:
                String text = ed.getText().toString();
                if(text.length() != 0){
                Items.add(text);
                ed.setText("");
                Adapter.notifyDataSetChanged();
            }
            break;
            case R.id.delete:
                int pos;
                pos = list.getCheckedItemPosition();
                if(pos != ListView.INVALID_POSITION){
                    Items.remove(pos);
                    list.clearChoices();
                    Adapter.notifyDataSetChanged();
                }
                break;
        }

    }
}
