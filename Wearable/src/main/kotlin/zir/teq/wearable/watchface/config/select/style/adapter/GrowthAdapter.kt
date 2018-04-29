package zir.teq.wearable.watchface.config.select.style.adapter

import android.app.Activity
import android.support.wear.widget.CircularProgressLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.data.settings.color.Palette
import zir.teq.wearable.watchface.model.data.settings.style.Growth


class GrowthAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Growth>) : RecAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            GrowthViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_growth, parent, false))

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val growth = mOptions[position]
        val viewHolder = vh as GrowthViewHolder
        viewHolder.bindGrowth(growth, ConfigData.palette())
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class GrowthViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mFirst: CircularProgressLayout = view.findViewById(R.id.list_item_growth_current_stroke)
        val mSecond: CircularProgressLayout = view.findViewById(R.id.list_item_growth_grown_stroke)
        val mText: TextView = view.findViewById(R.id.list_item_growth_text)

        init {
            view.setOnClickListener(this)
        }

        fun bindGrowth(growth: Growth, pal: Palette) {
            mFirst.setBackgroundColor(pal.light())
            mFirst.foreground = mText.context.getDrawable(R.drawable.icon_growth_off)
            mFirst.strokeWidth = 1F
            mSecond.setBackgroundColor(pal.light())
            mSecond.foreground = mText.context.getDrawable(growth.iconId)
            mSecond.strokeWidth = 1F
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
        editor.apply()
    }

    companion object {
        val DISPLAY_ITEM_FACTOR = 0.5F //=radius to circumfence?
    }
}