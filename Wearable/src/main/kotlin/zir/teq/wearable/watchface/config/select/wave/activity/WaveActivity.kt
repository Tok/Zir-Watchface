package zir.teq.wearable.watchface.config.select.wave.activity

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.wave.adapter.WaveAdapter
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.settings.wave.Wave
import zir.teq.wearable.watchface.model.data.types.WaveFrequency
import zir.teq.wearable.watchface.util.ViewHelper


class WaveActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: WaveAdapter
    private lateinit var mManager: WearableLinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        val sharedWaveName = intent.getStringExtra(EXTRA)
        mAdapter = WaveAdapter(sharedWaveName, Wave.options())
        mView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = Wave.ALL.indexOfFirst { it.equals(ConfigData.wave()) } + 1
        mView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE"
        internal val EXTRA_VELOCITY = this::class.java.getPackage().name + "SHARED_WAVE_VELOCITY"
        internal val EXTRA_FREQUENCY = this::class.java.getPackage().name + "SHARED_WAVE_FREQUENCY"
        internal val EXTRA_INTENSITY = this::class.java.getPackage().name + "SHARED_WAVE_INTENSITY"
    }
}
