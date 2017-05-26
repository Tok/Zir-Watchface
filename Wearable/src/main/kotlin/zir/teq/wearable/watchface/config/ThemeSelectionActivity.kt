package zir.teq.wearable.watchface.config

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.util.ActivityHelper


class ThemeSelectionActivity : Activity() {
    private var mConfigView: WearableRecyclerView? = null
    private var mAdapter: ThemeSelectionAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stroke_selection_config)
        val ctx = applicationContext
        val sharedStrokeName = intent.getStringExtra(EXTRA_SHARED_STROKE)
        Log.d(TAG, "sharedStrokeName:" + sharedStrokeName)
        mAdapter =ThemeSelectionAdapter(sharedStrokeName, Stroke.createStrokeOptions(ctx))
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ActivityHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    companion object {
        private val TAG = StrokeSelectionActivity::class.java.simpleName
        internal val EXTRA_SHARED_STROKE = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_STROKE"
    }
}