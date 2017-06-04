package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.GrowthSelectionActivity
import zir.teq.wearable.watchface.config.select.GrowthSelectionActivity.Companion.EXTRA_SHARED_GROWTH

class GrowthPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    private var mActivity: Class<GrowthSelectionActivity>? = null

    init {
        initButton(view.findViewById(R.id.growth_picker_button))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val position = adapterPosition
        Log.d(TAG, "onClick() position: " + position)
        if (mActivity != null) {
            val launchIntent = Intent(view.context, mActivity)
            launchIntent.putExtra(EXTRA_SHARED_GROWTH, mPrefString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.GROWTH.code)
        }
    }

    fun setLaunchActivity(activity: Class<GrowthSelectionActivity>) {
        mActivity = activity
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}