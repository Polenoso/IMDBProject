package aitorpagan.starmoviesimdb.Controller.Delegate;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import aitorpagan.starmoviesimdb.Controller.MainViewController;
import aitorpagan.starmoviesimdb.Network.NetworkConstants;

/**
 * Created by aitorpagan on 24/2/17.
 */

public class TextWatcherDelegate implements TextWatcher{

    Context context;
    MainViewController activity;
    MainDelegate delegate;

    public TextWatcherDelegate(Context context, MainViewController activity,MainDelegate delegate){
        this.context = context;
        this.delegate = delegate;
        this.activity = activity;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.i("before","textChange");
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i("on","textChange");
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.i("after","textChange");
        if(!s.toString().isEmpty()) {
           delegate.updateFilms(NetworkConstants.NEWSEARCH_OP,s.toString());
        }else{
            activity.searchEnd();
        }
    }

}
