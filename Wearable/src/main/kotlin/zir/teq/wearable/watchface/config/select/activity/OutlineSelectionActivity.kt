package zir.teq.wearable.watchface.config.select.activity

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.adapter.OutlineSelectionAdapter
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Outline
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.util.ViewHelper

class OutlineSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: OutlineSelectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_outline)
        val sharedOutlineName = intent.getStringExtra(EXTRA)
        mAdapter = OutlineSelectionAdapter(sharedOutlineName, Outline.options(applicationContext))
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val ctx = applicationContext
        val outlineName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_outline), Theme.default.outlineName)
        val index = Outline.all.indexOfFirst { it.name.equals(outlineName) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_OUTLINE"
    }
}