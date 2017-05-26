package zir.teq.wearable.watchface.config

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.CurvedChildLayoutManager
import android.support.wearable.view.WearableRecyclerView
import android.util.Log
import zir.teq.wearable.watchface.model.data.Col
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.util.ActivityHelper


class ColorSelectionActivity : Activity() {
    private var mConfigView: WearableRecyclerView? = null
    private var mColorSelectionAdapter: ColorSelectionAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.color_selection_config)
        val sharedColorName = intent.getStringExtra(EXTRA_SHARED_COLOR)
        Log.d(TAG, "sharedColorName:" + sharedColorName)
        mColorSelectionAdapter = ColorSelectionAdapter(sharedColorName, Col.getColorOptions())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ActivityHelper.initView(mConfigView, mColorSelectionAdapter, CurvedChildLayoutManager(this))
    }

    companion object {
        private val TAG = ColorSelectionActivity::class.java.simpleName
        internal val EXTRA_SHARED_COLOR = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_COLOR"
    }
}