package com.tangqi.filepersistencetest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText et ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.content);
        String inputText = load();
        if(!TextUtils.isEmpty(inputText)){
            et.setText(inputText);
            et.setSelection(inputText.length());
            Toast.makeText(this,"Restoring succeeded",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String word = et.getText().toString();
        save(word);
    }

    public void save(String word){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(word);
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String load(){
        FileInputStream fis = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try{
            fis = openFileInput("data");
            br = new BufferedReader(new InputStreamReader(fis));
            String cache = "";
            while ((cache = br.readLine())!=null){
                sb.append(cache);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
           if(br!=null){
               try{
                   br.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }
        return sb.toString();
    }
}
