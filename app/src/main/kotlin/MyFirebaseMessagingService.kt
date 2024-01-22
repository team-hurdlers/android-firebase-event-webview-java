package kr.hurdlers.webba.Firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.igaworks.v2.core.AdBrixRm

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCM"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, remoteMessage.toString())
        AdBrixRm.onMessageReceived(applicationContext, remoteMessage)
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        AdBrixRm.setRegistrationId(token)
    }
}
