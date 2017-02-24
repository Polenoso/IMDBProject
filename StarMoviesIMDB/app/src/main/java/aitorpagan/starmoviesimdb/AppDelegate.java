package aitorpagan.starmoviesimdb;

import android.app.Application;

import com.koushikdutta.ion.Ion;

/**
 * Created by aitor.pagannarro on 24/02/2017.
 */

public class AppDelegate extends Application {

    public Ion ion;

    @Override
    public void onCreate(){
        super.onCreate();
        ion.build(this);
    }

}
