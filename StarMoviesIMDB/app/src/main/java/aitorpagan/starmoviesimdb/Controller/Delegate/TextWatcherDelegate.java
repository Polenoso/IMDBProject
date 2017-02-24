package aitorpagan.starmoviesimdb.Controller.Delegate;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import aitorpagan.starmoviesimdb.Controller.MainViewController;
import aitorpagan.starmoviesimdb.Interface.JSONResponse;
import aitorpagan.starmoviesimdb.Interface.NetworkOperationDelegate;
import aitorpagan.starmoviesimdb.Model.FilmResponse;
import aitorpagan.starmoviesimdb.Network.NetworkRequestImpl;
import aitorpagan.starmoviesimdb.R;

/**
 * Created by aitorpagan on 24/2/17.
 */

public class TextWatcherDelegate implements TextWatcher, NetworkOperationDelegate{

    Context context;
    Handler handler;
    Boolean isPreviousRunning;
    NetworkRequestImpl httpReq;
    MainViewController activity;

    public TextWatcherDelegate(Context context, MainViewController activity){
        this.context = context;
        this.handler = new Handler();
        this.isPreviousRunning = false;
        httpReq = new NetworkRequestImpl(this, new FilmResponse(),this.context,null);
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
        if(isPreviousRunning){
            handler.removeCallbacks(httpReq);
        }
        if(!s.toString().isEmpty()) {
            String url = this.context.getResources().getString(R.string.search_url);
            String apikey = this.context.getResources().getString(R.string.api_key);
            url = String.format(url, apikey, "", "1");
            httpReq.setUrl(url);
            handler.post(httpReq);
        }else{
            activity.searchEnd();
        }
    }

    @Override
    public void preExecuteNeworkRequest() {
        activity.searchBegan();
    }

    @Override
    public void processNetworkResponse(int operation, JSONResponse response) {
        activity.container.stopLoading();
        activity.container.addFilms(((FilmResponse)response).getFilms());
    }
}
