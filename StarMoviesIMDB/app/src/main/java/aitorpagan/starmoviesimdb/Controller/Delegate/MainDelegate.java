package aitorpagan.starmoviesimdb.Controller.Delegate;

import android.app.Activity;

import java.util.List;

import aitorpagan.starmoviesimdb.Controller.MainViewController;
import aitorpagan.starmoviesimdb.Model.Film;
import aitorpagan.starmoviesimdb.Tools;

/**
 * Created by aitorpagan on 23/2/17.
 */

public class MainDelegate {

    MainViewController activity;
    private int page;

    public MainViewController getActivity() {
        return activity;
    }

    public void setMainViewController(MainViewController activity) {
        this.activity = activity;
    }

    public MainDelegate(){
        this.page = 1;
    }

    public void updateFilms(){
        activity.container.addFilms(activity.getMockedFilms(Tools.loadJSONFromAsset(activity.getApplicationContext())));
        if(page == 1){
            activity.onResultLoad();
            page++;
        }else{
            activity.onResultBack();
        }

    }

}
