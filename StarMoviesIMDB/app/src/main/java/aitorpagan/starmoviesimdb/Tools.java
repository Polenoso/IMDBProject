package aitorpagan.starmoviesimdb;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aitorpagan on 23/2/17.
 */

public class Tools {
    /*
        Method to get the json example response of movies
     */
    public static JSONObject loadJSONFromAsset(Context context) {
        String json = null;
        JSONObject obj = null;
        try {

            InputStream is =  context.getResources().openRawResource(R.raw.response);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
            obj = new JSONObject(json);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;

    }

    /*
        @method hideKeyboard
        @param activity from where is called

        hides the keyboard
     */
    public static void hideKeyboard(Activity activity){
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    /*
        @method getYearFromStringDate
        @param dateString String date in string
        @param format String format of date in string

        @return response String with the year or dateString if error occurs
     */
    public static String getYearFromStirngDate(String dateString, String format){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyLocalizedPattern(format);
        String response = dateString;
        try {
            Date date = sdf.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            response = String.valueOf(calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return response;
    }
}
