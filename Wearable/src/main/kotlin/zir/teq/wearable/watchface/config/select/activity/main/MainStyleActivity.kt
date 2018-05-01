package zir.teq.wearable.watchface.config.select.activity.main

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.select.adapter.StylePropsAdapter
import zir.teq.wearable.watchface.util.ViewHelper

class MainStyleActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: StylePropsAdapter
    private lateinit var mManager: WearableLinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        mAdapter = StylePropsAdapter(Item.STYLE_TYPES)
        mView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mView, mAdapter, mManager)
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_STYLE_PROPS"
    }
}
