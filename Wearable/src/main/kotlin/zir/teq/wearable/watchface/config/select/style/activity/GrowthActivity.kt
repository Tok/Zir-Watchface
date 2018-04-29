package zir.teq.wearable.watchface.config.select.style.activity

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.config.select.style.adapter.GrowthAdapter
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.style.Growth
import zir.teq.wearable.watchface.util.ViewHelper


class GrowthActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: GrowthAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        val sharedGrowthName = intent.getStringExtra(EXTRA)
        mAdapter = GrowthAdapter(sharedGrowthName, Growth.options())
        mConfigView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(mConfigView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = Growth.ALL.indexOfFirst { it.name.equals(ConfigData.style().growth.name) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_GROWTH"
    }
}
