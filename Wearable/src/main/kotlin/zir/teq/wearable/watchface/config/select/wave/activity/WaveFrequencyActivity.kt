package zir.teq.wearable.watchface.config.select.wave.activity

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.wave.adapter.WaveFrequencyAdapter
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.WaveFrequency
import zir.teq.wearable.watchface.model.data.types.WaveProps
import zir.teq.wearable.watchface.util.ViewHelper


class WaveFrequencyActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: WaveFrequencyAdapter
    private lateinit var mManager: WearableLinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        mAdapter = WaveFrequencyAdapter(WaveFrequency.ALL)
        mView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = WaveFrequency.ALL.indexOfFirst { it.equals(ConfigData.waveFrequency()) }
        mView.smoothScrollToPosition(index)
    }

    companion object {
        internal val CLASS = WaveFrequencyActivity::class.java
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE_FREQUENCY"
    }
}
