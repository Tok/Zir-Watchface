package zir.teq.wearable.watchface.config.select.holder

import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.AlphaSelectionActivity

class AlphaPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    init {
        initButton(view.findViewById(R.id.config_list_item_alpha))
        view.setOnClickListener(this)
    }
    override fun onClick(view: View) = super.handleClick(EXTRA, REQ, view)
    companion object {
        val EXTRA = AlphaSelectionActivity.EXTRA_SHARED_ALPHA
        val REQ = ZirWatchConfigActivity.ALPHA.code
    }
}
