package com.websarva.wings.android.workout4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private String date;
    private Button bt_chest,bt_biceps,bt_triceps,bt_shoulder,bt_back,bt_leg,bt_aero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent intent = getIntent();

        Log.d("Calendar","ok");

        //カレンダーの日付をタップした時の処理。
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "年" + month +"月" + dayOfMonth + "日";

                Intent intent = new Intent(CalendarActivity.this,PartActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });

        //７種のボタンの取得とリスナ設定。
        bt_chest = findViewById(R.id.button_chest);
        bt_biceps = findViewById(R.id.button_biceps);
        bt_triceps = findViewById(R.id.button_triceps);
        bt_shoulder = findViewById(R.id.button_shoulder);
        bt_back = findViewById(R.id.button_back);
        bt_leg = findViewById(R.id.button_leg);
        bt_aero = findViewById(R.id.button_aero);

        ConfirmMenuListener listener = new ConfirmMenuListener();

        bt_chest.setOnClickListener(listener);
        bt_biceps.setOnClickListener(listener);
        bt_triceps.setOnClickListener(listener);
        bt_shoulder.setOnClickListener(listener);
        bt_back.setOnClickListener(listener);
        bt_leg.setOnClickListener(listener);
        bt_aero.setOnClickListener(listener);

    }
    //７種のボタンをタップした時のリスナ設定。
    private class ConfirmMenuListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            //ボタンのidごとに分岐。
            int id = view.getId();
            Intent intent = new Intent(CalendarActivity.this,RegisteredMenuActivity.class);
            switch(id){
                //胸
                case R.id.button_chest:
                    intent.putExtra("part","胸");
                    startActivity(intent);
                    break;

                case R.id.button_biceps:
                    intent.putExtra("part","上腕二頭筋");
                    startActivity(intent);
                    break;

                case R.id.button_triceps:
                    intent.putExtra("part","上腕三頭筋");
                    startActivity(intent);
                    break;

                case R.id.button_shoulder:
                    intent.putExtra("part","肩");
                    startActivity(intent);
                    break;

                case R.id.button_back:
                    intent.putExtra("part","背中");
                    startActivity(intent);
                    break;

                case R.id.button_leg:
                    intent.putExtra("part","脚");
                    startActivity(intent);
                    break;

                case R.id.button_aero:
                    intent.putExtra("part","有酸素運動");
                    startActivity(intent);
                    break;
            }
        }
    }


}
