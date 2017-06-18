package zir.teq.wearable.watchface.config.select.activity

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import config.select.adapter.StrokeSelectionAdapter
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.Stroke
import zir.teq.wearable.watchface.util.ViewHelper


class StrokeSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: StrokeSelectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_stroke)
        val sharedStrokeName = intent.getStringExtra(EXTRA)
        mAdapter = StrokeSelectionAdapter(sharedStrokeName, Stroke.options())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Stroke.all.indexOfFirst { it.name.equals(ConfigData.stroke.name) }
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_STROKE"
    }
}