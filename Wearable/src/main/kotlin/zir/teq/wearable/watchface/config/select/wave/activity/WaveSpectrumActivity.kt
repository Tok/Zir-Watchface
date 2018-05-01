package zir.teq.wearable.watchface.config.select.wave.activity

import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import zir.teq.wearable.watchface.config.general.adapter.PropsAdapter
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.wave.Spectrum
import zir.teq.wearable.watchface.util.ViewHelper


class WaveSpectrumActivity : WavePropsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PropsAdapter(Spectrum.ALL, Spectrum.pref, Spectrum.iconId)
        manager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(view, adapter, manager)
    }

    override fun onStart() {
        super.onStart()
        val index = Spectrum.ALL.indexOfFirst { it.equals(ConfigData.waveSpectrum()) }
        view.smoothScrollToPosition(index + 1)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE_SPECTRUM"
    }
}
