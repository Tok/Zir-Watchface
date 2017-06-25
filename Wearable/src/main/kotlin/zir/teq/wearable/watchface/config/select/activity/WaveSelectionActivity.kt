package zir.teq.wearable.watchface.config.select.activity

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import android.view.View
import config.select.adapter.WaveSelectionAdapter
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ScalingLayoutManager
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.Wave
import zir.teq.wearable.watchface.util.ViewHelper


class WaveSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: WaveSelectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_wave)
        val sharedWaveName = intent.getStringExtra(EXTRA)
        mAdapter = WaveSelectionAdapter(sharedWaveName, Wave.options())
        mConfigView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Wave.all.indexOfFirst { it.name.equals(ConfigData.wave.name) }
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE"
    }
}