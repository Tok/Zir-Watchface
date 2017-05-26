package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import android.util.Log
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.util.ActivityHelper

class ThemeSelectionActivity : Activity() {
    private var mConfigView: WearableRecyclerView? = null
    private var mAdapter: ThemeSelectionAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.theme_selection_config)
        val sharedThemeName = intent.getStringExtra(EXTRA_SHARED_THEME)
        Log.d(TAG, "sharedThemeName:" + sharedThemeName)
        mAdapter = ThemeSelectionAdapter(sharedThemeName, Theme.createThemeOptions())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ActivityHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    companion object {
        private val TAG = ThemeSelectionActivity::class.java.simpleName
        internal val EXTRA_SHARED_THEME = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_THEME"
    }
}