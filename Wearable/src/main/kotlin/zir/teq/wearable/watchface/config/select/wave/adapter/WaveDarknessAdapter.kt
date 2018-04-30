package zir.teq.wearable.watchface.config.select.wave.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.holder.PropsHolder
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.types.wave.WaveDarkness


class WaveDarknessAdapter(private val options: List<WaveDarkness>) : RecAdapter() {
    override fun getItemViewType(position: Int) = options[position].configId
    override fun getItemCount(): Int = options.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_circle_text, parent, false)
        val names = options.map { it.name }
        return PropsHolder(view, WaveDarkness.pref, names)
    }

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        (vh as PropsHolder).bindProps(options[position].name, WaveDarkness.iconId)
    }
}