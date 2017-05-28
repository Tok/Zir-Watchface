package zir.teq.wearable.watchface.config.select.holder

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.CheckBox
import zir.teq.wearable.watchface.R

class BooleanPickerViewHolder(view: View) : ZirPickerViewHolder(view), View.OnClickListener {
    init {
        initButton(view.findViewById(R.id.checkbox_list_item))
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        Log.d(TAG, "onClick() mPrefString: $mPrefString")
        if (!mPrefString!!.isEmpty()) {
            val ctx = view.context
            val prefs = ctx.getSharedPreferences(
                    ctx.getString(R.string.zir_watch_preference_file_key),
                    Context.MODE_PRIVATE)
            val editor = prefs.edit()
            val checkBox = mButton as CheckBox
            editor.putString(mPrefString, checkBox.isChecked().toString())
            editor.commit()
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
