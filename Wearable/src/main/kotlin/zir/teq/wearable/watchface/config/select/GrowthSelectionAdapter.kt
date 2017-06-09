package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.*


class GrowthSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Growth>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            GrowthViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_growth, parent, false))

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val growth = mOptions[position]
        val growthViewHolder = vh as GrowthSelectionAdapter.GrowthViewHolder
        val ctx = growthViewHolder.mFirst.context
        val palName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_palette), Palette.default.name)
        val pal = Palette.getByName(palName)
        growthViewHolder.bindGrowth(growth, pal)
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
            val ctx = mFirst.context

            val strokeName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_stroke), Stroke.default.name)
            val stroke = Stroke.create(ctx, strokeName)

            val themeName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_theme), Theme.default.name)
            val theme = Theme.getByName(themeName)
            val outline = Outline.create(ctx, theme.outlineName)

            val dim: Float = (DISPLAY_ITEM_FACTOR * (stroke.dim + outline.dim))
            mFirst.circleRadius = dim
            mFirst.setCircleColor(ctx.getColor(pal.lightId))
            mFirst.setCircleBorderWidth(outline.dim)

            val growthDim: Float = dim + growth.dim
            mSecond.circleRadius = growthDim
            mSecond.setCircleColor(ctx.getColor(pal.lightId))
            mSecond.setCircleBorderWidth(outline.dim)

            mText.text = growth.name
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val growth: Growth = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                updateSavedValue(view.context, growth)
                activity.setResult(android.app.Activity.RESULT_OK)
            }
            activity.finish()
        }
    }

    fun updateSavedValue(ctx: Context, growth: Growth) {
        val editor = ConfigData.prefs(ctx).edit()
        editor.putString(mPrefString, growth.name)
        editor.commit()
    }

    companion object {
        val DISPLAY_ITEM_FACTOR = 0.5F //=radius to circumfence?
    }
}