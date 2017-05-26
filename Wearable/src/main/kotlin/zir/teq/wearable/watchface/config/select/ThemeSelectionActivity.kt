package zir.teq.wearable.watchface.config.select

import android.support.wearable.view.CurvedChildLayoutManager
import zir.teq.wearable.watchface.model.data.Theme


class ThemeSelectionActivity : android.app.Activity() {
    private var mConfigView: android.support.wearable.view.WearableRecyclerView? = null
    private var mAdapter: ThemeSelectionAdapter? = null
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(zir.teq.wearable.watchface.R.layout.theme_selection_config)
        val sharedThemeName = intent.getStringExtra(zir.teq.wearable.watchface.config.select.ThemeSelectionActivity.Companion.EXTRA_SHARED_THEME)
        android.util.Log.d(zir.teq.wearable.watchface.config.select.ThemeSelectionActivity.Companion.TAG, "sharedThemeName:" + sharedThemeName)
        mAdapter = ThemeSelectionAdapter(sharedThemeName, Theme.Companion.createThemeOptions())
        mConfigView = findViewById(zir.teq.wearable.watchface.R.id.wearable_recycler_view) as android.support.wearable.view.WearableRecyclerView
        zir.teq.wearable.watchface.util.ActivityHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    companion object {
        private val TAG = zir.teq.wearable.watchface.config.select.ThemeSelectionActivity::class.java.simpleName
        internal val EXTRA_SHARED_THEME = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_THEME"
    }
}