package zir.teq.wearable.watchface.config.select.adapter

import android.support.v7.widget.RecyclerView


class GrowthSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<zir.teq.wearable.watchface.model.data.Growth>) : android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int) =
            GrowthViewHolder(android.view.LayoutInflater.from(parent.context).inflate(zir.teq.wearable.watchface.R.layout.list_item_growth, parent, false))

    override fun onBindViewHolder(vh: android.support.v7.widget.RecyclerView.ViewHolder, position: Int) {
        val growth = mOptions[position]
        val growthViewHolder = vh as zir.teq.wearable.watchface.config.select.adapter.GrowthSelectionAdapter.GrowthViewHolder
        val ctx = growthViewHolder.mFirst.context
        val palName = zir.teq.wearable.watchface.model.ConfigData.prefs(ctx).getString(ctx.getString(zir.teq.wearable.watchface.R.string.saved_palette), zir.teq.wearable.watchface.model.data.Palette.Companion.default.name)
        val pal = zir.teq.wearable.watchface.model.data.Palette.Companion.getByName(palName)
        growthViewHolder.bindGrowth(growth, pal)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class GrowthViewHolder(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener {
        val mFirst = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_growth_current_stroke) as android.support.wearable.view.CircledImageView
        val mSecond = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_growth_grown_stroke) as android.support.wearable.view.CircledImageView
        val mText = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_growth_text) as android.widget.TextView
        init {
            view.setOnClickListener(this)
        }

        fun bindGrowth(growth: zir.teq.wearable.watchface.model.data.Growth, pal: zir.teq.wearable.watchface.model.data.Palette) {
            val ctx = mFirst.context

            val strokeName = zir.teq.wearable.watchface.model.ConfigData.prefs(ctx).getString(ctx.getString(zir.teq.wearable.watchface.R.string.saved_stroke), zir.teq.wearable.watchface.model.data.Stroke.Companion.default.name)
            val stroke = zir.teq.wearable.watchface.model.data.Stroke.Companion.create(ctx, strokeName)

            val themeName = zir.teq.wearable.watchface.model.ConfigData.prefs(ctx).getString(ctx.getString(zir.teq.wearable.watchface.R.string.saved_theme), zir.teq.wearable.watchface.model.data.Theme.Companion.default.name)
            val theme = zir.teq.wearable.watchface.model.data.Theme.Companion.getByName(themeName)
            val outline = zir.teq.wearable.watchface.model.data.Outline.Companion.create(ctx, theme.outlineName)

            val dim: Float = (zir.teq.wearable.watchface.config.select.adapter.GrowthSelectionAdapter.Companion.DISPLAY_ITEM_FACTOR * (stroke.dim + outline.dim))
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
            val growth: zir.teq.wearable.watchface.model.data.Growth = mOptions[position]
            val activity = view.context as android.app.Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                updateSavedValue(view.context, growth)
                activity.setResult(android.app.Activity.RESULT_OK)
            }
            activity.finish()
        }
    }

    fun updateSavedValue(ctx: android.content.Context, growth: zir.teq.wearable.watchface.model.data.Growth) {
        val editor = zir.teq.wearable.watchface.model.ConfigData.prefs(ctx).edit()
        editor.putString(mPrefString, growth.name)
        editor.commit()
    }

    companion object {
        val DISPLAY_ITEM_FACTOR = 0.5F //=radius to circumfence?
    }
}