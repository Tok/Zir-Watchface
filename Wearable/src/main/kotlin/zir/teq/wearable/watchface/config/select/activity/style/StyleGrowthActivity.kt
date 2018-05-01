package zir.teq.wearable.watchface.config.select.activity.style

import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import zir.teq.wearable.watchface.config.general.adapter.SettingsAdapter
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.StyleDim
import zir.teq.wearable.watchface.model.setting.StyleGrowth
import zir.teq.wearable.watchface.util.ViewHelper


class StyleGrowthActivity : StylePropsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SettingsAdapter(StyleGrowth, StyleGrowth.all)
        manager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(view, adapter, manager)
    }

    override fun onStart() {
        super.onStart()
        val index = StyleGrowth.all.indexOfFirst { it.equals(StyleGrowth.load()) }
        view.smoothScrollToPosition(index + 1)
    }
}