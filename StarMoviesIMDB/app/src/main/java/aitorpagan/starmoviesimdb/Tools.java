package aitorpagan.starmoviesimdb;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aitorpagan on 23/2/17.
 */

public class Tools {
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
}
