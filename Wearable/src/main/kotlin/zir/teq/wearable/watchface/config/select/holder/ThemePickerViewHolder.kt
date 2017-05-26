package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.ThemeSelectionActivity

class ThemePickerViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private val mAppearanceButton: Button = view.findViewById(R.id.theme_picker_button) as Button
    private var mSharedPrefResourceString: String? = null
    private var mLaunchActivityToSelectTheme: Class<ThemeSelectionActivity>? = null

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val position = adapterPosition
        Log.d(TAG, "onClick() position: " + position)
        if (mLaunchActivityToSelectTheme != null) {
            val launchIntent = Intent(view.context, mLaunchActivityToSelectTheme)
            launchIntent.putExtra(ThemeSelectionActivity.EXTRA_SHARED_THEME, mSharedPrefResourceString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.UPDATE_THEME_CONFIG_REQUEST_CODE)
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

    fun setLaunchActivityToSelectTheme(activity: Class<ThemeSelectionActivity>) {
        mLaunchActivityToSelectTheme = activity
    }

    companion object {
        private val TAG = ThemePickerViewHolder::class.java.simpleName
    }
}