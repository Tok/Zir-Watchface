package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.AlphaSelectionActivity

class AlphaPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    private var mActivity: Class<AlphaSelectionActivity>? = null

    init {
        initButton(view.findViewById(R.id.config_list_item_alpha))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (mActivity != null) {
            val launchIntent = Intent(view.context, mActivity)
            launchIntent.putExtra(AlphaSelectionActivity.EXTRA_SHARED_ALPHA, mPrefString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.ALPHA.code)
        }
    }

    fun setLaunchActivity(activity: Class<AlphaSelectionActivity>) {
        mActivity = activity
    }
}
