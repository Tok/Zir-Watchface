package zir.teq.wearable.watchface.config.select

import android.support.v7.widget.RecyclerView
import zir.teq.wearable.watchface.model.data.Stroke

class StrokeSelectionAdapter(
        private val mSharedPrefString: String?,
        private val mStrokeOptionsDataSet: java.util.ArrayList<Stroke>) : android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): android.support.v7.widget.RecyclerView.ViewHolder {
        android.util.Log.d(zir.teq.wearable.watchface.config.select.StrokeSelectionAdapter.Companion.TAG, "onCreateViewHolder(): viewType: " + viewType)
        val viewHolder = StrokeViewHolder(
                android.view.LayoutInflater.from(parent.context).inflate(zir.teq.wearable.watchface.R.layout.stroke_config_list_item, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: android.support.v7.widget.RecyclerView.ViewHolder, position: Int) {
        android.util.Log.d(zir.teq.wearable.watchface.config.select.StrokeSelectionAdapter.Companion.TAG, "onBindViewHolder() Element $position set.")
        val stroke = mStrokeOptionsDataSet[position]
        val strokeViewHolder = viewHolder as zir.teq.wearable.watchface.config.select.StrokeSelectionAdapter.StrokeViewHolder
        val ctx = viewHolder.itemView.context
        strokeViewHolder.setItemDisplayColor(ctx.getColor(zir.teq.wearable.watchface.R.color.white))
        strokeViewHolder.setItemDisplayRadius(stroke.dim)
    }

    override fun getItemCount(): Int {
        return mStrokeOptionsDataSet.size
    }

    inner class StrokeViewHolder(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener {
        private val mView: android.support.wearable.view.CircledImageView
        init {
            mView = view.findViewById(zir.teq.wearable.watchface.R.id.stroke) as android.support.wearable.view.CircledImageView
            view.setOnClickListener(this)
        }

        fun setItemDisplayColor(color: Int) {
            mView.setCircleColor(color)
        }
        fun setItemDisplayRadius(radius: Float) {
            mView.circleRadius = radius
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val stroke = mStrokeOptionsDataSet[position]
            android.util.Log.d(zir.teq.wearable.watchface.config.select.StrokeSelectionAdapter.Companion.TAG, "Stroke: $stroke onClick() position: $position sharedPrefString: $mSharedPrefString")
            val activity = view.context as android.app.Activity
            if (mSharedPrefString != null && !mSharedPrefString.isEmpty()) {
                val sharedPref = activity.getSharedPreferences(
                        activity.getString(zir.teq.wearable.watchface.R.string.zir_watch_preference_file_key),
                        android.content.Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString(mSharedPrefString, stroke.name)
                editor.commit()
                activity.setResult(android.app.Activity.RESULT_OK) //triggers config activity
            }
            activity.finish()
        }
    }

    companion object {
        private val TAG = zir.teq.wearable.watchface.config.select.StrokeSelectionAdapter::class.java.simpleName
    }
}