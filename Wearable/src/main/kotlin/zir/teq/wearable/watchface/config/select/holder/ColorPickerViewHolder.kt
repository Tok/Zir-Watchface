package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.PaletteSelectionActivity

class ColorPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    init {
        initButton(view.findViewById(R.id.config_list_item_color))
        view.setOnClickListener(this)
    }
    override fun onClick(view: View) = super.handleClick(EXTRA, REQ, view)
    companion object {
        val EXTRA = PaletteSelectionActivity.EXTRA_SHARED_COLOR
        val REQ = ZirWatchConfigActivity.COLORS.code
    }
}
