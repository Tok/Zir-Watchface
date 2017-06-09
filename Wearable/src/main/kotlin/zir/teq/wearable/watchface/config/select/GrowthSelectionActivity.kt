package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Growth
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.util.ViewHelper

class GrowthSelectionActivity : Activity() {
    private var mConfigView: WearableRecyclerView? = null
    private var mAdapter: GrowthSelectionAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_growth)
        val sharedGrowthName = intent.getStringExtra(EXTRA_SHARED_GROWTH)
        mAdapter = GrowthSelectionAdapter(sharedGrowthName, Growth.options(applicationContext))
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val ctx = applicationContext
        val growthName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_growth), Theme.default.growthName)
        val index = Growth.all.indexOfFirst { it.name.equals(growthName) } + 1
        mConfigView?.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA_SHARED_GROWTH = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_GROWTH"
    }
}