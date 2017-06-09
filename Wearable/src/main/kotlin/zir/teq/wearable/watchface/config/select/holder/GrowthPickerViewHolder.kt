package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.GrowthSelectionActivity
import zir.teq.wearable.watchface.config.select.GrowthSelectionActivity.Companion.EXTRA_SHARED_GROWTH

class GrowthPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    private var mActivity: Class<GrowthSelectionActivity>? = null

    init {
        initButton(view.findViewById(R.id.config_list_item_growth))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        //val position = adapterPosition
        if (mActivity != null) {
            val launchIntent = Intent(view.context, mActivity)
            launchIntent.putExtra(EXTRA_SHARED_GROWTH, mPrefString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.GROWTH.code)
        }
    }

    fun setActivity(activity: Class<GrowthSelectionActivity>) {
        mActivity = activity
    }
}