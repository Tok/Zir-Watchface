package zir.teq.wearable.watchface.config.select.holder

import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.OutlineSelectionActivity

class OutlinePickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    init {
        initButton(view.findViewById(R.id.config_list_item_outline))
        view.setOnClickListener(this)
    }
    override fun onClick(view: View) = super.handleClick(EXTRA, REQ, view)
    companion object {
        val EXTRA = OutlineSelectionActivity.EXTRA_SHARED_OUTLINE
        val REQ = ZirWatchConfigActivity.OUTLINE.code
    }
}