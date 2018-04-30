package zir.teq.wearable.watchface.config.select.wave.activity

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.manager.ScalingLayoutCallback
import zir.teq.wearable.watchface.config.select.wave.adapter.WaveDarknessAdapter
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.types.wave.WaveDarkness
import zir.teq.wearable.watchface.util.ViewHelper


class WaveDarknessActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: WaveDarknessAdapter
    private lateinit var mManager: WearableLinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        mAdapter = WaveDarknessAdapter(WaveDarkness.ALL)
        mView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this, ScalingLayoutCallback())
        ViewHelper.initView(mView, mAdapter, mManager)
    }

    override fun onStart() {
        super.onStart()
        val index = WaveDarkness.ALL.indexOfFirst { it.equals(ConfigData.waveDarkness()) }
        mView.smoothScrollToPosition(index + 1)
    }

    companion object {
        internal val CLASS = WaveDarknessActivity::class.java
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE_DARKNESS"
    }
}
