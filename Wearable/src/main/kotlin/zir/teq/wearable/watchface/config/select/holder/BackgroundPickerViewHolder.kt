package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.BackgroundSelectionActivity

class BackgroundPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    init {
        initButton(view.findViewById(R.id.config_list_item_background))
        view.setOnClickListener(this)
    }
    override fun onClick(view: View) = super.handleClick(EXTRA, REQ, view)
    companion object {
        val EXTRA = BackgroundSelectionActivity.EXTRA_SHARED_BACKGROUND
        val REQ = ZirWatchConfigActivity.BACKGROUND.code
    }
}
