package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ScalingLayoutManager
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.util.ViewHelper

class ThemeSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: ThemeSelectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_theme)
        val sharedThemeName = intent.getStringExtra(EXTRA)
        mAdapter = ThemeSelectionAdapter(sharedThemeName, Theme.options())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val ctx = applicationContext
        val themeName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_theme), Theme.default.name)
        val index = Theme.all.indexOfFirst { it.name.equals(themeName) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_THEME"
    }
}