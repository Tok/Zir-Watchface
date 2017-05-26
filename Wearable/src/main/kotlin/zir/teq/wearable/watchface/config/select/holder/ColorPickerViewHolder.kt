package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.ColorSelectionActivity

class ColorPickerViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private val mAppearanceButton: Button = view.findViewById(R.id.color_picker_button) as Button
    private var mSharedPrefResourceString: String? = null
    private var mLaunchActivityToSelectColor: Class<ColorSelectionActivity>? = null

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val position = adapterPosition
        Log.d(TAG, "onClick() position: " + position)
        if (mLaunchActivityToSelectColor != null) {
            val launchIntent = Intent(view.context, mLaunchActivityToSelectColor)
            launchIntent.putExtra(ColorSelectionActivity.EXTRA_SHARED_COLOR, mSharedPrefResourceString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.UPDATE_COLORS_CONFIG_REQUEST_CODE)
        }
    }

    fun setName(name: String) {
        mAppearanceButton.text = name
    }

    fun setIcon(resourceId: Int) {
        val context = mAppearanceButton.context
        mAppearanceButton.setCompoundDrawablesWithIntrinsicBounds(
                context.getDrawable(resourceId), null, null, null)
    }

    fun setSharedPrefString(sharedPrefString: String) {
        mSharedPrefResourceString = sharedPrefString
    }

    fun setLaunchActivityToSelectColor(activity: Class<ColorSelectionActivity>) {
        mLaunchActivityToSelectColor = activity
    }

    companion object {
        private val TAG = ColorPickerViewHolder::class.java.simpleName
    }
}
