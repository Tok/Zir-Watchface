package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.Stroke

class StrokeSelectionAdapter(
        private val mSharedPrefString: String?,
        private val mStrokeOptionsDataSet: ArrayList<Stroke>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder(): viewType: " + viewType)
        val viewHolder = StrokeViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_stroke, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() Element $position set.")
        val stroke = mStrokeOptionsDataSet[position]
        val strokeViewHolder = viewHolder as StrokeViewHolder
        val ctx = viewHolder.itemView.context
        strokeViewHolder.setItemDisplayColor(ctx.getColor(R.color.white))
        strokeViewHolder.setItemDisplayRadius(stroke.dim)
    }

    override fun getItemCount(): Int {
        return mStrokeOptionsDataSet.size
    }

    inner class StrokeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val mView: CircledImageView
        init {
            mView = view.findViewById(R.id.stroke) as CircledImageView
            view.setOnClickListener(this)
        }

        fun setItemDisplayColor(color: Int) {
            mView.setCircleColor(color)
        }

        fun setItemDisplayRadius(radius: Float) {
            mView.circleRadius = radius
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val stroke = mStrokeOptionsDataSet[position]
            Log.d(TAG, "Stroke: $stroke onClick() position: $position sharedPrefString: $mSharedPrefString")
            val activity = view.context as Activity
            if (mSharedPrefString != null && !mSharedPrefString.isEmpty()) {
                val sharedPref = activity.getSharedPreferences(
                        activity.getString(zir.teq.wearable.watchface.R.string.zir_watch_preference_file_key),
                        Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString(mSharedPrefString, stroke.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK) //triggers config activity
            }
            activity.finish()
        }
    }

    companion object {
        private val TAG = StrokeSelectionAdapter::class.java.simpleName
    }
}