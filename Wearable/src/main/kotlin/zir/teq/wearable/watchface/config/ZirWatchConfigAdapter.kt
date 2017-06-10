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
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.THEME
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.COLORS
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.BACKGROUND
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.STROKE
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.OUTLINE
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.GROWTH
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.ALPHA
import zir.teq.wearable.watchface.model.item.ConfigItem.Companion.DIM

class ZirWatchConfigAdapter(
        private val mContext: Context,
        watchFaceServiceClass: Class<*>,
        private val mSettingsDataSet: ArrayList<ConfigData.ConfigItemType>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mWatchFaceComponentName: ComponentName

    init {
        mWatchFaceComponentName = ComponentName(mContext, watchFaceServiceClass)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHelper.createViewHolder(viewGroup, viewType)

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, pos: Int) {
        val ci = mSettingsDataSet[pos] as ConfigItem
        Log.d(TAG, "onBindViewHolder() ci: $ci")
        if (ci.type.isPair()) {
            prepareDoubleCheckHolder(vh as BooleanPairViewHolder, ci)
        } else {
            prepareHolder(vh as ZirPickerViewHolder, ci)
        }
        when (vh.itemViewType) {
            THEME.code -> (vh as ThemePickerViewHolder).setActivity((ci as ThemeConfigItem).activity)
            COLORS.code -> (vh as ColorPickerViewHolder).setActivity((ci as ColorConfigItem).activity)
            BACKGROUND.code -> (vh as BackgroundPickerViewHolder).setActivity((ci as BackgroundConfigItem).activity)
            STROKE.code -> (vh as StrokePickerViewHolder).setActivity((ci as StrokeConfigItem).activity)
            OUTLINE.code -> (vh as OutlinePickerViewHolder).setActivity((ci as OutlineConfigItem).activity)
            GROWTH.code -> (vh as GrowthPickerViewHolder).setActivity((ci as GrowthConfigItem).activity)
            ALPHA.code -> (vh as AlphaPickerViewHolder).setActivity((ci as AlphaConfigItem).activity)
            DIM.code -> (vh as DimPickerViewHolder).setActivity((ci as DimConfigItem).activity)
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

    private fun prepareHolder(holder: ZirPickerViewHolder, item: ConfigItem) {
        val col = Palette.findActive(holder.itemView.context)
        holder.setName(item.name)
        holder.setSharedPrefString(item.pref)
        if (item.type.iconId != null) {
            holder.bindIcon(item.type.iconId, col.lightId)
        }
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
