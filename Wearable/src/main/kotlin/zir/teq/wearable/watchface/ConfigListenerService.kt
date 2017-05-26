package zir.watchface

import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.DataMap
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import java.util.concurrent.TimeUnit

class ConfigListenerService :
        WearableListenerService(),
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private val mGoogleApiClient: GoogleApiClient =
            GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Wearable.API).build()

    override fun onMessageReceived(messageEvent: MessageEvent?) {
        if (messageEvent!!.path != WatchFaceUtil.PATH_WITH_FEATURE) {
            return
        }
        val rawData = messageEvent.data
        val configKeysToOverwrite = DataMap.fromByteArray(rawData)
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Received config message: " + configKeysToOverwrite)
        }
        if (!mGoogleApiClient.isConnected) {
            val connectionResult = mGoogleApiClient.blockingConnect(30, TimeUnit.SECONDS)
            if (!connectionResult.isSuccess) {
                Log.e(TAG, "Failed to connect to GoogleApiClient.")
                return
            }
        }
        WatchFaceUtil.putMap(mGoogleApiClient, configKeysToOverwrite)
    }

    override fun onConnected(hint: Bundle?) {
        Log.d(TAG, "onConnected: " + hint!!)
    }

    override fun onConnectionSuspended(cause: Int) {
        Log.d(TAG, "onConnectionSuspended: " + cause)
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        Log.d(TAG, "onConnectionFailed: " + result)
    }

    companion object {
        private val TAG = ConfigListenerService::class.java.simpleName
    }
}
