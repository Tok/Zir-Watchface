package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.ZirWatchConfigActivity
import zir.teq.wearable.watchface.config.select.DimSelectionActivity

class DimPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    private var mActivity: Class<DimSelectionActivity>? = null

    init {
        initButton(view.findViewById(R.id.config_list_item_dim))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        Log.d(TAG, "onClick() adapterPosition: " + adapterPosition)
        if (mActivity != null) {
            val launchIntent = Intent(view.context, mActivity)
            launchIntent.putExtra(DimSelectionActivity.EXTRA_SHARED_DIM, mPrefString)
            val activity = view.context as Activity
            activity.startActivityForResult(
                    launchIntent,
                    ZirWatchConfigActivity.DIM.code)
        }
    }

    fun setLaunchActivity(activity: Class<DimSelectionActivity>) {
        mActivity = activity
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
