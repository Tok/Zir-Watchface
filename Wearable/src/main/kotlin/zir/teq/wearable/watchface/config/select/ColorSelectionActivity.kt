package zir.teq.wearable.watchface.config.select

import android.support.wearable.view.CurvedChildLayoutManager
import zir.teq.wearable.watchface.model.data.Col


class ColorSelectionActivity : android.app.Activity() {
    private var mConfigView: android.support.wearable.view.WearableRecyclerView? = null
    private var mAdapter: zir.teq.wearable.watchface.config.ColorSelectionAdapter? = null
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(zir.teq.wearable.watchface.R.layout.color_selection_config)
        val sharedColorName = intent.getStringExtra(zir.teq.wearable.watchface.config.select.ColorSelectionActivity.Companion.EXTRA_SHARED_COLOR)
        android.util.Log.d(zir.teq.wearable.watchface.config.select.ColorSelectionActivity.Companion.TAG, "sharedColorName:" + sharedColorName)
        mAdapter = zir.teq.wearable.watchface.config.ColorSelectionAdapter(sharedColorName, Col.getColorOptions())
        mConfigView = findViewById(zir.teq.wearable.watchface.R.id.wearable_recycler_view) as android.support.wearable.view.WearableRecyclerView
        zir.teq.wearable.watchface.util.ActivityHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    companion object {
        private val TAG = zir.teq.wearable.watchface.config.select.ColorSelectionActivity::class.java.simpleName
        internal val EXTRA_SHARED_COLOR = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_COLOR"
    }
}