package zir.teq.wearable.watchface.config

import android.content.ComponentName
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import zir.teq.wearable.watchface.config.select.holder.*
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.item.*
import zir.teq.wearable.watchface.util.ViewHelper
import java.util.*

class ZirWatchConfigAdapter(
        private val mContext: Context,
        watchFaceServiceClass: Class<*>,
        private val mSettingsDataSet: ArrayList<ConfigData.ConfigItemType>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mWatchFaceComponentName: ComponentName

    init {
        mWatchFaceComponentName = ComponentName(mContext, watchFaceServiceClass)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        Log.d(TAG, "onCreateViewHolder(): viewGroup: $viewGroup, viewType: $viewType")
        val holder = ViewHelper.createViewHolder(viewGroup, viewType)
        Log.d(TAG, "onCreateViewHolder(): holder: $holder")
        return holder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        Log.d(TAG, "Element $pos set.")
        val configItemType = mSettingsDataSet[pos] as ConfigItem
        when (viewHolder.itemViewType) {
            ConfigItem.THEME.code -> launchThemeAct(viewHolder, configItemType)
            ConfigItem.COLORS.code -> launchColorAct(viewHolder, configItemType)
            ConfigItem.BACKGROUND.code -> launchBackgroundAct(viewHolder, configItemType)
            ConfigItem.STROKE.code -> launchStrokeAct(viewHolder, configItemType)
            ConfigItem.OUTLINE.code -> launchOutlineAct(viewHolder, configItemType)
            ConfigItem.GROWTH.code -> launchGrowthAct(viewHolder, configItemType)
            ConfigItem.ALPHA.code -> launchAlphaAct(viewHolder, configItemType)
            ConfigItem.DIM.code -> launchDimAct(viewHolder, configItemType)
        }
    }

    fun launchColorAct(holder: RecyclerView.ViewHolder, item: ConfigItem) {
        prepareHolder(holder as ZirPickerViewHolder, item)
        (holder as ColorPickerViewHolder).setLaunchActivity((item as ColorConfigItem).activity)
    }

    fun launchBackgroundAct(holder: RecyclerView.ViewHolder, item: ConfigItem) {
        prepareHolder(holder as ZirPickerViewHolder, item)
        (holder as BackgroundPickerViewHolder).setLaunchActivity((item as BackgroundConfigItem).activity)
    }

    fun launchStrokeAct(holder: RecyclerView.ViewHolder, item: ConfigItem) {
        prepareHolder(holder as ZirPickerViewHolder, item)
        (holder as StrokePickerViewHolder).setLaunchActivity((item as StrokeConfigItem).activity)
    }

    fun launchThemeAct(holder: RecyclerView.ViewHolder, item: ConfigItem) {
        prepareHolder(holder as ZirPickerViewHolder, item)
        (holder as ThemePickerViewHolder).setLaunchActivity((item as ThemeConfigItem).activity)
    }

    fun launchOutlineAct(holder: RecyclerView.ViewHolder, item: ConfigItem) {
        prepareHolder(holder as ZirPickerViewHolder, item)
        (holder as OutlinePickerViewHolder).setLaunchActivity((item as OutlineConfigItem).activity)
    }

    fun launchGrowthAct(holder: RecyclerView.ViewHolder, item: ConfigItem) {
        prepareHolder(holder as ZirPickerViewHolder, item)
        (holder as GrowthPickerViewHolder).setLaunchActivity((item as GrowthConfigItem).activity)
    }

    fun launchAlphaAct(holder: RecyclerView.ViewHolder, item: ConfigItem) {
        prepareHolder(holder as ZirPickerViewHolder, item)
        (holder as AlphaPickerViewHolder).setLaunchActivity((item as AlphaConfigItem).activity)
    }

    fun launchDimAct(holder: RecyclerView.ViewHolder, item: ConfigItem) {
        prepareHolder(holder as ZirPickerViewHolder, item)
        (holder as DimPickerViewHolder).setLaunchActivity((item as DimConfigItem).activity)
    }

    private fun prepareHolder(holder: ZirPickerViewHolder, item: ConfigItem) {
        val col = Palette.findActive(holder.itemView.context)
        holder.setName(item.name)
        holder.setSharedPrefString(item.pref)
        holder.bindIcon(item.type.iconId, col.lightId)
    }

    override fun getItemViewType(position: Int): Int = mSettingsDataSet[position].configType

    override fun getItemCount(): Int = mSettingsDataSet.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
