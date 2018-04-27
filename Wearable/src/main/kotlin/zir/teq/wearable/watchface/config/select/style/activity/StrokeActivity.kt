package zir.teq.wearable.watchface.config.select.style.activity

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.style.adapter.StrokeAdapter
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.style.Stroke
import zir.teq.wearable.watchface.util.ViewHelper


class StrokeActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: StrokeAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        val sharedStrokeName = intent.getStringExtra(EXTRA)
        mAdapter = StrokeAdapter(sharedStrokeName, Stroke.options())
        mConfigView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mConfigView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = Stroke.all.indexOfFirst { it.name.equals(ConfigData.style.stroke.name) }
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_STROKE"
    }
}
