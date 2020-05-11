package com.websarva.wings.android.workout4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WriteMenuActivity extends AppCompatActivity {

    private String date,getMenu,part;
    private TextView tv_date,tv_getMenu;
    private Button bt_register ;
    private EditText et_kg_1,et_kg_2,et_kg_3,et_kg_4,et_kg_5;
    private EditText et_rep_1,et_rep_2,et_rep_3,et_rep_4,et_rep_5;
    private EditText et_min_1,et_min_2,et_min_3,et_min_4,et_min_5;
    private EditText et_sec_1,et_sec_2,et_sec_3,et_sec_4,et_sec_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_menu);

        //MenuActivityのリストをタップしたときの処理。
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        getMenu = intent.getStringExtra("sendMenu");
        part = intent.getStringExtra("part");
        tv_date = findViewById(R.id.tv_date);
        tv_getMenu = findViewById(R.id.tv_getMenu);
        tv_date.setText(date);
        tv_getMenu.setText(getMenu);

        Log.d("frommenuactivity","ok");

        //「登録」ボタンをタップした時の処理
        bt_register = findViewById(R.id.bt_register);
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EditTextに記入した数値を取得。
                et_kg_1 = findViewById(R.id.et_kg_1);
                et_kg_2 = findViewById(R.id.et_kg_2);
                et_kg_3 = findViewById(R.id.et_kg_3);
                et_kg_4 = findViewById(R.id.et_kg_4);
                et_kg_5 = findViewById(R.id.et_kg_5);
                String[] kg = {et_kg_1.getText().toString(), et_kg_2.getText().toString(), et_kg_3.getText().toString(), et_kg_4.getText().toString(), et_kg_5.getText().toString()};
                et_rep_1 = findViewById(R.id.et_rep_1);
                et_rep_2 = findViewById(R.id.et_rep_2);
                et_rep_3 = findViewById(R.id.et_rep_3);
                et_rep_4 = findViewById(R.id.et_rep_4);
                et_rep_5 = findViewById(R.id.et_rep_5);
                String[] rep = {et_rep_1.getText().toString(), et_rep_2.getText().toString(), et_rep_3.getText().toString(), et_rep_4.getText().toString(), et_rep_5.getText().toString()};
                et_min_1 = findViewById(R.id.et_min_1);
                et_min_2 = findViewById(R.id.et_min_2);
                et_min_3 = findViewById(R.id.et_min_3);
                et_min_4 = findViewById(R.id.et_min_4);
                et_min_5 = findViewById(R.id.et_min_5);
                String[] min = {et_min_1.getText().toString(), et_min_2.getText().toString(), et_min_3.getText().toString(), et_min_4.getText().toString(), et_min_5.getText().toString()};
                et_sec_1 = findViewById(R.id.et_sec_1);
                et_sec_2 = findViewById(R.id.et_sec_2);
                et_sec_3 = findViewById(R.id.et_sec_3);
                et_sec_4 = findViewById(R.id.et_sec_4);
                et_sec_5 = findViewById(R.id.et_sec_5);
                String[] sec = {et_sec_1.getText().toString(), et_sec_2.getText().toString(), et_sec_3.getText().toString(), et_sec_4.getText().toString(), et_sec_5.getText().toString()};

                //データベースヘルパーオブジェクトを作成。
                DatabaseHelper helper = new DatabaseHelper(WriteMenuActivity.this);
                //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得。
                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    //Insert用SQL文字列の用意。
                    String sqlInsert = "INSERT INTO menu_table_all (part, menu, date, weight, rep, minute, second) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    //ステートメントを取得。
                    SQLiteStatement stmt = db.compileStatement(sqlInsert);
                    for (int i = 0; i < kg.length; i++) {
                        //変数のバインド。
                        if(kg[i].length() > 0 || rep[i].length() > 0 || min[i].length() > 0 || sec[i].length() > 0){
                            stmt.bindString(1, part);
                            stmt.bindString(2, getMenu);
                            stmt.bindString(3, date);
                            stmt.bindString(4, kg[i]);
                            stmt.bindString(5, rep[i]);
                            stmt.bindString(6, min[i]);
                            stmt.bindString(7, sec[i]);
                            //インサートSQLの実行。
                            stmt.executeInsert();
                        }
                    }
                } finally {
                    //DB接続オブジェクトの解放。
                    db.close();
                }
                //calendarActivityへの移動。
                Intent intent = new Intent(WriteMenuActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}
