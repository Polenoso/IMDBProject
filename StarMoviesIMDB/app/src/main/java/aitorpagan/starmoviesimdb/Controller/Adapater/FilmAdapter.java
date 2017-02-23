package aitorpagan.starmoviesimdb.Controller.Adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import aitorpagan.starmoviesimdb.Interface.FilmContainer;
import aitorpagan.starmoviesimdb.Model.Film;
import aitorpagan.starmoviesimdb.R;

/**
 * Created by aitorpagan on 23/2/17.
 */

public class FilmAdapter extends RecyclerView.Adapter implements FilmContainer{

    List<Film> films;
    Context context;


    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public FilmAdapter(Context context){
        this.context = context;
        this.films = new ArrayList<>(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_view_layout, parent, false);
        FilmHolder vh = new FilmHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FilmHolder filmHolder = (FilmHolder) holder;
        final int pos = position; //for calling in callback
        filmHolder.overviewView.setText(getFilms().get(position).getOverview());
        filmHolder.titleView.setText(getFilms().get(position).getTitle());
        //Needs to cast date to only year
        filmHolder.releaseDateView.setText(getFilms().get(position).getRelease_date());
        //Needs to call to download image where setting image to holder.
        final Picasso.Builder builder = new Picasso.Builder(context);
        builder.indicatorsEnabled(true);
        builder.loggingEnabled(true);
        //We make a callBack to manage in case Cache is cleaned.
        builder.build().load(context.getResources().getString(R.string.images_url)+getFilms().get(position).getPoster_path()).networkPolicy(NetworkPolicy.OFFLINE).into(filmHolder.movieImageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                //if there is no cache, get the image from the server
                builder.build().load("https://image.tmdb.org/t/p/w500/"+getFilms().get(pos).getPoster_path()).error(R.drawable.img_example).into(filmHolder.movieImageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    @Override
    public void addFilms(List<Film> newFilms) {
        this.films.addAll(newFilms);
        this.notifyDataSetChanged();
    }


    private static class FilmHolder extends RecyclerView.ViewHolder{


        public TextView titleView;
        public ImageView movieImageView;
        public TextView releaseDateView;
        public TextView overviewView;
        public FilmHolder(View v){
            super(v);
            titleView = (TextView) v.findViewById(R.id.movie_title);
            movieImageView = (ImageView) v.findViewById(R.id.movie_image);
            releaseDateView = (TextView) v.findViewById(R.id.release_year);
            overviewView = (TextView) v.findViewById(R.id.overview_text);
        }

    }
}
