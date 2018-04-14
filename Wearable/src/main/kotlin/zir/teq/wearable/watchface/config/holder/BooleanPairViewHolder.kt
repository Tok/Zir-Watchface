package zir.teq.wearable.watchface.config.holder

import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecHolder

class BooleanPairViewHolder(view: View) : RecHolder(view), View.OnClickListener {
    var mLayout: LinearLayout
    var mActiveBox: Button
    var mAmbientBox: Button
    var mText: TextView
    var mActivePref: String? = null
    var mAmbientPref: String? = null

    init {
        mLayout = view as LinearLayout
        mActiveBox = view.findViewById<View>(R.id.list_item_double_check_active) as Button
        mActiveBox.setOnClickListener(this)
        mAmbientBox = view.findViewById<View>(R.id.list_item_double_check_ambient) as Button
        mAmbientBox.setOnClickListener(this)
        mText = view.findViewById<View>(R.id.list_item_double_check_text) as TextView
    }

    fun updateBoxes(activePref: String, ambientPref: String, text: String) {
        mActivePref = activePref
        mAmbientPref = ambientPref
        mText.text = text

        val isActiveChecked = ConfigData.prefs.getBoolean(activePref, false)
        (mActiveBox as CheckBox).isChecked = isActiveChecked

        val isAmbientChecked = ConfigData.prefs.getBoolean(ambientPref, false)
        (mAmbientBox as CheckBox).isChecked = isAmbientChecked
    }

    override fun onClick(view: View) {
        val isReady = !mActivePref!!.isEmpty() && !mAmbientPref!!.isEmpty()
        if (isReady) {
            val editor = ConfigData.prefs.edit()
            editor.putBoolean(mActivePref, (mActiveBox as CheckBox).isChecked())
            editor.putBoolean(mAmbientPref, (mAmbientBox as CheckBox).isChecked())
            editor.apply()
        }
    }
}
