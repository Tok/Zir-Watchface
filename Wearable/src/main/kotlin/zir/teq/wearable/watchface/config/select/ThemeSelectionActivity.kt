package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.model.data.types.StrokeType
import zir.teq.wearable.watchface.util.ViewHelper

class ThemeSelectionActivity : Activity() {
    private var mConfigView: WearableRecyclerView? = null
    private var mAdapter: ThemeSelectionAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_theme)
        val sharedThemeName = intent.getStringExtra(EXTRA_SHARED_THEME)
        Log.d(TAG, "sharedThemeName:" + sharedThemeName)
        mAdapter = ThemeSelectionAdapter(sharedThemeName, Theme.createThemeOptions())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val ctx = applicationContext
        val themeName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_theme), Theme.defaultTheme.name)
        val index = Theme.ALL_THEMES.indexOfFirst { it.name.equals(themeName) } + 1
        mConfigView?.smoothScrollToPosition(index)
    }

    companion object {
        private val TAG = this::class.java.simpleName
        internal val EXTRA_SHARED_THEME = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_THEME"
    }
}