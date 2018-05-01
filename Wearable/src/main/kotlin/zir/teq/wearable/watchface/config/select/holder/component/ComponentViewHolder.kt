package zir.teq.wearable.watchface.config.select.holder.component

import android.view.View
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.model.setting.component.Theme


class ComponentViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, Theme.EXTRA) }
    }
}
