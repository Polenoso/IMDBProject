package aitorpagan.starmoviesimdb.Network;

import android.content.Context;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import aitorpagan.starmoviesimdb.Interface.JSONResponse;
import aitorpagan.starmoviesimdb.Interface.NetworkOperationDelegate;

/**
 * Created by aitorpagan on 24/02/2017.
 */

public class NetworkRequestImpl implements Runnable {

    NetworkOperationDelegate delegateOperation;
    JSONResponse response;
    Context context;
    String url;
    int operation;

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NetworkRequestImpl(NetworkOperationDelegate delegateOperation, JSONResponse response, Context context, String url){
        this.delegateOperation = delegateOperation;
        this.response = response;
        this.context = context;
        this.url = url;
    }

    public void cancelPendingTransactions(){
        Ion.getDefault(context).cancelAll();
    }

    @Override
    public void run() {
        delegateOperation.preExecuteNeworkRequest(operation);
        Ion.with(context).load("GET",url).asJsonObject().withResponse().setCallback(new FutureCallback<Response<JsonObject>>() {
            @Override
            public void onCompleted(Exception e, Response<JsonObject> result) {
                if(null!=result) {
                    try {
                        response.parseJSON(new JSONObject(result.getResult().toString()));
                        delegateOperation.processNetworkResponse(operation, response);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}
