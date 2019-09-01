package com.example.dbinterlockandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Network.NetWorkDelete;
import Network.NetWorkUpdate;

public class Custom_Adapter extends BaseAdapter {
    private Activity mAct;
    LayoutInflater mInflater;
    ArrayList<UserInfo> mUserInfoObjArr;
    int mListLayout;
    private Custom_Adapter adapter;


    public Custom_Adapter(Activity a, int listLayout, ArrayList<UserInfo>mUserInfoObjArrayListT){
        mAct =a;
        mListLayout =listLayout;
        mUserInfoObjArr = mUserInfoObjArrayListT;
        mInflater = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDatas(ArrayList<UserInfo> arrayList){
        mUserInfoObjArr = arrayList;
    }//데이터를 받아서 set하기 위함

    @Override
    public int getCount() {
        return mUserInfoObjArr.size();
    }

    @Override
    public Object getItem(int i) {
        return mUserInfoObjArr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
            view = mInflater.inflate(mListLayout, viewGroup, false);

        final TextView tvID = (TextView) view.findViewById(R.id.tv_id);
        tvID.setText(mUserInfoObjArr.get(i).id);

        final TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        tvName.setText(mUserInfoObjArr.get(i).name);

        final TextView tvPhone = (TextView) view.findViewById(R.id.tv_phone);
        tvPhone.setText(mUserInfoObjArr.get(i).phone);

        final TextView tvGrade = (TextView) view.findViewById(R.id.tv_grade);
        tvGrade.setText(mUserInfoObjArr.get(i).grade);

        final TextView tvWriteTime = (TextView) view.findViewById(R.id.tv_write_time);
        tvWriteTime.setText(mUserInfoObjArr.get(i).writeTime);

        Button updateButton = (Button) view.findViewById(R.id.btnUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(mAct);
                final View v = mAct.getLayoutInflater().inflate(R.layout.dialog_add, null);

                String id = ((EditText) v.findViewById(R.id.edtID)).getText().toString();
                String name = ((EditText) v.findViewById(R.id.edtName)).getText().toString();
                String phone = ((EditText) v.findViewById(R.id.edtPhone)).getText().toString();
                String grade = ((EditText) v.findViewById(R.id.edtGrade)).getText().toString();

                new NetWorkUpdate(adapter).execute(id, name, phone, grade);


                ad.setTitle("멤버 수정");
                ad.setView(v);
                ad.setMessage("정말 수정하시겠습니까?");
                ad.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {

                        Toast.makeText(mAct,"수정되었습니다", Toast.LENGTH_LONG).show();
                    }
                });
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        Toast.makeText(mAct,"취소하였습니다", Toast.LENGTH_LONG).show();
                    }
                });
                ad.show();

            }
        });

        Button deleteButton = (Button) view.findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = tvID.getText().toString();
                AlertDialog.Builder ad = new AlertDialog.Builder(mAct);
                ad.setTitle("삭제하기");
                ad.setMessage("사용자 ID "+ userID + " 를(을) 정말 삭제하시겠습니까?");

                ad.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        new NetWorkDelete(Custom_Adapter.this).execute(tvID.getText().toString());
                    }
                });
                ad.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        Toast.makeText(mAct,"취소하였습니다", Toast.LENGTH_LONG).show();
                    }
                });
                ad.show();
            }
        });

        return view;
    }
}
