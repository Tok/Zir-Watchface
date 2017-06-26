package zir.teq.wearable.watchface.config.select.holder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.RecHolder


open class ZirPickerViewHolder(view: View) : RecHolder(view) {
    private lateinit var mActivity: Class<out Activity>
    lateinit var mButton: Button
    lateinit var mPrefString: String

    fun handleClick(view: View, extra: String) = handleClick(view, extra, NO_REQ)
    fun handleClick(view: View, extra: String, req: Int) {
        if (mActivity is Class<out Activity>) {
            val launchIntent = Intent(view.context, mActivity)
            val pref = mPrefString
            launchIntent.putExtra(extra, pref)
            val activity = view.context as Activity
            activity.startActivityForResult(launchIntent, req)
        }
    }

    fun setActivity(activity: Class<out Activity>) {
        mActivity = activity
    }

    fun initButton(view: View) {
        mButton = view as Button
    }

    fun setName(name: String) {
        mButton.text = name
    }

    fun bindIcon(resourceId: Int, tintColorId: Int?) {
        val ctx = mButton.context
        val drawable = ctx.getDrawable(resourceId)
        val scaled = scaleImage(ctx, drawable)
        setFilterColor(ctx, scaled, tintColorId)
        mButton.setCompoundDrawablesWithIntrinsicBounds(scaled, null, null, null)
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
        if (image is BitmapDrawable) {
            val dim = ctx.resources.getDimension(R.dimen.main_config_item_size).toInt()
            val resizedBitmap = Bitmap.createScaledBitmap(image.bitmap, dim, dim, false)
            return BitmapDrawable(ctx.resources, resizedBitmap)
        }
        return image
    }

    companion object {
        private val NO_REQ = 0 //default value if unused
    }
}