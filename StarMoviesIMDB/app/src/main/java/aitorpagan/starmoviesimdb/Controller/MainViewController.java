package aitorpagan.starmoviesimdb.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aitorpagan.starmoviesimdb.Controller.Adapater.FilmAdapter;
import aitorpagan.starmoviesimdb.Model.Film;
import aitorpagan.starmoviesimdb.R;
import aitorpagan.starmoviesimdb.Tools;


/**
 * Created by aitorpagan on 23/2/17.
 */

public class MainViewController extends Activity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FilmAdapter adapter = new FilmAdapter(getApplicationContext());
        adapter.setFilms(getMockedFilms(Tools.loadJSONFromAsset(getApplicationContext())));
        recyclerView.setAdapter(adapter);

    }

    public List<Film> getMockedFilms(JSONObject jsonObject){
        List<Film> films = new ArrayList<Film>(0);

        try {
            JSONArray array = jsonObject.getJSONArray("results");
            for(int i = 0; i < array.length(); i++){
                films.add(Film.fromJSON(array.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return films;
    }

}
