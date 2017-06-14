package zir.teq.wearable.watchface.config.select.activity

import android.app.Activity
import android.os.Bundle
import android.support.wearable.view.WearableRecyclerView
import config.select.adapter.StackSelectionAdapter
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ScalingLayoutManager
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Stack
import zir.teq.wearable.watchface.util.ViewHelper


class StackSelectionActivity : Activity() {
    private lateinit var mConfigView: WearableRecyclerView
    private lateinit var mAdapter: StackSelectionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selection_stack)
        val sharedStackName = intent.getStringExtra(EXTRA)
        mAdapter = StackSelectionAdapter(sharedStackName, Stack.options())
        mConfigView = findViewById(R.id.wearable_recycler_view) as WearableRecyclerView
        ViewHelper.initView(mConfigView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onStart() {
        super.onStart()
        val index = Stack.all.indexOfFirst { it.name.equals(ConfigData.stack.name) }
        mConfigView.smoothScrollToPosition(index)
    }

    companion object {
        internal val EXTRA = "zir.teq.wearable.watchface.config.extra.EXTRA_SHARED_STACK"
    }
}