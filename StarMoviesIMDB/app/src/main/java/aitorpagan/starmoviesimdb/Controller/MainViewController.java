package aitorpagan.starmoviesimdb.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aitorpagan.starmoviesimdb.Controller.Adapater.FilmAdapter;
import aitorpagan.starmoviesimdb.Controller.Delegate.MainDelegate;
import aitorpagan.starmoviesimdb.Interface.FilmContainer;
import aitorpagan.starmoviesimdb.Model.Film;
import aitorpagan.starmoviesimdb.R;


/**
 * Created by aitorpagan on 23/2/17.
 */

public class MainViewController extends Activity{

    private RecyclerView recyclerView;
    MainDelegate delegate;
    public FilmContainer container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        delegate = new MainDelegate();
        delegate.setMainViewController(this);

        container = new FilmAdapter(getApplicationContext());
        //FilmAdapter adapter = new FilmAdapter(getApplicationContext());
        //adapter.setFilms(getMockedFilms(Tools.loadJSONFromAsset(getApplicationContext())));

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                delegate.updateFilms();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable,1000);

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

    public void onResultLoad(){
        setContentView(R.layout.main_view_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewlist);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                    int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    int totalItemCount =  recyclerView.getLayoutManager().getItemCount();
                    if (visibleItemCount >= totalItemCount) {
                        delegate.updateFilms();
                    }
                }

        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter((RecyclerView.Adapter) container);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void onResultBack(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }

}
