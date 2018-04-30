package zir.teq.wearable.watchface.config.general.holder

import android.app.Activity
import android.support.wear.widget.CircularProgressLayout
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecHolder

class PropsHolder(view: View, val pref: String, val names: List<String>) : RecHolder(view), View.OnClickListener {
    val mView = view as LinearLayout
    val mCircle: CircularProgressLayout = view.findViewById(R.id.list_item_cicle_layout)
    val mTextView: TextView = view.findViewById(R.id.list_item_text)

    init {
        mView.setOnClickListener(this)
    }

    fun bindProps(name: String, iconId: Int) {
        mCircle.foreground = mView.context.getDrawable(iconId)
        mCircle.backgroundColor = ConfigData.palette().half()
        mCircle.strokeWidth = 1F
        mTextView.text = name
    }

    override fun onClick(view: View) {
        val activity = view.context as Activity
        val editor = ConfigData.prefs.edit()
        editor.putString(pref, names[adapterPosition])
        editor.apply()
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }
}
