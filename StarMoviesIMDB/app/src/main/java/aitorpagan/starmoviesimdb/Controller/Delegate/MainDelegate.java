package aitorpagan.starmoviesimdb.Controller.Delegate;

import android.os.Handler;


import aitorpagan.starmoviesimdb.Controller.MainViewController;
import aitorpagan.starmoviesimdb.Interface.JSONResponse;
import aitorpagan.starmoviesimdb.Interface.NetworkOperationDelegate;
import aitorpagan.starmoviesimdb.Model.FilmResponse;
import aitorpagan.starmoviesimdb.Network.NetworkConstants;
import aitorpagan.starmoviesimdb.Network.NetworkRequestImpl;
import aitorpagan.starmoviesimdb.R;

/**
 * Created by aitorpagan on 23/2/17.
 */

public class MainDelegate implements NetworkOperationDelegate {

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
        String url = getActivity().getResources().getString(R.string.discover_url);
        String apikey = getActivity().getResources().getString(R.string.api_key);
        url = String.format(url,apikey);
        url = url + page;
        NetworkRequestImpl networkRequest = new NetworkRequestImpl(this,new FilmResponse(),activity.getApplicationContext(),url);
        networkRequest.setOperation(NetworkConstants.DISCOVER_OP);
        Handler handler = new Handler();
        handler.post(networkRequest);

    }

    @Override
    public void preExecuteNeworkRequest() {
        if(page > 1){
            activity.container.startLoading();
        }
    }

    @Override
    public void processNetworkResponse(int operation, JSONResponse response) {

        if(operation == NetworkConstants.DISCOVER_OP){
            if(page > 1){
                activity.container.stopLoading();
            }else{
                activity.onResultLoad();
            }
            activity.container.addFilms(((FilmResponse)response).getFilms());
            page++;
        }

    }
}
