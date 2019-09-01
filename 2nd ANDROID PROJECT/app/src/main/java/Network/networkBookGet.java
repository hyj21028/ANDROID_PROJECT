package Network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.project.Adapter;
import com.example.project.BookInfo;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class networkBookGet extends AsyncTask<String, Void, String> {
    private URL Url;
    private String URL_Address ="http://192.168.0.76:8080/Personal_Project_0701/bookDB.jsp";
    private Adapter adapter;

    public networkBookGet (Adapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        try{
            Url = new URL(URL_Address);
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();

            conn.setDefaultUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");


            //content-type 설정
            conn.setRequestProperty("Content-type","application/x-www-form-urlencoded; charset=utf-8");

            //전송값 설정
            StringBuffer buffer = new StringBuffer();
            buffer.append("symbol").append("=").append(strings[0]);

            //서버전송
            OutputStreamWriter outStream = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            StringBuilder builder = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine())!=null){
                builder.append(line + "\n");
            }
            res = builder.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("Get Result", res);
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ArrayList<BookInfo> bookList = new ArrayList<BookInfo>();
        int count = 0;

        try{
            count = bookJsonParser.getBookInfoJson(s, bookList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (count == 0){

        }else{
            adapter.setDatas(bookList);
            adapter.notifyDataSetInvalidated();
        }
    }
}
