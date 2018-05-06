package zir.teq.wearable.watchface.config.select.adapter.main

import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.holder.SetupViewHolder
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.setting.Setup
import zir.teq.wearable.watchface.util.ViewHelper


class MainSetupAdapter(private val options: List<Setup>) : RecAdapter() {
    override fun getItemCount() = options.size
    override fun getItemViewType(position: Int) = options[position].code
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val view = ViewHelper.createView(parent, R.layout.list_item_main)
        val item = Setup.all.find { it.code == viewType } ?: Setup.default
        return SetupViewHolder(view, item)
    }

    override fun onBindViewHolder(holder: RecHolder, position: Int) {
        val item = options[position]
        when (holder) {
            is SetupViewHolder -> {
                holder.setName(item.name)
                holder.bindIcon(item.iconId, null)
            }
        }
    }

    companion object {
        internal val EXTRA = this::class.java.getPackage().name + "SHARED_MAIN_SETUP"
    }
}
