package zir.teq.wearable.watchface.config.select.activity.main

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.types.WaveItem
import zir.teq.wearable.watchface.config.select.adapter.WavePropsAdapter
import zir.teq.wearable.watchface.util.ViewHelper

class MainWaveActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: WavePropsAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        mAdapter = WavePropsAdapter(WaveItem.all)
        mView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mView, mAdapter, mManager)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE_PROPS"
    }
}
