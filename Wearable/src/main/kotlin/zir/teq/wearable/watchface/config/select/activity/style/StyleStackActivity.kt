package zir.teq.wearable.watchface.config.select.activity.style

import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import zir.teq.wearable.watchface.config.general.adapter.SettingsAdapter
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.model.setting.style.StyleStack
import zir.teq.wearable.watchface.util.ViewHelper


class StyleStackActivity : StylePropsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SettingsAdapter(StyleStack, StyleStack.all)
        manager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(view, adapter, manager)
    }

    override fun onStart() {
        super.onStart()
        val index = StyleStack.all.indexOfFirst { it.equals(StyleStack.load()) }
        view.smoothScrollToPosition(index + 1)
    }
}
