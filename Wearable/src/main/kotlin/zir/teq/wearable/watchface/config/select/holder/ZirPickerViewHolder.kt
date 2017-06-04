package zir.teq.wearable.watchface.config.select.holder

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button


open class ZirPickerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var mButton: Button? = null
    var mPrefString: String? = null

    fun initButton(view: View) {
        mButton = view as Button
    }

    fun setName(name: String) {
        mButton!!.text = name
    }

    fun setIcon(resourceId: Int, tintColorId: Int?) {
        val ctx = mButton!!.context
        val drawable = ctx.getDrawable(resourceId)
        setFilterColor(ctx, drawable, tintColorId)
        mButton!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }

    /*
    fun drawIcon(resourceId: Int, theme: Theme) {
        val ctx = mButton!!.context
        val can = Canvas()
        mButton!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }*/

    fun setFilterColor(ctx: Context, drawable: Drawable, colorId: Int?) {
        if (colorId != null) {
            val cf = PorterDuffColorFilter(ctx.getColor(colorId), PorterDuff.Mode.MULTIPLY)
            drawable.setColorFilter(cf)
        }
    }

    open fun setSharedPrefString(sharedPrefString: String) {
        mPrefString = sharedPrefString
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}