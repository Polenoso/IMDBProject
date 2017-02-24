package aitorpagan.starmoviesimdb.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import aitorpagan.starmoviesimdb.Controller.Adapater.FilmAdapter;
import aitorpagan.starmoviesimdb.Controller.Delegate.MainDelegate;
import aitorpagan.starmoviesimdb.Controller.Delegate.TextWatcherDelegate;
import aitorpagan.starmoviesimdb.Interface.FilmContainer;
import aitorpagan.starmoviesimdb.R;


/**
 * Created by aitorpagan on 23/2/17.
 */

public class MainViewController extends Activity implements View.OnClickListener{

    private RecyclerView recyclerView;
    MainDelegate delegate;
    public FilmContainer container;
    EditText searchText;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        delegate = new MainDelegate();
        delegate.setMainViewController(this);

        container = new FilmAdapter(getApplicationContext());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                delegate.updateFilms();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 1000);

    }


    public void onResultLoad() {
        setContentView(R.layout.main_view_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final RecyclerView r = recyclerView;
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (lastItemPosition >= totalItemCount - 1 && !((FilmAdapter) recyclerView.getAdapter()).getmIsLoading()) {
                    delegate.updateFilms();
                }
            }

        });
        recyclerView.setAdapter((RecyclerView.Adapter) container);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.getAdapter().notifyDataSetChanged();
        searchButton = (Button) findViewById(R.id.search_button_view);
        searchButton.setOnClickListener(this);
        searchText = (EditText) findViewById(R.id.search_text_view);
        searchText.addTextChangedListener(new TextWatcherDelegate(this.getApplicationContext(),this));
    }

    @Override
    public void onClick(View v) {
        if(v == searchButton){
            searchText.setVisibility(View.VISIBLE);
        }
    }

    public void searchBegan(){
        ((FilmAdapter)this.recyclerView.getAdapter()).removeAll();
    }

    public void searchEnd(){
        ((FilmAdapter)this.recyclerView.getAdapter()).removeAll();
        delegate.updateFilms();
    }
}