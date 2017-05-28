package zir.teq.wearable.watchface.config.select.holder

import android.util.Log
import android.view.View
import zir.teq.wearable.watchface.R

class BooleanPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    init {
        initButton(view.findViewById(R.id.checkbox_list_item))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        Log.d(TAG, "##### BooleanPickerViewHolder onClick() mPrefString: " + mPrefString)
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
