package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.StrokeSelectionActivity

class StrokePickerViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private val mAppearanceButton: Button = view.findViewById(R.id.color_picker_button) as Button
    private var mSharedPrefResourceString: String? = null
    private var mLaunchActivityToSelectStroke: Class<StrokeSelectionActivity>? = null

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val position = adapterPosition
        Log.d(TAG, "onClick() position: " + position)
        if (mLaunchActivityToSelectStroke != null) {
            val launchIntent = Intent(view.context, mLaunchActivityToSelectStroke)
            launchIntent.putExtra(StrokeSelectionActivity.EXTRA_SHARED_STROKE, mSharedPrefResourceString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.UPDATE_STROKE_CONFIG_REQUEST_CODE)
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

    fun setLaunchActivityToSelectStroke(activity: Class<StrokeSelectionActivity>) {
        mLaunchActivityToSelectStroke = activity
    }

    companion object {
        private val TAG = StrokePickerViewHolder::class.java.simpleName
    }
}