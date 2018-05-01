package zir.teq.wearable.watchface.config.general.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.holder.PropsHolder
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.setting.Config
import zir.teq.wearable.watchface.model.setting.Setting

class SettingsAdapter(val config: Config) : RecAdapter() {
    val options: List<Setting> = config.all
    override fun getItemViewType(position: Int) = config.code
    override fun getItemCount(): Int = options.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_circle_text, parent, false)
        return PropsHolder(view, config.pref, options)
    }

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        (vh as PropsHolder).bindProps(options[position].label, config.iconId)
    }
}
