package com.websarva.wings.android.workout4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PartActivity extends AppCompatActivity {

    private TextView tvdate,biceps,triceps,chest,leg,shoulder,back,aero;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part);

        tvdate = findViewById(R.id.date);

        //カレンダーがタップされたときの処理。
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        tvdate.setText(date);


        Log.d("fromCalendar","ok");

        //各種ボタンの取得。
        biceps = findViewById(R.id.biceps);
        triceps = findViewById(R.id.triceps);
        chest = findViewById(R.id.chest);
        leg = findViewById(R.id.leg);
        shoulder = findViewById(R.id.shoulder);
        back = findViewById(R.id.back);
        aero = findViewById(R.id.aero);

        //部位がタップされたときのリスナ設定。
        setPartListener listener = new setPartListener();

        biceps.setOnClickListener(listener);
        triceps.setOnClickListener(listener);
        chest.setOnClickListener(listener);
        leg.setOnClickListener(listener);
        shoulder.setOnClickListener(listener);
        back.setOnClickListener(listener);
        aero.setOnClickListener(listener);

    }
    private class setPartListener implements View.OnClickListener{
        @Override
        public void onClick(View view){

            //タップされたボタンごとのidで分岐処理。

            Log.d("beforeSelectItem","ok");

            int id = view.getId();
            switch (id){
                //胸
                case R.id.chest:

                    Log.d("selectItem","ok");

                    Intent intent = new Intent(PartActivity.this,MenuActivity.class);
                    intent.putExtra("part","胸");
                    intent.putExtra("date",date);
                    startActivity(intent);

                    Log.d("afterSelectItem","ok");

                    break;
                //二頭筋
                case R.id.biceps:
                    intent = new Intent(PartActivity.this,MenuActivity.class);
                    intent.putExtra("part","上腕二頭筋");
                    intent.putExtra("date",date);
                    startActivity(intent);
                    break;
                //三頭筋
                case R.id.triceps:
                    intent = new Intent(PartActivity.this,MenuActivity.class);
                    intent.putExtra("part","上腕三頭筋");
                    intent.putExtra("date",date);
                    startActivity(intent);
                    break;
                //肩
                case R.id.shoulder:
                    intent = new Intent(PartActivity.this,MenuActivity.class);
                    intent.putExtra("part","肩");
                    intent.putExtra("date",date);
                    startActivity(intent);
                    break;
                //背中
                case R.id.back:
                    intent = new Intent(PartActivity.this,MenuActivity.class);
                    intent.putExtra("part","背中");
                    intent.putExtra("date",date);
                    startActivity(intent);
                    break;
                //脚
                case R.id.leg:
                    intent = new Intent(PartActivity.this,MenuActivity.class);
                    intent.putExtra("part","脚");
                    intent.putExtra("date",date);
                    startActivity(intent);
                    break;
                //有酸素
                case R.id.aero:
                    intent = new Intent(PartActivity.this,MenuActivity.class);
                    intent.putExtra("part","有酸素運動");
                    intent.putExtra("date",date);
                    startActivity(intent);
                    break;
            }
        }

    }
}
