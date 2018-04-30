package zir.teq.wearable.watchface.config.select.wave.activity

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.adapter.PropsAdapter
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.wave.WaveAmbientResolution
import zir.teq.wearable.watchface.util.ViewHelper


class WaveAmbientResolutionActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: PropsAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        mAdapter = PropsAdapter(WaveAmbientResolution.ALL, WaveAmbientResolution.pref, WaveAmbientResolution.iconId)
        mView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(mView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = WaveAmbientResolution.ALL.indexOfFirst { it.equals(ConfigData.waveAmbientResolution()) }
        mView.smoothScrollToPosition(index + 1)
    }

    companion object {
        internal val CLASS = WaveAmbientResolutionActivity::class.java
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE_AMBIENT_RESOLUTION"
    }
}
