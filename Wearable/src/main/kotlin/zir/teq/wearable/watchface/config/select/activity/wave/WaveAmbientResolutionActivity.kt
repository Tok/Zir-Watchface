package zir.teq.wearable.watchface.config.select.activity.wave

import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import zir.teq.wearable.watchface.config.general.adapter.SettingsAdapter
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.model.setting.wave.WaveAmbientResolution
import zir.teq.wearable.watchface.util.ViewHelper


class WaveAmbientResolutionActivity : WavePropsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SettingsAdapter(WaveAmbientResolution)
        manager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(view, adapter, manager)
    }

    override fun onStart() {
        super.onStart()
        view.smoothScrollToPosition(WaveAmbientResolution.index())
    }
}
