package zir.teq.wearable.watchface.config.general.holder

import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.setting.component.Theme

class BooleanPairViewHolder(view: View, val mActivePref: String, val mAmbientPref: String) :
        RecHolder(view), View.OnClickListener {
    var mLayout: LinearLayout
    var mActiveBox: Button
    var mAmbientBox: Button
    var mText: TextView

    init {
        mLayout = view as LinearLayout
        mActiveBox = view.findViewById<View>(R.id.list_item_double_check_active) as Button
        mActiveBox.setOnClickListener(this)
        mAmbientBox = view.findViewById<View>(R.id.list_item_double_check_ambient) as Button
        mAmbientBox.setOnClickListener(this)
        mText = view.findViewById<View>(R.id.list_item_double_check_text) as TextView
    }

    fun updateBoxes(activePref: String, ambientPref: String, text: String) {
        mText.text = text

        val isActiveChecked = ConfigData.prefs.getBoolean(activePref, false)
        (mActiveBox as CheckBox).isChecked = isActiveChecked

        val isAmbientChecked = ConfigData.prefs.getBoolean(ambientPref, false)
        (mAmbientBox as CheckBox).isChecked = isAmbientChecked
    }

    override fun onClick(view: View) {
        val editor = ConfigData.prefs.edit()
        val isActiveChecked = (mActiveBox as CheckBox).isChecked()
        val isAmbientChecked = (mAmbientBox as CheckBox).isChecked()
        val instanceMap = Theme.INSTANCE.map.toMutableMap()
        instanceMap.put(mActivePref, isActiveChecked)
        instanceMap.put(mAmbientPref, isAmbientChecked)
        Theme.INSTANCE = Theme.INSTANCE.copy(map = instanceMap)
        editor.putBoolean(mActivePref, isActiveChecked)
        editor.putBoolean(mAmbientPref, isAmbientChecked)
        editor.apply()
    }
}
