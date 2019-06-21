package kr.co.darkhand.file190621;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class SQLiteActivity extends AppCompatActivity {
    //데이터베이스 변수
    WordDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        //데이터베이스 생성
        dbHelper = new WordDBHelper(SQLiteActivity.this);


    }


    class WordDBHelper extends SQLiteOpenHelper {
        //생성자에서 데이터베이스를 생성
        public WordDBHelper(Context context){
            super(context, "EngWord.db", null, 1);
        }

        //데이터베이스가 만들어 질 때 호출되는 메소드
        @Override
        public void onCreate(SQLiteDatabase db){
            //테이블 만들기
            db.execSQL("CREATE TABLE dic (_id INTEGER PRIMARY KEY AUTOINCREMENT, eng TEXT, han TEXT);");
        }

        //데이터 베이스 버전이 변경될 때 호출되는 메소드
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("drop table if exists dic");
            onCreate(db);
        }
    }

}
