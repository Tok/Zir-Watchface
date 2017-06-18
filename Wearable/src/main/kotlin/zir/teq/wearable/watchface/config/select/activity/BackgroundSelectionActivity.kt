package zir.teq.wearable.watchface.config.select.activity

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ScalingLayoutManager
import zir.teq.wearable.watchface.config.select.adapter.BackgroundSelectionAdapter
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.Background
import zir.teq.wearable.watchface.util.ViewHelper


class BackgroundSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: BackgroundSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_background)
        val sharedBackgroundId = intent.getStringExtra(EXTRA)
        mAdapter = BackgroundSelectionAdapter(sharedBackgroundId, Background.options())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Background.all.indexOfFirst { it.name.equals(ConfigData.background.name) } + 1
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_BACKGROUND"
    }
}