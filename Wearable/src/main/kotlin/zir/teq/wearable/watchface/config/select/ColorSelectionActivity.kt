package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.Col
import zir.teq.wearable.watchface.util.ViewHelper


class ColorSelectionActivity : Activity() {
    private var mConfigView: WearableRecyclerView? = null
    private var mAdapter: ColorSelectionAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_color)
        val sharedColorName = intent.getStringExtra(EXTRA_SHARED_COLOR)
        android.util.Log.d(TAG, "sharedColorName:" + sharedColorName)
        mAdapter = ColorSelectionAdapter(sharedColorName, Col.getColorOptions())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, CurvedChildLayoutManager(this))
    }

    companion object {
        private val TAG = ColorSelectionActivity::class.java.simpleName
        internal val EXTRA_SHARED_COLOR = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_COLOR"
    }
}