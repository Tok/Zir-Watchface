package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.util.ViewHelper
import java.util.*

class PaletteSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Palette>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder(): viewType: " + viewType)
        val viewHolder = ColorViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_color, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() Element $position set.")
        val pal = mOptions[position]
        val colorViewHolder = viewHolder as ColorViewHolder
        colorViewHolder.bindPalette(pal)
        ViewHelper.bindCircleBorderWidth(colorViewHolder.mView)
        ViewHelper.bindCircleRadius(colorViewHolder.mView)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mView: CircledImageView
        init {
            mView = view.findViewById(R.id.color) as CircledImageView
            view.setOnClickListener(this)
        }

        fun bindPalette(pal: Palette) { mView.setCircleColor(mView.context.getColor(pal.id)) }

        override fun onClick(view: View) {
            val position = adapterPosition
            val color = mOptions[position]
            Log.d(TAG, "Color: $color onClick() position: $position sharedPrefString: $mPrefString")
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = ConfigData.prefs(view.context).edit()
                editor.putString(mPrefString, color.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK) //triggers config activity
            }
            activity.finish()
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}