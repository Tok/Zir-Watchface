package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ScalingLayoutManager
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.util.ViewHelper


class PaletteSelectionActivity : Activity() {
    private var mConfigView: WearableRecyclerView? = null
    private var mAdapter: PaletteSelectionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_palette)
        val sharedColorName = intent.getStringExtra(EXTRA_SHARED_COLOR)
        mAdapter = PaletteSelectionAdapter(sharedColorName, Palette.options())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val ctx = applicationContext
        val colName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_palette), Palette.WHITE.name)
        val col = Palette.getByName(colName)
        val index = Palette.selectable.indexOfFirst { it.equals(col) } + 1
        mConfigView?.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA_SHARED_COLOR = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_COLOR"
    }
}