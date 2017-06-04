package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.util.Log
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        android.util.Log.d(TAG, "onCreateViewHolder(): viewType: " + viewType)
        val viewHolder = GrowthViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_growth, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        android.util.Log.d(TAG, "onBindViewHolder() Element $position set.")
        val growth = mOptions[position]
        val growthViewHolder = viewHolder as GrowthSelectionAdapter.GrowthViewHolder
        val ctx = growthViewHolder.mPrimary.context
        val palName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_palette), Palette.default.name)
        val pal = Palette.getByName(palName)
        growthViewHolder.bindGrowth(growth, pal)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class GrowthViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mPrimary = view.findViewById(R.id.primary_growth_button) as CircledImageView
        val mSecondary = view.findViewById(R.id.secondary_growth_button) as CircledImageView
        val mText = view.findViewById(R.id.growth_text) as TextView
        init {
            view.setOnClickListener(this)
        }

        fun bindGrowth(growth: Growth, pal: Palette) {
            val ctx = mPrimary.context

            val strokeName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_stroke), Stroke.default.name)
            val stroke = Stroke.create(ctx, strokeName)

            val themeName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_theme), Theme.default.name)
            val theme = Theme.getByName(themeName)
            val outline = Outline.create(ctx, theme.outlineName)

            val dim: Float = (DISPLAY_ITEM_FACTOR * (stroke.dim + outline.dim))
            mPrimary.circleRadius = dim
            mPrimary.setCircleColor(ctx.getColor(pal.lightId))

            val growthDim: Float = dim + growth.dim
            mSecondary.circleRadius = growthDim
            mSecondary.setCircleColor(ctx.getColor(pal.lightId))

            mText.text = growth.name
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val growth: Growth = mOptions[position]
            Log.d(TAG, "growth: $growth onClick() position: $position sharedPrefString: $mPrefString")
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                updateSavedValue(view.context, growth)
                activity.setResult(android.app.Activity.RESULT_OK) //triggers config activity
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
        private val TAG = this::class.java.simpleName
    }
}