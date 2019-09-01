package Network;

import com.example.project.BookInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class bookJsonParser {
    static public int getBookInfoJson(String response, ArrayList<BookInfo> bookList) throws JSONException {

        String strBookSymbol;
        String strBookName;
        String strBookAuthor;
        String strBookPublisher;

        JSONObject rootJSON = new JSONObject(response);
        JSONArray jsonArray = new JSONArray(rootJSON.getString("bookInfo"));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = (JSONObject) jsonArray.get(i);

            if (jsonObj.getString("SYMBOL").toString().equals("null"))
                strBookSymbol = "-";
            else
                strBookSymbol = jsonObj.getString("SYMBOL").toString();

            if (jsonObj.getString("NAME").toString().equals("null"))
                strBookName = "-";
            else
                strBookName = jsonObj.getString("NAME").toString();

            if (jsonObj.getString("AUTHOR").toString().equals("null"))
                strBookAuthor = "-";
            else
                strBookAuthor = jsonObj.getString("AUTHOR").toString();

            if (jsonObj.getString("PUBLISHER").toString().equals("null"))
                strBookPublisher = "-";
            else {
                strBookPublisher = jsonObj.getString("PUBLISHER").toString();
            }
            bookList.add(new BookInfo(strBookSymbol, strBookName, strBookAuthor, strBookPublisher));

        }
        return jsonArray.length();
    }
    static public int getResultJson(String response) throws JSONException{
        JSONArray jsonArray =new JSONArray((response));
        JSONObject jsonObject = new JSONObject(jsonArray.getString(0));
        return  Integer.parseInt(jsonObject.getString("RESULT_OK"));
    }


}

