package zir.teq.wearable.watchface.config.select.wave.activity

import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import zir.teq.wearable.watchface.config.general.adapter.PropsAdapter
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.wave.WaveAmbientResolution
import zir.teq.wearable.watchface.util.ViewHelper


class WaveAmbientResolutionActivity : WavePropsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PropsAdapter(WaveAmbientResolution.ALL, WaveAmbientResolution.pref, WaveAmbientResolution.iconId)
        manager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(view, adapter, manager)
    }

    override fun onStart() {
        super.onStart()
        val index = WaveAmbientResolution.ALL.indexOfFirst { it.equals(ConfigData.waveAmbientResolution()) }
        view.smoothScrollToPosition(index + 1)
    }

    companion object {
        internal val CLASS = WaveAmbientResolutionActivity::class.java
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE_AMBIENT_RESOLUTION"
    }
}
