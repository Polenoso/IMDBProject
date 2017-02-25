package aitorpagan.starmoviesimdb.Network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
        try{
            Ion.getDefault(context).cancelAll(context);
        }catch (Exception e){
            Log.e("cancel",e.getMessage());
        }

    }

    @Override
    public void run() {
        delegateOperation.preExecuteNeworkRequest(operation);
        Ion.with(context).load("GET",url.replaceAll("\\s","%20")).noCache().asJsonObject().withResponse().setCallback(new FutureCallback<Response<JsonObject>>() {
            @Override
            public void onCompleted(Exception e, Response<JsonObject> result) {
                if(null!=result) {
                    try {
                        response.parseJSON(new JSONObject(result.getResult().toString()));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        Toast.makeText(context,result.toString(),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    response = null;
                }
                delegateOperation.processNetworkResponse(operation, response);
            }
        });
    }
}
