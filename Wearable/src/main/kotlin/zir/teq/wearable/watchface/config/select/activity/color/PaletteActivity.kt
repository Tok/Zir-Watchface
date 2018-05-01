package zir.teq.wearable.watchface.config.select.activity.color

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.config.select.adapter.color.PaletteAdapter
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.color.BackgroundConfigItem
import zir.teq.wearable.watchface.model.data.settings.color.Palette
import zir.teq.wearable.watchface.util.ViewHelper


class PaletteActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: PaletteAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        val sharedColorName = intent.getStringExtra(EXTRA)
        val items = listOf(BackgroundConfigItem()) + Palette.ALL
        mAdapter = PaletteAdapter(sharedColorName, items)
        mConfigView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(mConfigView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = Palette.ALL.indexOfFirst { it.equals(ConfigData.palette()) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_COLOR"
    }
}
