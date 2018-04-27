package zir.teq.wearable.watchface.config.select.main.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.select.main.adapter.MainConfigAdapter
import zir.teq.wearable.watchface.util.ViewHelper


class MainConfigActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: MainConfigAdapter
    private lateinit var mManager: WearableLinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        mView = findViewById(R.id.zir_list_view)
        mAdapter = MainConfigAdapter(Item.createMainConfig(this))
        mManager = WearableLinearLayoutManager(this)
        ViewHelper.initView(mView, mAdapter, mManager)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PALETTE.code -> mAdapter.notifyDataSetChanged();
            }
        }
    }

    companion object {
        data class UpdateReq(val code: Int)

        val PALETTE = UpdateReq(1000)
    }
}
