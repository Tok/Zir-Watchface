package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ScalingLayoutManager
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Background
import zir.teq.wearable.watchface.util.ViewHelper


class BackgroundSelectionActivity : Activity() {
    private var mConfigView: WearableRecyclerView? = null
    private var mAdapter: BackgroundSelectionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_background)
        val sharedBackgroundId = intent.getStringExtra(EXTRA_SHARED_BACKGROUND)
        mAdapter = BackgroundSelectionAdapter(sharedBackgroundId, Background.options())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val ctx = applicationContext
        val name = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_background), Background.default.name)
        val index = Background.all.indexOfFirst { it.name.equals(name) } + 1
        mConfigView?.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA_SHARED_BACKGROUND = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_BACKGROUND"
    }
}