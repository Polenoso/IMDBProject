package aitorpagan.starmoviesimdb.Controller.Adapater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    Picasso.Builder builder;
    Boolean mIsLoading;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public Boolean getmIsLoading() {
        return mIsLoading;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public FilmAdapter(Context context){
        this.context = context;
        this.films = new ArrayList<>(0);
        builder = new Picasso.Builder(this.context);
        Picasso.setSingletonInstance(builder.build());
        builder.indicatorsEnabled(true);
        builder.loggingEnabled(true);
        mIsLoading = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = null;
        if(viewType == VIEW_TYPE_ITEM){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_view_layout, parent, false);
            return new FilmHolder(v);
        }else if(viewType == VIEW_TYPE_LOADING){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(v);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position){
        return getFilms().get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  FilmHolder){
            FilmHolder filmHolder = (FilmHolder) holder;
            filmHolder.overviewView.setText(getFilms().get(position).getOverview());
            filmHolder.titleView.setText(getFilms().get(position).getTitle());
            //Needs to cast date to only year
            filmHolder.releaseDateView.setText(getFilms().get(position).getRelease_date());
            //Needs to call to download image where setting image to holder.
            Picasso.with(context).load(context.getResources().getString(R.string.images_url) + getFilms().get(position).getPoster_path()).resize(150,250).into(filmHolder.movieImageView);
        }else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
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

    @Override
    public void startLoading(){
        this.films.add(null);
        this.mIsLoading = true;
        this.notifyDataSetChanged();
    }

    @Override
    public void stopLoading(){
        if(films.size() > 0) {
            this.films.remove(films.size() - 1);
            this.mIsLoading = false;
            this.notifyDataSetChanged();
        }
    }

    public void removeAll(){
        this.films = new ArrayList<>(0);
        this.films.add(null);
        this.mIsLoading = true;
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

    private  static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

}
