package zir.teq.wearable.watchface.config.select.wave.activity

import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import zir.teq.wearable.watchface.config.general.adapter.SettingsAdapter
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.WaveVelocity
import zir.teq.wearable.watchface.util.ViewHelper


class WaveVelocityActivity : WavePropsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SettingsAdapter(WaveVelocity, WaveVelocity.all)
        manager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(view, adapter, manager)
    }

    override fun onStart() {
        super.onStart()
        val index = WaveVelocity.all.indexOfFirst { it.equals(ConfigData.waveVelocity()) }
        view.smoothScrollToPosition(index + 1)
    }
}
