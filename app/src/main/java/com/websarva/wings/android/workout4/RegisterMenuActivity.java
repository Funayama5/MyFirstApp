package com.websarva.wings.android.workout4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class RegisterMenuActivity extends AppCompatActivity {

    private TextView addmenu;
    private Button btregister;
    private EditText et1,et2,et3,et4,et5;
    //EditTextに自分で記入した種目を入れるための配列
    private String[] menus;
    //MenuActivityからの送られてくる部位を入れる変数。
    private String part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);

        //「メニューを追加する」ボタンがタップされたときの処理
        Intent intent = getIntent();
        addmenu = findViewById(R.id.add_menu);
        part = intent.getStringExtra("part");
        addmenu.setText(part + "のメニューを追加");

        //記入したメニューの入ったEditTextを取得。
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);

        //「登録」ボタンをタップしたときの処理。
        btregister = findViewById(R.id.bt_register);

        //１．データベースへの情報の登録。２．MenuActivityへ画面遷移。
        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menus配列に要素を入れる。
                menus = new String[]{et1.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4.getText().toString(), et5.getText().toString()};
                //１．データベースへの登録。
                DatabaseHelper helper = new DatabaseHelper(RegisterMenuActivity.this);
                //接続オブジェクトを取得。
                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    String sqlInsert = "INSERT INTO menu_table_piece(part,menu) VALUES(?,?)";
                    //ステートメンとを取得。
                    SQLiteStatement stmt = db.compileStatement(sqlInsert);
                    for(int i = 0;i < menus.length; i ++){
                        //変数のバインド。
                        if(menus[i].length() > 0 ){
                            stmt.bindString(1,part);
                            stmt.bindString(2,menus[i]);
                            //インサートSQLの実行。
                            stmt.executeInsert();
                        }
                    }
                }finally{
                    //DB接続オブジェクトを閉じる。
                    db.close();
                }
                //２．MenuActivityに画面遷移。
                //MenuActivityで返答を受け取るようにstartActivityForResultを記述しているからIntent()の引数なし。
                //startActivityは不要。
                Intent intent = new Intent();
                //記入したメニューを消去する。
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                et5.setText("");

                setResult(RESULT_OK,intent);

                finish();
            }
        });

    }
}
