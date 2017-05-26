package zir.teq.wearable.watchface.config

import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.holder.ColorPickerViewHolder
import zir.teq.wearable.watchface.config.select.holder.StrokePickerViewHolder
import zir.teq.wearable.watchface.config.select.holder.ThemePickerViewHolder
import zir.teq.wearable.watchface.config.select.holder.ZirPickerViewHolder
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Col
import zir.teq.wearable.watchface.model.item.ColorConfigItem
import zir.teq.wearable.watchface.model.item.ConfigItem
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.TYPE_COLOR_CONFIG
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.TYPE_STROKE_CONFIG
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.TYPE_THEME_CONFIG
import zir.teq.wearable.watchface.model.item.StrokeConfigItem
import zir.teq.wearable.watchface.model.item.ThemeConfigItem
import java.util.*

class ZirWatchConfigAdapter(
        private val mContext: Context,
        watchFaceServiceClass: Class<*>,
        private val mSettingsDataSet: ArrayList<ConfigData.ConfigItemType>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mWatchFaceComponentName: ComponentName
    internal var mSharedPref: SharedPreferences

    init {
        mWatchFaceComponentName = ComponentName(mContext, watchFaceServiceClass)
        mSharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.zir_watch_preference_file_key),
                Context.MODE_PRIVATE)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        Log.d(TAG, "onCreateViewHolder(): viewGroup: $viewGroup, viewType: $viewType")
        return createViewHolder(viewGroup, viewType)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        Log.d(TAG, "Element $pos set.")
        val configItemType = mSettingsDataSet[pos] as ConfigItem
        when (viewHolder.itemViewType) {
            TYPE_COLOR_CONFIG -> launchColorAct(viewHolder, configItemType)
            TYPE_STROKE_CONFIG -> launchStrokeAct(viewHolder, configItemType)
            TYPE_THEME_CONFIG -> launchThemeAct(viewHolder, configItemType)
        }
    }

    fun launchColorAct(holder: RecyclerView.ViewHolder, item: ConfigItem) {
        prepareHolder(holder as ZirPickerViewHolder, item)
        (holder as ColorPickerViewHolder).setLaunchActivity((item as ColorConfigItem).activity)
    }

    fun launchStrokeAct(holder: RecyclerView.ViewHolder, item: ConfigItem) {
        prepareHolder(holder as ZirPickerViewHolder, item)
        (holder as StrokePickerViewHolder).setLaunchActivity((item as StrokeConfigItem).activity)
    }

    fun launchThemeAct(holder: RecyclerView.ViewHolder, item: ConfigItem) {
        prepareHolder(holder as ZirPickerViewHolder, item)
        (holder as ThemePickerViewHolder).setLaunchActivity((item as ThemeConfigItem).activity)
    }

    private fun prepareHolder(holder: ZirPickerViewHolder, item: ConfigItem) {
        val col = Col.findActive(holder.itemView.context)
        holder.setIcon(item.iconId, col.lightId)
        holder.setName(item.name)
        holder.setSharedPrefString(item.pref)
    }

    override fun getItemViewType(position: Int): Int {
        val configItemType = mSettingsDataSet[position]
        return configItemType.configType
    }

    override fun getItemCount(): Int {
        return mSettingsDataSet.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    companion object {
        private val TAG = ZirWatchConfigAdapter::class.java.simpleName
    }
}
