package aitorpagan.starmoviesimdb.Interface;

import java.util.List;

import aitorpagan.starmoviesimdb.Model.Film;

/**
 * Created by aitorpagan on 23/2/17.
 */

public interface FilmContainer {

    public void addFilms(List<Film> newFilms);
    public void startLoading();
    public void stopLoading();
}
