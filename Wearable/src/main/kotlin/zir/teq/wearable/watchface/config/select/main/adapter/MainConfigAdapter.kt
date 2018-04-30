package zir.teq.wearable.watchface.config.select.main.adapter

import android.view.ViewGroup
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.general.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.general.types.MainType
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.util.ViewHelper


class MainConfigAdapter(private val mSettingsDataSet: List<Item>) : RecAdapter() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHelper.createViewHolder(viewGroup, viewType)
    override fun onBindViewHolder(vh: RecHolder, pos: Int) {
        val ci = mSettingsDataSet[pos]
        prepareHolder(vh, ci)
        if (vh is RecSelectionViewHolder) {
            vh.setActivity(ci.activity)
        }
    }

    private fun prepareHolder(vh: RecHolder, item: Item) {
        val holder = vh as RecSelectionViewHolder
        holder.setName(item.name)
        holder.setSharedPrefString(item.pref)
        when (item) {
            is MainType -> holder.bindIcon(item.iconId, ConfigData.palette().lightId)
        }
    }

    override fun getItemViewType(position: Int) = mSettingsDataSet[position].code
    override fun getItemCount() = mSettingsDataSet.size
}
