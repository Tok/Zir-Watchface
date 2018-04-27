package zir.teq.wearable.watchface.config.select.color.holder

import android.view.View
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.select.main.activity.MainConfigActivity
import zir.teq.wearable.watchface.config.select.color.activity.PaletteActivity

class PaletteViewHolder(view: View) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { super.handleClick(view, PaletteActivity.EXTRA, MainConfigActivity.PALETTE.code) }
    }
}
