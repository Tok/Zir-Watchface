package zir.teq.wearable.watchface.config.select.adapter.main

import android.view.ViewGroup
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.general.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.util.ViewHelper


class MainStyleAdapter(private val options: List<Item>) : RecAdapter() {
    override fun getItemCount() = options.size
    override fun getItemViewType(position: Int) = options[position].code
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHelper.createViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecHolder, position: Int) {
        val item = options[position]
        when (holder) {
            is RecSelectionViewHolder -> {
                holder.setName(item.name)
                holder.setSharedPrefString(item.pref)
                holder.bindIcon(item.iconId, ConfigData.palette().lightId)
                holder.setActivity(item.activity)
            }
        }
    }
}
