package aitorpagan.starmoviesimdb.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aitorpagan.starmoviesimdb.Interface.JSONParser;
import aitorpagan.starmoviesimdb.Interface.JSONResponse;

/**
 * Created by aitorpagan on 24/02/2017.
 */

public class FilmResponse implements JSONResponse {

    List<Film> films;
    JSONParser body;

    public JSONParser getBody() {
        return body;
    }

    public void setBody(JSONParser body) {
        this.body = body;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    @Override
    public void parseJSON(JSONObject object) {
        this.films = new ArrayList<>(0);;

        try {
            JSONArray array = object.getJSONArray("results");
            for(int i = 0; i < array.length(); i++){
                films.add(Film.fromJSON(array.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
