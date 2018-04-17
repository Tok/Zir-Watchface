package zir.teq.wearable.watchface.config.select.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.wear.widget.WearableLinearLayoutManager
import android.support.wear.widget.WearableRecyclerView
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.select.config.ConfigItemTypes
import zir.teq.wearable.watchface.config.select.config.Item
import zir.teq.wearable.watchface.config.select.config.MainType
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
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

class MainConfigAdapter(private val mSettingsDataSet: ConfigItemTypes) : RecAdapter() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHelper.createViewHolder(viewGroup, viewType)
    override fun onBindViewHolder(vh: RecHolder, pos: Int) {
        val ci = mSettingsDataSet[pos] as Item
        prepareHolder(vh, ci)
        if (vh is RecSelectionViewHolder) {
            vh.setActivity((ci).activity)
        }
    }

    private fun prepareHolder(vh: RecHolder, item: Item) {
        val holder = vh as RecSelectionViewHolder
        holder.setName(item.name)
        holder.setSharedPrefString(item.pref)
        val type = item.type
        when (type) {
            is MainType -> holder.bindIcon((item.type as MainType).iconId, ConfigData.palette.lightId)
        }
    }

    override fun getItemViewType(position: Int) = mSettingsDataSet[position].configType
    override fun getItemCount() = mSettingsDataSet.size
}
