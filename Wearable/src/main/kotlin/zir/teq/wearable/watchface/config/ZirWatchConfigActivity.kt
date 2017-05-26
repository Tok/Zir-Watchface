package zir.teq.wearable.watchface.config

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.util.ActivityHelper

class ZirWatchConfigActivity : Activity() {
    private var mView: WearableRecyclerView? = null
    private var mAdapter: ZirWatchConfigAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_watch_config_activity)
        mAdapter = ZirWatchConfigAdapter(applicationContext,
                ConfigData.watchFaceServiceClass,
                ConfigData.getDataToPopulateAdapter(this))
        mView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ActivityHelper.initView(mView, mAdapter, CurvedChildLayoutManager(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == UPDATE_COLORS_CONFIG_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "ZirWatchConfigActivity onActivityResult data.dataString: " + data?.dataString)
        }
    }

    companion object {
        private val TAG = ZirWatchConfigActivity::class.java.simpleName
        internal val UPDATE_COLORS_CONFIG_REQUEST_CODE = 1002
    }
}