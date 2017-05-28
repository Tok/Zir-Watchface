package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.util.ViewHelper


class StrokeSelectionActivity : Activity() {
    private var mConfigView: WearableRecyclerView? = null
    private var mAdapter: StrokeSelectionAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_stroke)
        val sharedStrokeName = intent.getStringExtra(EXTRA_SHARED_STROKE)
        android.util.Log.d(TAG, "sharedStrokeName:" + sharedStrokeName)
        val ctx = applicationContext
        mAdapter = StrokeSelectionAdapter(sharedStrokeName, Stroke.createStrokeOptions(ctx))
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    companion object {
        private val TAG = StrokeSelectionActivity::class.java.simpleName
        internal val EXTRA_SHARED_STROKE = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_STROKE"
    }
}