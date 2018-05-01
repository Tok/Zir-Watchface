package zir.teq.wearable.watchface.config.select.activity.wave

import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import zir.teq.wearable.watchface.config.general.adapter.SettingsAdapter
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.model.setting.wave.WaveIntensity
import zir.teq.wearable.watchface.util.ViewHelper


class WaveIntensityActivity : WavePropsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SettingsAdapter(WaveIntensity)
        manager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(view, adapter, manager)
    }

    override fun onStart() {
        super.onStart()
        view.smoothScrollToPosition(WaveIntensity.index())
    }
}
