package zir.teq.wearable.watchface.config.select.adapter

import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.general.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.general.types.CheckboxItem
import zir.teq.wearable.watchface.config.general.types.MainItem
import zir.teq.wearable.watchface.config.select.holder.PropsViewHolder
import zir.teq.wearable.watchface.config.select.holder.WavePropsViewHolder
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.util.ViewHelper


class ConfigAdapter(private val mSettingsDataSet: List<Item>) : RecAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val item = MainItem.all.find { it.code == viewType }
        return when (item) {
            is MainItem -> {
                val view = ViewHelper.createView(parent, item.layoutId())
                PropsViewHolder(view, EXTRA, viewType)
            }
            is CheckboxItem -> ViewHelper.createCheckboxViewHolder(parent, item)
            else -> throw IllegalArgumentException("Item unknown $item.")
        }
    }

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
        holder.bindIcon(item.iconId, ConfigData.palette().lightId)
    }

    override fun getItemViewType(position: Int) = mSettingsDataSet[position].code
    override fun getItemCount() = mSettingsDataSet.size

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_CONFIG"
    }
}
