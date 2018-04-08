package zir.teq.wearable.watchface.config.select.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
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
import zir.teq.wearable.watchface.config.select.config.MainType
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.color.Palette
import zir.teq.wearable.watchface.util.ViewHelper

class MainConfigActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: MainConfigAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_config)
        mView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        mAdapter = MainConfigAdapter(Item.createMainConfig(this))
        ViewHelper.initView(mView, mAdapter, ScalingLayoutManager(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PALETTE.code -> {
                    val filter = Palette.createFilter(ConfigData.palette)
                    DRAWABLE_IDS.forEach { updateItemColor(mView.context, filter, it) }
                }
            }
        }
    }

    companion object {
        data class UpdateReq(val code: Int)

        val PALETTE = UpdateReq(1000)

        private val DRAWABLE_IDS = listOf<Int>(
                R.drawable.icon_color,
                R.drawable.icon_background,
                R.drawable.icon_stroke,
                R.drawable.icon_components,
                R.drawable.icon_outline,
                R.drawable.icon_growth,
                R.drawable.icon_alpha,
                R.drawable.icon_dim,
                R.drawable.icon_dummy
        )

        private fun updateItemColor(ctx: Context, filter: ColorFilter, id: Int): Drawable =
                ctx.resources.getDrawable(id, null).apply {
                    colorFilter = filter
                    invalidateSelf()
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
        val type = item.type
        when (type) {
            is MainType -> holder.bindIcon((item.type as MainType).iconId, ConfigData.palette.lightId)
        }
    }

    override fun getItemViewType(position: Int) = mSettingsDataSet[position].configType
    override fun getItemCount() = mSettingsDataSet.size
}
