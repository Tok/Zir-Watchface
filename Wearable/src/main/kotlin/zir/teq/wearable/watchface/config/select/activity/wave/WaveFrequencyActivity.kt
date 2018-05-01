package zir.teq.wearable.watchface.config.select.activity.wave

import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import zir.teq.wearable.watchface.config.general.adapter.SettingsAdapter
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.model.setting.wave.WaveFrequency
import zir.teq.wearable.watchface.util.ViewHelper


class WaveFrequencyActivity : WavePropsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SettingsAdapter(WaveFrequency)
        manager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(view, adapter, manager)
    }

    override fun onStart() {
        super.onStart()
        val index = WaveFrequency.all.indexOfFirst { it.equals(WaveFrequency.load()) }
        view.smoothScrollToPosition(index + 1)
    }
}
