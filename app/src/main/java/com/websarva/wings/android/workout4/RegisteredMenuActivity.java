package com.websarva.wings.android.workout4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisteredMenuActivity extends AppCompatActivity {

    private String part;
    private TextView tv_part;
    private String strMenu,strDate,strWeight,strRep,strMinute,strSecond;
    private List<Map<String,String>> menulist;
    private Map<String,String> menus;
    private ListView lv_menu;
    public static final String[] FROM = {"menuMap","dateMap","weightMap","repMap","minuteMap","secondMap"};
    public static final int[] TO = {R.id.tv_menu,R.id.tv_date,R.id.tv_kg,R.id.tv_rep,R.id.tv_min,R.id.tv_sec};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_menu);


        //７種の登録内容の確認ボタンをタップしたとき
        Intent intent =getIntent();
        part = intent.getStringExtra("part");
        tv_part = findViewById(R.id.tv_part);
        tv_part.setText(part);

        Log.d("fromcalendar","ok");

        //ListView(lv_menu)にDBからの情報を表示させる。
        DatabaseHelper helper = new DatabaseHelper(RegisteredMenuActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        try{
            //menulistにすでに種目が入っている可能性があるので、中身を全消し。
            menulist = new ArrayList<>();
            menulist.clear();
            //取得のSQL文。
            String sql = "SELECT * FROM menu_table_all WHERE part = ?";
            //SQLの実行。
            String[] pararms = {String.valueOf(part)};
            Cursor cursor = db.rawQuery(sql,pararms);
            //SQL実行の戻り値であるカーソルオブジェクトをループさせてデータベース内のでデータを得る。
            while(cursor.moveToNext()){
                //カラムのインデックス値を取得。Mapを生成するのを忘れずに。
                menus = new HashMap<>();
                int idxMenu = cursor.getColumnIndex("menu");
                int idxDate = cursor.getColumnIndex("date");
                int idxWeight = cursor.getColumnIndex("weight");
                int idxRep = cursor.getColumnIndex("rep");
                int idxMinute = cursor.getColumnIndex("minute");
                int idxSecond = cursor.getColumnIndex("second");
                //カラムのインデックス値をもとに実際のデータの値を取得。
                strMenu = cursor.getString(idxMenu);
                strDate = cursor.getString(idxDate);
                strWeight = cursor.getString(idxWeight);
                strRep = cursor.getString(idxRep);
                strMinute = cursor.getString(idxMinute);
                strSecond = cursor.getString(idxSecond);
                //mapに入れる。
                menus.put("menuMap",strMenu);
                menus.put("dateMap",strDate);
                menus.put("weightMap",strWeight);
                menus.put("repMap",strRep);
                menus.put("minuteMap",strMinute);
                menus.put("secondMap",strSecond);
                //menulistに入れる。
                menulist.add(menus);
            }
            //menulistにSimpleAdapter登録。
            SimpleAdapter adapter = new SimpleAdapter(RegisteredMenuActivity.this,menulist,R.layout.registered_menu,FROM,TO);
            lv_menu = findViewById(R.id.lv_menu);
            lv_menu.setAdapter(adapter);
        }
        finally {
            //データベース接続オブジェクトの解放。
            db.close();
        }
    }
    //「カレンダーに戻る」ボタンがタップされた時
    public void backCalendar(View view){
        finish();
    }
}
