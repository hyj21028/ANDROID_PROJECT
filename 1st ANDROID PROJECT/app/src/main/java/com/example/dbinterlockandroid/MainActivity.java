package com.example.dbinterlockandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import Network.NetWorkGet;
import Network.NetWorkInsert;

public class MainActivity extends Activity {
    private Button refreshBtn, addBtn;
    private ListView listView;
    private Custom_Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new Custom_Adapter(MainActivity.this, R.layout.adapter_userinfo, new ArrayList<UserInfo>());
        listView.setAdapter(adapter);

        refreshBtn = (Button)findViewById(R.id.btnRefresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NetWorkGet((Custom_Adapter) listView.getAdapter()).execute("");//전체불러오기
            }
        });
        addBtn = (Button) findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final  View v = getLayoutInflater().inflate(R.layout.dialog_add, null);
                new AlertDialog.Builder(MainActivity.this).setTitle("멤버추가").setView(v).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id = ((EditText)v.findViewById(R.id.edtID)).getText().toString();
                        String name = ((EditText)v.findViewById(R.id.edtName)).getText().toString();
                        String phone = ((EditText)v.findViewById(R.id.edtPhone)).getText().toString();
                        String grade = ((EditText)v.findViewById(R.id.edtGrade)).getText().toString();

                        new NetWorkInsert(adapter).execute(id, name, phone, grade);
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setCancelable(false).show();
            }
        });
        new NetWorkGet((Custom_Adapter)listView.getAdapter()).execute("");
    }
}
