package zir.teq.wearable.watchface.config.select.style.adapter

import android.view.ViewGroup
import zir.teq.wearable.watchface.config.general.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.general.types.StyleType
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.ConfigItemTypes
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.util.ViewHelper


class StyleAdapter(private val options: ConfigItemTypes) : RecAdapter() {
    override fun getItemViewType(position: Int) = options[position].configType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        return ViewHelper.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecHolder, position: Int) {
        val item = options[position] as Item
        when (holder) {
            is RecSelectionViewHolder -> {
                holder.setName(item.name)
                holder.setSharedPrefString(item.pref)
                holder.bindIcon((item.type as StyleType).iconId, ConfigData.palette().lightId)
                holder.setActivity(item.activity)
            }
        }
    }

    override fun getItemCount() = options.size
}
