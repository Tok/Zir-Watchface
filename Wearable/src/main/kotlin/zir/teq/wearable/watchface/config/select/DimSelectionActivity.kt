package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ScalingLayoutManager
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Dim
import zir.teq.wearable.watchface.util.ViewHelper


class DimSelectionActivity : Activity() {
    private var mConfigView: WearableRecyclerView? = null
    private var mAdapter: DimSelectionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_alpha)
        val sharedDimId = intent.getStringExtra(EXTRA_SHARED_DIM)
        mAdapter = DimSelectionAdapter(sharedDimId, Dim.options())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val ctx = applicationContext
        val name = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_dim), Dim.default.name)
        val index = Dim.all.indexOfFirst { it.name.equals(name) } + 1
        mConfigView?.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA_SHARED_DIM = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_DIM"
    }
}