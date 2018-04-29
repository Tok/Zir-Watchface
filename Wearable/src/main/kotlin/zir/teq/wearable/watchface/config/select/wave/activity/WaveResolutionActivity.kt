package zir.teq.wearable.watchface.config.select.wave.activity

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.wave.adapter.WaveResolutionAdapter
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.WaveResolution
import zir.teq.wearable.watchface.util.ViewHelper


class WaveResolutionActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: WaveResolutionAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        mAdapter = WaveResolutionAdapter(WaveResolution.ALL)
        mView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = WaveResolution.ALL.indexOfFirst { it.equals(ConfigData.waveResolution()) }
        mView.smoothScrollToPosition(index + 1)
    }

    companion object {
        internal val CLASS = WaveResolutionActivity::class.java
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE_RESOLUTION"
    }
}
