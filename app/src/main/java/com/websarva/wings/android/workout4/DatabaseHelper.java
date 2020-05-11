package com.websarva.wings.android.workout4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //データベースファイル名
    private static final String DATABASE_NAME = "menu_table.db";
    //バージョン情報の定数フィールド。
    private static final int DATABASE_VERSION = 1;



    //必須１　コンストラクタ
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    //必須２　onCreate()
    public void onCreate(SQLiteDatabase db){
        //テーブル作成用SQL文字列の作成。
        //テーブル一つ目：_id,部位,種目のみ
        //テーブル二つ目:_id,部位,種目,重量,回数,日付,インターバル（分、秒）
        String sql_piece = "CREATE TABLE menu_table_piece(_id INTEGER PRIMARY KEY,part TEXT,menu TEXT);";
        db.execSQL(sql_piece);
        String sql_all = "CREATE TABLE menu_table_all(_id INTEGER PRIMARY KEY,part TEXT,menu TEXT,date TEXT,weight TEXT,rep TEXT,minute TEXT,second TEXT);";
        db.execSQL(sql_all);
    }
    @Override
    //必須３　onUpgrade()
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

}
