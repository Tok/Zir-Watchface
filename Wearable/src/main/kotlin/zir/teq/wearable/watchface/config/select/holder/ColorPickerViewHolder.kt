package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.ColorSelectionActivity

class ColorPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    private var mActivity: Class<ColorSelectionActivity>? = null

    init {
        initButton(view.findViewById(R.id.color_picker_button))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val position = adapterPosition
        Log.d(TAG, "onClick() position: " + position)
        if (mActivity != null) {
            val launchIntent = Intent(view.context, mActivity)
            launchIntent.putExtra(ColorSelectionActivity.EXTRA_SHARED_COLOR, mSharedPrefString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.UPDATE_COLORS_CONFIG_REQUEST_CODE)
        }
    }

    fun setLaunchActivity(activity: Class<ColorSelectionActivity>) {
        mActivity = activity
    }

    companion object {
        private val TAG = ColorPickerViewHolder::class.java.simpleName
    }
}
