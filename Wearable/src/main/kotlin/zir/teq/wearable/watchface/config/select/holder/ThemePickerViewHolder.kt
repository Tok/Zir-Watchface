package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity
import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity.Companion.EXTRA_SHARED_THEME

class ThemePickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    private var mActivity: Class<ThemeSelectionActivity>? = null

    init {
        initButton(view.findViewById(R.id.theme_picker_button))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val position = adapterPosition
        Log.d(TAG, "onClick() position: " + position)
        if (mActivity != null) {
            val launchIntent = Intent(view.context, mActivity)
            launchIntent.putExtra(EXTRA_SHARED_THEME, mPrefString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.THEME.code)
        }
    }

    fun setLaunchActivity(activity: Class<ThemeSelectionActivity>) {
        mActivity = activity
    }

    companion object {
        private val TAG = ThemePickerViewHolder::class.java.simpleName
    }
}