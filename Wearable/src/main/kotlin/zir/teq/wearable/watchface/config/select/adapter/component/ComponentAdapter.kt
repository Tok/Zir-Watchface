package zir.teq.wearable.watchface.config.select.adapter.component

import android.view.LayoutInflater
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.holder.BooleanPairViewHolder
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.setting.ConfigItem
import zir.teq.wearable.watchface.model.setting.component.Component


class ComponentAdapter(private val options: List<ConfigItem>) : RecAdapter() {
    override fun getItemViewType(position: Int) = options[position].configId
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (Component.isDoubleBoolean(viewType)) {
            val comp = Component.valueOf(viewType) as Component
            val view = inflater.inflate(R.layout.list_item_double_check, parent, false)
            BooleanPairViewHolder(view, comp.activeKey, comp.ambientKey).apply {
                updateBoxes(comp.activeKey, comp.ambientKey, comp.name)
            }
        } else throw IllegalArgumentException("Viewtype unkown: $viewType.")
    }

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val item = options[position]
        when (item) {
            is Component -> if (vh is BooleanPairViewHolder) prepareDoubleCheckHolder(vh, item)
            else -> IllegalStateException("Unexpected Item: $item.")
        }
    }

    private fun prepareDoubleCheckHolder(holder: BooleanPairViewHolder, item: Component) {
        holder.updateBoxes(item.activeKey, item.ambientKey, item.name)
    }

    override fun getItemCount(): Int = options.size
}
