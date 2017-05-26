package zir.teq.wearable.watchface.config.select

import android.support.wearable.view.CurvedChildLayoutManager
import zir.teq.wearable.watchface.model.data.Stroke


class StrokeSelectionActivity : android.app.Activity() {
    private var mConfigView: android.support.wearable.view.WearableRecyclerView? = null
    private var mAdapter: zir.teq.wearable.watchface.config.StrokeSelectionAdapter? = null
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(zir.teq.wearable.watchface.R.layout.stroke_selection_config)
        val sharedStrokeName = intent.getStringExtra(zir.teq.wearable.watchface.config.select.StrokeSelectionActivity.Companion.EXTRA_SHARED_STROKE)
        android.util.Log.d(zir.teq.wearable.watchface.config.select.StrokeSelectionActivity.Companion.TAG, "sharedStrokeName:" + sharedStrokeName)
        val ctx = applicationContext
        mAdapter = zir.teq.wearable.watchface.config.StrokeSelectionAdapter(sharedStrokeName, Stroke.createStrokeOptions(ctx))
        mConfigView = findViewById(zir.teq.wearable.watchface.R.id.wearable_recycler_view) as android.support.wearable.view.WearableRecyclerView
        zir.teq.wearable.watchface.util.ActivityHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    companion object {
        private val TAG = zir.teq.wearable.watchface.config.select.StrokeSelectionActivity::class.java.simpleName
        internal val EXTRA_SHARED_STROKE = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_STROKE"
    }
}