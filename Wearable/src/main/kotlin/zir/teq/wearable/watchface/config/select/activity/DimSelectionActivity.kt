package zir.teq.wearable.watchface.config.select.activity

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ScalingLayoutManager
import zir.teq.wearable.watchface.config.select.adapter.DimSelectionAdapter
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.Dim
import zir.teq.wearable.watchface.util.ViewHelper


class DimSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: DimSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_alpha)
        val sharedDimId = intent.getStringExtra(EXTRA)
        mAdapter = DimSelectionAdapter(sharedDimId, Dim.options())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Dim.all.indexOfFirst { it.name.equals(ConfigData.dim.name) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_DIM"
    }
}