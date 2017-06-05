package zir.teq.wearable.watchface.config.select.holder

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import zir.teq.wearable.watchface.R
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap




open class ZirPickerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var mButton: Button? = null
    var mPrefString: String? = null

    fun initButton(view: View) {
        mButton = view as Button
    }

    fun setName(name: String) {
        mButton!!.text = name
    }

    fun bindIcon(resourceId: Int, tintColorId: Int?) {
        val ctx = mButton!!.context
        val drawable = ctx.getDrawable(resourceId)
        val scaled = scaleImage(ctx, drawable)
        setFilterColor(ctx, scaled, tintColorId)
        mButton!!.setCompoundDrawablesWithIntrinsicBounds(scaled, null, null, null)
    }

    fun setFilterColor(ctx: Context, drawable: Drawable, colorId: Int?) {
        if (colorId != null) {
            val cf = PorterDuffColorFilter(ctx.getColor(colorId), PorterDuff.Mode.MULTIPLY)
            drawable.setColorFilter(cf)
        }
    }

    open fun setSharedPrefString(sharedPrefString: String) {
        mPrefString = sharedPrefString
    }

    fun scaleImage(ctx: Context, image: Drawable): Drawable {
        var image = image
        if (image == null || image !is BitmapDrawable) {
            return image
        }
        val b = image.bitmap
        val dim = ctx.resources.getDimension(R.dimen.main_config_item_size).toInt()
        val bitmapResized = Bitmap.createScaledBitmap(b, dim, dim, false)
        image = BitmapDrawable(ctx.resources, bitmapResized)
        return image
    }
    companion object {
        private val TAG = this::class.java.simpleName
    }
}