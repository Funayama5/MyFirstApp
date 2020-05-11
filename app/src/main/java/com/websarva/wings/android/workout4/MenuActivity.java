package com.websarva.wings.android.workout4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private TextView tvdate,tvpart;
    private Button btadd;
    private String part,date;
    private ListView lvmenu;
    public static final int RESULT_REGISTER_MENU_ACTIVITY = 1000;
    private List<String> menulist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //PartActivityの部位ボタンのいずれかがタップされたときの処理。
        //日付と部位を選択。
        Intent intent = getIntent();

        Log.d("MenuSucceed","ok");
        date = intent.getStringExtra("date");
        part = intent.getStringExtra("part");
        tvdate = findViewById(R.id.date);
        tvpart = findViewById(R.id.tv_part);
        tvdate.setText(date);
        tvpart.setText(part);

        //DBから登録情報を取得してlvmenuに入れる。
        DatabaseHelper helper = new DatabaseHelper(MenuActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        Log.d("MenuCreateDatabase","ok");

        try {
            //lvMenuにすでに種目が入っている可能性があるので、lvMenuの中身を全消し。
            menulist = new ArrayList<>();
            menulist.clear();

            //取得のSQL文。
            String sqlSelect = "SELECT * FROM menu_table_piece WHERE part = ?";

            //SQLの実行。
            //cursorの第２引数String[]を生成。
            String[] parts = {String.valueOf(part)};
            Cursor cursor = db.rawQuery(sqlSelect,parts);
            //データベースから取得した値を格納する変数の準備。
            String menu = "";
            //SQL実行の戻り値であるカーソルオブジェクトをループさせてデータベース内のでデータを得る。
            while(cursor.moveToNext()){
                //カラムのインデックス値を取得。
                int idxNote = cursor.getColumnIndex("menu");
                //カラムのインデックス値をもとに実際のデータの値を取得。
                menu = cursor.getString(idxNote);
                menulist.add(menu);
            }
            //lvmenuにadapter登録。
            lvmenu = findViewById(R.id.lv_menu);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MenuActivity.this, android.R.layout.simple_list_item_1, menulist);
            lvmenu.setAdapter(adapter);
        }
        finally {
            //データベース接続オブジェクトを閉じる。
            db.close();
        }

        //ボタンを取得。
        //「メニューを追加」ボタンをタップしたときの処理。
        //RegisterMenuActivityへデータを送る。RegisterMenuActivityからのデータの返答を受け取れるように設定。
        btadd = findViewById(R.id.bt_add);

        Log.d("buttonSucceed","ok");

        btadd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MenuActivity.this,RegisterMenuActivity.class);
                intent.putExtra("part",part);
                startActivityForResult(intent,RESULT_REGISTER_MENU_ACTIVITY);
            }
        });
        //lvmenuにリスナ設定。
        lvmenu.setOnItemClickListener(new ListMenuClickListener());
    }


    //lvmenuのリスナ（ListMenuClickListener）の作成。
    private class ListMenuClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?>parent,View view,int position,long id){
            Intent intent = new Intent(MenuActivity.this,WriteMenuActivity.class);
            TextView tv = (TextView) view;
            String sendMenu = tv.getText().toString();
            intent.putExtra("sendMenu",sendMenu);
            intent.putExtra("date",date);
            intent.putExtra("part",part);
            startActivity(intent);
        }
    }


    //RegisterMenuActivityからの画面遷移。
    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK && requestCode == RESULT_REGISTER_MENU_ACTIVITY &&
                intent != null) {

            //DBから登録情報を取得してlvmenuに入れる。
            DatabaseHelper helper = new DatabaseHelper(MenuActivity.this);
            SQLiteDatabase db = helper.getWritableDatabase();
            try{
                //lvMenuにすでに種目が入っている可能性があるので、lvMenuの中身を全消し。
                menulist = new ArrayList<>();
                menulist.clear();
                //取得のSQL文。
                String sql = "SELECT * FROM menu_table_piece WHERE part = ?";
                //SQLの実行。
                String[] parts = {String.valueOf(part)};
                Cursor cursor = db.rawQuery(sql,parts);

                //データベースから取得した値を格納する変数の準備。ArrayListも準備。
                String menu = "";

                //SQL実行の戻り値であるカーソルオブジェクトをループさせてデータベース内のでデータを得る。
                while(cursor.moveToNext()){
                    //カラムのインデックス値を取得。
                    int idxNote = cursor.getColumnIndex("menu");
                    //カラムのインデックス値をもとに実際のデータの値を取得。
                    menu = cursor.getString(idxNote);
                    menulist.add(menu);
                }
                //lvmenuにadapter登録。
                //lvmenuを再度生成することで、すべてのDBからの情報を反映させる。
                lvmenu = findViewById(R.id.lv_menu);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MenuActivity.this, android.R.layout.simple_list_item_1, menulist);
                lvmenu.setAdapter(adapter);
            }
            finally {
                //データベース接続オブジェクトの解放。
                db.close();
            }
            //lvmenuにリスナ設定。
            lvmenu.setOnItemClickListener(new ListMenuClickListener());
        }
    }
}

