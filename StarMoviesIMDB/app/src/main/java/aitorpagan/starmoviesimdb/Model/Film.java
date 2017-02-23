package aitorpagan.starmoviesimdb.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aitorpagan on 23/2/17.
 */

public class Film {

    private String poster_path;
    private String overview;
    private String release_date;
    private String title;

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Film fromJSON(JSONObject json){
        Film film = new Film();
        try {
            film.setOverview(json.getString("overview"));
            film.setRelease_date(json.getString("release_date"));
            film.setTitle(json.getString("title"));
            film.setPoster_path(json.getString("poster_path"));
        } catch (JSONException e) {
            e.printStackTrace();
            film = null;
        }

        return film;
    }
}
