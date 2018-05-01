package zir.teq.wearable.watchface.config.select.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.types.MainItem
import zir.teq.wearable.watchface.config.select.adapter.ConfigAdapter
import zir.teq.wearable.watchface.util.ViewHelper


class ConfigActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: ConfigAdapter
    private lateinit var mManager: WearableLinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_list)
        mView = findViewById(R.id.zir_list_view)
        mAdapter = ConfigAdapter(MainItem.all)
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
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_WAVE_PROPS"

        data class UpdateReq(val code: Int)

        val PALETTE = UpdateReq(1000)
    }
}
