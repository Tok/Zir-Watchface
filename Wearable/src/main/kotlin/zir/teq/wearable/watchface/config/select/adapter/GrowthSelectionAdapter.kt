package zir.teq.wearable.watchface.config.select.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Growth
import zir.teq.wearable.watchface.model.data.Palette


class GrowthSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Growth>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            GrowthViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_growth, parent, false))

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val growth = mOptions[position]
        val viewHolder = vh as GrowthViewHolder
        val ctx = viewHolder.mFirst.context
        viewHolder.bindGrowth(growth, ConfigData.palette)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class GrowthViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mFirst = view.findViewById(R.id.list_item_growth_current_stroke) as CircledImageView
        val mSecond = view.findViewById(R.id.list_item_growth_grown_stroke) as CircledImageView
        val mText = view.findViewById(R.id.list_item_growth_text) as TextView
        init {
            view.setOnClickListener(this)
        }

        fun bindGrowth(growth: Growth, pal: Palette) {
            val dim: Float = (GrowthSelectionAdapter.DISPLAY_ITEM_FACTOR * (ConfigData.stroke.dim + ConfigData.outline.dim))
            mFirst.circleRadius = dim
            mFirst.setCircleColor(pal.light())
            mFirst.setCircleBorderWidth(ConfigData.outline.dim)

            val growthDim: Float = dim + growth.dim
            mSecond.circleRadius = growthDim
            mSecond.setCircleColor(pal.light())
            mSecond.setCircleBorderWidth(ConfigData.outline.dim)

            mText.text = growth.name
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val growth: Growth = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                updateSavedValue(growth)
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }

    fun updateSavedValue(growth: Growth) {
        val editor = ConfigData.prefs.edit()
        editor.putString(mPrefString, growth.name)
        editor.commit()
    }

    companion object {
        val DISPLAY_ITEM_FACTOR = 0.5F //=radius to circumfence?
    }
}