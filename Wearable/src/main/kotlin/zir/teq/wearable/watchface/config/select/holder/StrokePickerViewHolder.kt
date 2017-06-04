package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.StrokeSelectionActivity

class StrokePickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    private var mActivity: Class<StrokeSelectionActivity>? = null

    init {
        initButton(view.findViewById(R.id.config_list_item_stroke))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val position = adapterPosition
        Log.d(TAG, "onClick() position: " + position)
        if (mActivity != null) {
            val launchIntent = Intent(view.context, mActivity)
            launchIntent.putExtra(StrokeSelectionActivity.EXTRA_SHARED_STROKE, mPrefString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.STROKE.code)
        }
    }

    fun setLaunchActivity(activity: Class<StrokeSelectionActivity>) {
        mActivity = activity
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}