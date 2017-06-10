package zir.teq.wearable.watchface.config.select.holder

import android.view.View
import android.widget.CheckBox
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

class BooleanPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    init {
        initButton(view.findViewById(R.id.list_item_checkbox))
        view.setOnClickListener(this)
    }

    override fun setSharedPrefString(sharedPrefString: String) {
        val checkBox = mButton as CheckBox
        val isActive = ConfigData.prefs(checkBox.context).getBoolean(sharedPrefString, false)
        checkBox.setChecked(isActive)
        mPrefString = sharedPrefString
    }

    override fun onClick(view: View) {
        if (!mPrefString.isEmpty()) {
            val editor = ConfigData.prefs(view.context).edit()
            val checkBox = mButton as CheckBox
            editor.putBoolean(mPrefString, checkBox.isChecked())
            editor.commit()
        }
    }
}
