package zir.teq.wearable.watchface.config.select.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button

open class ZirViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var mButton: Button? = null
    var mSharedPrefString: String? = null

    fun initButton(view: View) {
        mButton = view as Button
    }

    fun setName(name: String) {
        mButton!!.text = name
    }

    fun setIcon(resourceId: Int) {
        val ctx = mButton!!.context
        mButton!!.setCompoundDrawablesWithIntrinsicBounds(ctx.getDrawable(resourceId), null, null, null)
    }

    fun setSharedPrefString(sharedPrefString: String) {
        mSharedPrefString = sharedPrefString
    }

    companion object {
        private val TAG = ZirViewHolder::class.java.simpleName
    }
}