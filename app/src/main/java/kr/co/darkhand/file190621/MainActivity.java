package kr.co.darkhand.file190621;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //어노니머스나 람다에서 사용할 수 있도록 final 로 변수를 생성
        final EditText display = (EditText)findViewById(R.id.display);

        //1 저장
        //파일을 생성해서 데이터를 저장하기 위한 버튼의 클릭 이벤트 처리
        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener((view) -> {
            //try ~ resource 구문
            //()안에서 만든 객체는 close()를 하지 않아도 됩니다.
            try(FileOutputStream fos = openFileOutput("test.txt", Context.MODE_PRIVATE)){
                fos.write("안드로이드 파일 출력".getBytes());
                fos.flush();
                display.setText("저장 성공");
            }catch (Exception e){
                Log.e("파일저장 실패 ===================", e.getMessage());
            }
        });


        //2.읽기
        Button load =(Button)findViewById(R.id.load);
        load.setOnClickListener((view) -> {
            // 1안) try~resource  : 소스가 간결해 진다.
           try(FileInputStream fis = openFileInput("test.txt")){
               //파일의 내용을 읽을 저장 공간 만들기
               byte [] buf = new byte[fis.available()];
               //파일의 내용읽기
               fis.read(buf);
               display.setText(new String(buf));
           }catch (Exception e){
               Log.e("파일읽기 실패 ===================", e.getMessage());
           }
        });



        //3.삭제
        Button delete =(Button)findViewById(R.id.delete);
        delete.setOnClickListener((view) -> {
            boolean result = deleteFile("test.txt");
            if(result){
                display.setText("삭제 성공");
            }else{
                display.setText("삭제 실패");
            }
        });


        //4.리소스 파일 일기
        Button resload = (Button)findViewById(R.id.resload);
        resload.setOnClickListener((view) -> {
            // 2안) finally 구문 방식 : source가 길고 복잡해진다.
            /*
            InputStream fis = null;
            try{
                fis = getResources().openRawResource(R.raw.data);
                byte [] buf = new byte[fis.available()];
                fis.read(buf);
                display.setText(new String(buf));
            }catch (Exception e){
                Log.e("리소스 파일읽기 실패 ===================", e.getMessage());
            }finally {
                try{
                    if(fis != null){
                        fis.close();
                    }
                }catch (Exception e){}
            }
            */

            //1안) try ~ resource 구문
            //()안에서 만든 객체는 close()를 하지 않아도 됩니다.
            try(InputStream fis = getResources().openRawResource(R.raw.data)){
                byte [] buf = new byte[fis.available()];
                fis.read(buf);
                display.setText(new String(buf));
            }catch (Exception e){
                Log.e("리소스 파일읽기 실패 ===================", e.getMessage());
            }
        });




    }
}
