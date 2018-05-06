package zir.teq.wearable.watchface.config.select.activity.main

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.adapter.main.MainSetupAdapter
import zir.teq.wearable.watchface.model.setting.Setup
import zir.teq.wearable.watchface.util.ViewHelper

class MainSetupActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: MainSetupAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        mAdapter = MainSetupAdapter(Setup.all)
        mView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mView, mAdapter, mManager)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_SETUP"
    }
}
