package zir.teq.wearable.watchface.config.select.holder

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.graphics.PorterDuffColorFilter
import zir.teq.wearable.watchface.R


open class ZirPickerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var mButton: Button? = null
    var mSharedPrefString: String? = null

    fun initButton(view: View) {
        mButton = view as Button
    }

    fun setName(name: String) {
        mButton!!.text = name
    }

    fun setIcon(resourceId: Int, tintColorId: Int?) {
        val ctx = mButton!!.context
        val drawable = ctx.getDrawable(resourceId)
        if (tintColorId != null) {
            val cf = PorterDuffColorFilter(ctx.getColor(tintColorId), PorterDuff.Mode.MULTIPLY)
            drawable.setColorFilter(cf)
        }
        mButton!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }

    fun setSharedPrefString(sharedPrefString: String) {
        mSharedPrefString = sharedPrefString
    }

    companion object {
        private val TAG = ZirPickerViewHolder::class.java.simpleName
    }
}