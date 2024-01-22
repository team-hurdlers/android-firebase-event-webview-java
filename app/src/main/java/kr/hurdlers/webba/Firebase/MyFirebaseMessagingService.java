package kr.hurdlers.webba.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.igaworks.v2.core.AdBrixRm;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, String.valueOf(remoteMessage));
        AdBrixRm.onMessageReceived(getApplicationContext(), remoteMessage);
    }

    @Override
    public void onNewToken(@NonNull String token){
        Log.d(TAG, "Refreshed token: " + token);
        AdBrixRm.setRegistrationId(token);
    }
}