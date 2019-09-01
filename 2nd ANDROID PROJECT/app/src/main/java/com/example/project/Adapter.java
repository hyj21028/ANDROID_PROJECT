package com.example.project;

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

import Network.networkBookDelete;
import Network.networkBookInsert;
import Network.networkBookUpdate;

public class Adapter extends BaseAdapter {
    private Activity mAct;
    LayoutInflater mInflater;
    ArrayList<BookInfo> mBookInfoObjArr;
    int mListLayout;
    private Adapter adapter;

    public Adapter(Activity act, int listLayout, ArrayList<BookInfo> mBookInfoObjArrayListT ){
        mAct =act;
        mListLayout =listLayout;
        mBookInfoObjArr = mBookInfoObjArrayListT;
        mInflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setDatas(ArrayList<BookInfo> arrayList){

        mBookInfoObjArr = arrayList;
    }

    @Override
    public int getCount() {
        return mBookInfoObjArr.size();
    }

    @Override
    public Object getItem(int i) {
        return mBookInfoObjArr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
            view = mInflater.inflate(mListLayout, viewGroup, false);

        final TextView tvBookSymbol = (TextView)view.findViewById(R.id.tv_BookSymbol);
        tvBookSymbol.setText(mBookInfoObjArr.get(i).symbol);

        final TextView tvBookName = (TextView)view.findViewById(R.id.tv_BookName);
        tvBookName.setText(mBookInfoObjArr.get(i).name);

        final TextView tvBookAuthor = (TextView)view.findViewById(R.id.tv_BookAuthor);
        tvBookAuthor.setText(mBookInfoObjArr.get(i).author);

        final TextView tvBookPublisher = (TextView)view.findViewById(R.id.tv_BookPublisher);
        tvBookPublisher.setText(mBookInfoObjArr.get(i).publisher);


        Button insertButton = (Button) view.findViewById(R.id.btnInsert);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(mAct);
                final View v = mAct.getLayoutInflater().inflate(R.layout.dialog_add, null);

                String bookSymbol = ((EditText) v.findViewById(R.id.edtBookSymbol)).getText().toString();
                String bookName = ((EditText) v.findViewById(R.id.edtBookName)).getText().toString();
                String bookAuthor = ((EditText) v.findViewById(R.id.edtBookAuthor)).getText().toString();
                String bookPublisher = ((EditText) v.findViewById(R.id.edtBookPublisher)).getText().toString();

                new networkBookInsert(adapter).execute(bookSymbol, bookName, bookAuthor, bookPublisher);


                ad.setTitle("도서 추가");
                ad.setView(v);
                ad.setMessage("추가 하시겠습니까?");
                ad.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {

                        Toast.makeText(mAct,"추되었습니다", Toast.LENGTH_LONG).show();
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

        Button updateButton = (Button) view.findViewById(R.id.btnUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(mAct);
                final View v = mAct.getLayoutInflater().inflate(R.layout.dialog_add, null);

                String bookSymbol = ((EditText) v.findViewById(R.id.edtBookSymbol)).getText().toString();
                String bookName = ((EditText) v.findViewById(R.id.edtBookName)).getText().toString();
                String bookAuthor = ((EditText) v.findViewById(R.id.edtBookAuthor)).getText().toString();
                String bookPublisher = ((EditText) v.findViewById(R.id.edtBookPublisher)).getText().toString();

                new networkBookUpdate(adapter).execute(bookSymbol, bookName, bookAuthor, bookPublisher);


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
                String bookSymbol = tvBookSymbol.getText().toString();
                AlertDialog.Builder ad = new AlertDialog.Builder(mAct);
                ad.setTitle("삭제하기");
                ad.setMessage("이 책을 정말 삭제하시겠습니까?");

                ad.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        new networkBookDelete(Adapter.this).execute(tvBookSymbol.getText().toString());
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
