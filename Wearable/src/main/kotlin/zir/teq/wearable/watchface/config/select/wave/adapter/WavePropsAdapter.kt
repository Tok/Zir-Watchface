package zir.teq.wearable.watchface.config.select.wave.adapter

import android.view.ViewGroup
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.types.WaveProps
import zir.teq.wearable.watchface.util.ViewHelper

class WavePropsAdapter(private val options: List<WaveProps>) : RecAdapter() {
    override fun getItemViewType(position: Int) = options[position].configId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        return ViewHelper.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        //val item = options[position]
    }

    override fun getItemCount(): Int = options.size
}
