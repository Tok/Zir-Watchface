package zir.teq.wearable.watchface.config.select.activity.component

import android.app.Activity
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.adapter.component.ComponentAdapter
import zir.teq.wearable.watchface.model.setting.component.Theme
import zir.teq.wearable.watchface.model.types.Component
import zir.teq.wearable.watchface.util.ViewHelper


class ComponentActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: ComponentAdapter
    private lateinit var mManager: WearableLinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        val items = Theme.ALL.toList() + Component.ALL
        mAdapter = ComponentAdapter(items)
        mView = findViewById(R.id.zir_list_view)
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mView, mAdapter, mManager)
    }
}
