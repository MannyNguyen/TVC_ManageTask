package demo.example.com.tvc_erp.utils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import demo.example.com.tvc_erp.ui.Model;

/**
 * Created by Quysunam on 11/2/2016.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Model.getInstance().callAPIToken(token, "");
    }

}
