package com.tangqi.litepaltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createDatabase = (Button)findViewById(R.id.create_database);
        Button adddata = (Button)findViewById(R.id.add_data);
        Button updatedata = (Button)findViewById(R.id.update_data);
        Button deletedata = (Button)findViewById(R.id.delete_data);
        Button querydata = (Button)findViewById(R.id.query_data);

        createDatabase.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
            }
        });

        adddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book1 = new Book();
                book1.setAuthor("David");
                book1.setName("The Secret");
                book1.setPages(346);
                book1.setPrice(28.99);
                book1.save();
            }
        });

        updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("book2 for update");
                book.setPrice(0.01);
                book.save();
                book.setPrice(1.99);
                book.save();

                book.setPrice(99.00);
                book.updateAll("name=?and price=?","The Secret","28.99");

                book.setToDefault("name");
                book.setToDefault("author");
                book.updateAll("price=?","28.99");
            }
        });

        deletedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class,"price<?","2");
            }
        });

        querydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = DataSupport.findAll(Book.class);
                for(Book book : books){
                    Log.d("MainActivity","book name is:"+ book.getName());
                    Log.d("MainActivity","book author is:"+ book.getAuthor());
                    Log.d("MainActivity","book pages is:"+ book.getPages());
                    Log.d("MainActivity","book price is:"+ book.getPrice());
                }
            }
        });
    }
}
