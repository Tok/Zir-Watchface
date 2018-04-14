package zir.teq.wearable.watchface.config.holder

import android.view.View
import android.widget.CheckBox
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

class BooleanViewHolder(view: View) : RecSelectionViewHolder(view), View.OnClickListener {
    init {
        initButton(view.findViewById<View>(R.id.list_item_checkbox))
        view.setOnClickListener(this)
    }

    override fun setSharedPrefString(sharedPrefString: String) {
        val checkBox = mButton as CheckBox
        val isActive = ConfigData.prefs.getBoolean(sharedPrefString, false)
        checkBox.setChecked(isActive)
        mPrefString = sharedPrefString
    }

    override fun onClick(view: View) {
        if (!mPrefString.isEmpty()) {
            val editor = ConfigData.prefs.edit()
            val checkBox = mButton as CheckBox
            editor.putBoolean(mPrefString, checkBox.isChecked())
            editor.apply()
        }
    }
}
