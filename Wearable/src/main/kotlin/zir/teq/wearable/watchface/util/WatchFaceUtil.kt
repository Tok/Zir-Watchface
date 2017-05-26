package zir.watchface

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.wearable.*


object WatchFaceUtil {
    private val TAG = WatchFaceUtil::class.java.simpleName
    val PATH_WITH_FEATURE = "/watch_face_config/ZirteqWatch"

    fun putMap(mGoogleApiClient: GoogleApiClient, newConfig: DataMap): PendingResult<DataApi.DataItemResult>? {
        val putDataMapReq = PutDataMapRequest.create(PATH_WITH_FEATURE)
        for (key in newConfig.keySet()) {
            putDataMapReq.dataMap.putString(key, newConfig.get(key))
        }
        return put(mGoogleApiClient, putDataMapReq)
    }

    private fun put(mGoogleApiClient: GoogleApiClient, req: PutDataMapRequest): PendingResult<DataApi.DataItemResult>? {
        val putDataReq: PutDataRequest = req.asPutDataRequest()
        return Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq)
    }
}
