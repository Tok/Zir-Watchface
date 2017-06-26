package zir.teq.wearable.watchface.config.select.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ColorFilter
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.WearableRecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.manager.ScalingLayoutManager
import zir.teq.wearable.watchface.config.select.*
import zir.teq.wearable.watchface.config.holder.BooleanPairViewHolder
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.Palette
import zir.teq.wearable.watchface.model.item.*
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.ALPHA
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.BACKGROUND
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.DIM
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.GROWTH
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.OUTLINE
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.PALETTE
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.STACK
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.STROKE
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.THEME
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.WAVE
import zir.teq.wearable.watchface.util.ViewHelper
import java.util.*

typealias ConfigItemTypes = ArrayList<ConfigData.ConfigItemType>

class MainConfigActivity : Activity() {
    private lateinit var mView: WearableRecyclerView
    private lateinit var mAdapter: MainConfigAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zir_config)
        mView = findViewById<View>(R.id.wearable_recycler_view) as WearableRecyclerView
        val ctx = ConfigData.ctx
        mAdapter = MainConfigAdapter(ctx,
                ConfigData.watchFaceServiceClass,
                ConfigData.getDataToPopulateAdapter(this))
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

class MainConfigAdapter(
        private val mContext: Context, watchFaceServiceClass: Class<*>,
        private val mSettingsDataSet: ConfigItemTypes) : RecAdapter() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHelper.createViewHolder(viewGroup, viewType)
    override fun onBindViewHolder(vh: RecHolder, pos: Int) {
        val ci = mSettingsDataSet[pos] as ConfigItem
        Log.d(TAG, "onBindViewHolder() ci: $ci")
        prepareHolder(vh, ci)
        when (vh.itemViewType) {
            THEME.code -> (vh as ThemePickerViewHolder).setActivity((ci as ThemeConfigItem).activity)
            PALETTE.code -> (vh as PalettePickerViewHolder).setActivity((ci as PaletteConfigItem).activity)
            BACKGROUND.code -> (vh as BackgroundPickerViewHolder).setActivity((ci as BackgroundConfigItem).activity)
            WAVE.code -> (vh as WavePickerViewHolder).setActivity((ci as WaveConfigItem).activity)
            STROKE.code -> (vh as StrokePickerViewHolder).setActivity((ci as StrokeConfigItem).activity)
            OUTLINE.code -> (vh as OutlinePickerViewHolder).setActivity((ci as OutlineConfigItem).activity)
            GROWTH.code -> (vh as GrowthPickerViewHolder).setActivity((ci as GrowthConfigItem).activity)
            ALPHA.code -> (vh as AlphaPickerViewHolder).setActivity((ci as AlphaConfigItem).activity)
            DIM.code -> (vh as DimPickerViewHolder).setActivity((ci as DimConfigItem).activity)
            STACK.code -> (vh as StackPickerViewHolder).setActivity((ci as StackConfigItem).activity)
        }
    }

    private fun prepareDoubleCheckHolder(holder: BooleanPairViewHolder, item: ConfigItem) {
        val res = holder.mLayout.context.resources
        with(item.type) {
            val activePref = res.getString(prefId)
            val ambientPref = res.getString(secondaryPrefId ?: prefId)
            val name = res.getString(nameId)
            holder.updateBoxes(activePref, ambientPref, name)
        }
    }

    private fun prepareHolder(vh: RecHolder, item: ConfigItem) {
        if (item.type.isPair()) {
            prepareDoubleCheckHolder(vh as BooleanPairViewHolder, item)
        } else {
            val holder = vh as RecSelectionViewHolder
            holder.setName(item.name)
            holder.setSharedPrefString(item.pref)
            if (item.type.iconId != null) {
                holder.bindIcon(item.type.iconId, ConfigData.palette.lightId)
            }
        }
    }

    override fun getItemViewType(position: Int) = mSettingsDataSet[position].configType
    override fun getItemCount() = mSettingsDataSet.size
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
