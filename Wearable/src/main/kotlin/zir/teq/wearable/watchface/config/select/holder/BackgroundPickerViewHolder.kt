package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.BackgroundSelectionActivity

class BackgroundPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    private var mActivity: Class<BackgroundSelectionActivity>? = null

    init {
        initButton(view.findViewById(R.id.config_list_item_background))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        Log.d(TAG, "onClick() adapterPosition: " + adapterPosition)
        if (mActivity != null) {
            val launchIntent = Intent(view.context, mActivity)
            launchIntent.putExtra(BackgroundSelectionActivity.EXTRA_SHARED_BACKGROUND, mPrefString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.BACKGROUND.code)
        }
    }

    fun setLaunchActivity(activity: Class<BackgroundSelectionActivity>) {
        mActivity = activity
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
