package com.example.yandextranslator;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;

import com.example.yandextranslator.utils.Db;

import static android.support.v7.appcompat.R.styleable.View;

public class LangList extends AppCompatActivity  {

    ListView lvData;
    Cursor cursor;
    Db db;
    SimpleCursorAdapter crAdapter;
    String itemName;
    private static final String TAG = "MyLog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang_list);

        db = new Db(this);
        cursor = db.getAllItems();

        String[] from = new String[] { Db.LANG_NAME, Db.LANG_CODE };
        int[] to = new int[] { R.id.tvName, R.id.tvEmail };

        crAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to, 0);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(crAdapter);
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Cursor c = (Cursor)lvData.getItemAtPosition(position);

                itemName = cursor.getString(1);
                intent.putExtra("name", itemName);
                setResult(RESULT_OK, intent);
                finish();

                Log.d(TAG, "itemClick: position = " + position + ", id = "
                        + id +  itemName);
            }
        });
        db.close();


    }


}
