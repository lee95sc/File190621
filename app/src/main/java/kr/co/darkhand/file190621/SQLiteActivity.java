package kr.co.darkhand.file190621;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class SQLiteActivity extends AppCompatActivity {

    //데이터베이스 변수
    WordDBHelper dbHelper;



    // SQLite를 사용하기 위한 클래스 생성
    class WordDBHelper extends SQLiteOpenHelper {
        //생성자에서 데이터베이스를 생성
        public WordDBHelper(Context context){
            //데이터베이스 파일 생성
            //EngWord.db 을 파일이름으로 하고 Version 은 1
            //factory는 표준 커서 이용
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
            //테이블을 삭제하고 새로 생성
            //기존 데이터가 존재하고 데이터를 보존할 필요성이 있을 때는
            //데이터를 옮기고 테이블을 다시 생성
            db.execSQL("drop table if exists dic");
            onCreate(db);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        //데이터베이스 생성
        dbHelper = new WordDBHelper(SQLiteActivity.this);

        //버튼 찾아오기
        Button create = (Button)findViewById(R.id.savesql);
        //저장 버튼을 클릭했을 때 처리
        create.setOnClickListener((view)->{
            //데이터베이스 찾아오기
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //삽입할 데이터를 생성
            ContentValues row = new ContentValues();
            row.put("han", "남자");
            row.put("eng", "man");
            //데이터 삽입
            db.insert("dic", null, row);

            //sql을 이용해서 삽입
            db.execSQL("insert into dic values(null, 'woman', '여자');");

            //로그출력
            Log.e("================ SQLite 데이터 삽입", "성공");

            //데이터베이스 닫기
            dbHelper.close();
        });


        //읽기 버튼 찾아오기
        Button read = (Button)findViewById(R.id.selectsql);
        //읽기 버튼을 눌렀을 때 수행할 내용
        read.setOnClickListener((view)->{
            //Edit 찾아오기
            EditText display = (EditText)findViewById(R.id.displaysql);
            //dic 테이블의 모든 데이터 찾아오기
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select eng, han from dic", null);
            //모든 데이터 읽기
            String r = "";
            while(cursor.moveToNext()){
                String eng = cursor.getString(0);
                String han = cursor.getString(1);
                r +=  eng + ":" + han + "\n";
            }
            display.setText(r);
            cursor.close();
            dbHelper.close();
        });



        //데이터 수정
        Button edit = (Button)findViewById(R.id.updatesql);
        //수정 버튼을 눌렀을 때 수행할 내용
        edit.setOnClickListener((view)->{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //수정할 내용 작성 - set 절의 내용
            ContentValues row = new ContentValues();
            row.put("han", "남성");
            //db.update("dic", row, "eng='man'", null);
            db.update("dic", row, "eng='woman'", null);
            dbHelper.close();
        });


        //데이터 삭제
        Button delete = (Button)findViewById(R.id.deletesql);
        //삭제 버튼을 눌렀을 때 수행할 내용
        delete.setOnClickListener((view)->{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //삭제할 내용
            db.delete("dic", "eng='man'", null);
            db.close();
        });


    } // END protected void onCreate(Bundle savedInstanceState) {

}
