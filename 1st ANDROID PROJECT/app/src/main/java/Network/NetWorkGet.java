package Network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dbinterlockandroid.Custom_Adapter;
import com.example.dbinterlockandroid.UserInfo;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class NetWorkGet extends AsyncTask<String, Void, String> {
    private URL Url;
    private String URL_Address = "http://192.168.0.76:8080/0620/testDB.jsp";
    private Custom_Adapter adapter;

    public NetWorkGet (Custom_Adapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "";
        try {
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
            buffer.append("id").append("=").append(strings[0]);

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
        Log.i("Get result", res);
        return res; //return result
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ArrayList<UserInfo> userList = new ArrayList<UserInfo>();
        int count = 0; //리턴값을 받기위함
        try {
            count = JasonParser.getUserInfoJason(s, userList); //count에 결과값을 받기위함
        }catch (JSONException e){
            e.printStackTrace();
        }
        if(count==0){

        }else {
            adapter.setDatas(userList);
            adapter.notifyDataSetInvalidated();
        }
    }
}
