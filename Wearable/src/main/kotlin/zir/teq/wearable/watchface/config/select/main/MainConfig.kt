package zir.teq.wearable.watchface.config.select.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ColorFilter
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.WearableRecyclerView
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.manager.ScalingLayoutManager
import zir.teq.wearable.watchface.config.select.config.ConfigItemTypes
import zir.teq.wearable.watchface.config.select.config.Item
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.Palette
import zir.teq.wearable.watchface.util.ViewHelper

class MainConfigActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: MainConfigAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_config)
        mView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        mAdapter = MainConfigAdapter(Item.createConfig(this))
        ViewHelper.initView(mView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PALETTE.code -> {
                if (resultCode == Activity.RESULT_OK) {
                    val ctx = mView.context
                    val filter = Palette.createFilter(ConfigData.palette)
                    updateItemColor(ctx, R.drawable.icon_color, filter)
                    updateItemColor(ctx, R.drawable.icon_background, filter)
                    updateItemColor(ctx, R.drawable.icon_stroke, filter)
                    updateItemColor(ctx, R.drawable.icon_theme, filter)
                    updateItemColor(ctx, R.drawable.icon_outline, filter)
                    updateItemColor(ctx, R.drawable.icon_growth, filter)
                    updateItemColor(ctx, R.drawable.icon_alpha, filter)
                    updateItemColor(ctx, R.drawable.icon_dim, filter)
                }
            }
        }
    }

    companion object {
        data class UpdateReq(val code: Int)
        val PALETTE = UpdateReq(1000)
        fun updateItemColor(ctx: Context, id: Int, filter: ColorFilter) {
            val drawable = ctx.resources.getDrawable(id, null)
            drawable.colorFilter = filter
            drawable.invalidateSelf()
        }
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
        if (item.type.iconId != null) {
            holder.bindIcon(item.type.iconId, ConfigData.palette.lightId)
        }
    }

    override fun getItemViewType(position: Int) = mSettingsDataSet[position].configType
    override fun getItemCount() = mSettingsDataSet.size
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
    }
}
