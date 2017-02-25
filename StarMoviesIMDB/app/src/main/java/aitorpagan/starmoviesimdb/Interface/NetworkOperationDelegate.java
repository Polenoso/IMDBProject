package aitorpagan.starmoviesimdb.Interface;

/**
 * Created by aitorpagan on 24/02/2017.
 */

public interface NetworkOperationDelegate {
    public void preExecuteNeworkRequest(int operation);
    public void processNetworkResponse(int operation, JSONResponse response);
}
