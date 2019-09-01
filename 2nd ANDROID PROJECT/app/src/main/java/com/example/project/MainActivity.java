package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import Network.networkBookGet;

public class MainActivity extends Activity {
    private ListView listView;
    private Button searchBtn;
    private Adapter adapter;
    private Intent intent;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list1);
        adapter = new Adapter(MainActivity.this, R.layout.adapter_bookinfo, new ArrayList<BookInfo>());
        listView.setAdapter(adapter);

        intent =new Intent(this, LoadingActivity.class);
        startActivity(intent);

        searchBtn =(Button)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new networkBookGet((Adapter)listView.getAdapter()).execute("");
                Toast.makeText(getApplicationContext(),"검색완료되었습니다",toast.LENGTH_LONG).show();

            }
        });
      //  new networkBookGet((Adapter) listView.getAdapter()).execute("");
    }
}
