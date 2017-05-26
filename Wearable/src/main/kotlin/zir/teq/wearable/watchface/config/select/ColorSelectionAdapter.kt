package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.model.data.Col
import zir.teq.wearable.watchface.R
import java.util.*

class ColorSelectionAdapter(
        private val mSharedPrefString: String?,
        private val mColorOptionsDataSet: ArrayList<Col>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder(): viewType: " + viewType)
        val viewHolder = ColorViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.color_config_list_item, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() Element $position set.")
        val color = mColorOptionsDataSet[position]
        val colorViewHolder = viewHolder as ColorViewHolder
        colorViewHolder.setItemDisplayColor(viewHolder.itemView.context, color)
    }

    override fun getItemCount(): Int {
        return mColorOptionsDataSet.size
    }

    inner class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val mView: CircledImageView
        init {
            mView = view.findViewById(R.id.color) as CircledImageView
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val color = mColorOptionsDataSet[position]
            Log.d(TAG, "Color: $color onClick() position: $position sharedPrefString: $mSharedPrefString")
            val activity = view.context as Activity
            if (mSharedPrefString != null && !mSharedPrefString.isEmpty()) {
                val sharedPref = activity.getSharedPreferences(
                        activity.getString(R.string.zir_watch_preference_file_key),
                        Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString(mSharedPrefString, color.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK) //triggers config activity
            }
            activity.finish()
        }

        fun setItemDisplayColor(ctx: Context, col: Col) {
            mView.setCircleColor(ctx.getColor(col.lightId))
        }
    }

    companion object {
        private val TAG = ColorSelectionAdapter::class.java.simpleName
    }
}