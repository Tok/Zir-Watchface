package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.util.ViewHelper


class PaletteSelectionActivity : Activity() {
    private var mConfigView: WearableRecyclerView? = null
    private var mAdapter: PaletteSelectionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_color)
        val sharedColorName = intent.getStringExtra(EXTRA_SHARED_COLOR)
        android.util.Log.d(TAG, "sharedColorName:" + sharedColorName)
        mAdapter = PaletteSelectionAdapter(sharedColorName, Palette.options())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val ctx = applicationContext
        val colName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_palette), Palette.WHITE.name)
        val col = Palette.getByName(colName)
        val index = Palette.all.indexOfFirst { it.equals(col) } + 1
        mConfigView?.smoothScrollToPosition(index)
    }

    companion object {
        private val TAG = this::class.java.simpleName
        internal val EXTRA_SHARED_COLOR = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_COLOR"
    }
}