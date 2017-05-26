package zir.teq.wearable.watchface.config

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.ColorSelectionActivity
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
        when (requestCode) {
            UPDATE_COLORS_CONFIG_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "extra: " + data?.extras?.get(ColorSelectionActivity.EXTRA_SHARED_COLOR))
            }
            //UPDATE_STROKE_CONFIG_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) { doSomething() }
            //UPDATE_THEME_CONFIG_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) { doSomething() }
        }
        Log.d(TAG, "onActivityResult data.dataString: " + data?.dataString)
    }

    companion object {
        private val TAG = ZirWatchConfigActivity::class.java.simpleName
        internal val UPDATE_COLORS_CONFIG_REQUEST_CODE = 1002
        internal val UPDATE_STROKE_CONFIG_REQUEST_CODE = 1003
        internal val UPDATE_THEME_CONFIG_REQUEST_CODE = 1004
    }
}