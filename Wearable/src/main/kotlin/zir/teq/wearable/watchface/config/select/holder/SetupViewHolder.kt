package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.view.View
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.select.activity.ConfigActivity
import zir.teq.wearable.watchface.model.setting.Setup


class SetupViewHolder(val view: View, val item: Setup) : RecSelectionViewHolder(view) {
    init {
        mButton = view.findViewById<View>(R.id.list_item_main) as Button
        view.setOnClickListener { handleSetupClick(view, item) }
    }

    fun handleSetupClick(view: View, item: Setup) {
        item.applySetup()
        val activity = view.context as Activity
        activity.setResult(0)
        activity.finish()
    }
}
