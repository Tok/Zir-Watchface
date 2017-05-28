package zir.teq.wearable.watchface.config.select.holder

import android.util.Log
import android.view.View
import zir.teq.wearable.watchface.R

class BooleanPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    //private var mActivity: Class<BooleanSelectionActivity>? = null

    init {
        initButton(view.findViewById(R.id.checkbox_list_item))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        Log.d(TAG, "##### BooleanPickerViewHolder onClick() mPrefString: " + mPrefString)

        //if (mActivity != null) {
            //val launchIntent = Intent(view.context, mActivity)

            /*
            val col = Col.findActive(view.context)
            Log.d(TAG, "Color changed to $col")
            launchIntent.putExtra("color", col.name)
            launchIntent.putExtra(ColorSelectionActivity.EXTRA_SHARED_COLOR, mPrefString)
            */
            /*
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.UPDATE_DRAW_CIRCLES_REQUEST_CODE)
                    */
        //}
    }

    /*
    fun setLaunchActivity(activity: Class<BooleanSelectionActivity>) {
        mActivity = activity
    }
    */

    companion object {
        private val TAG = BooleanPickerViewHolder::class.java.simpleName
    }
}
