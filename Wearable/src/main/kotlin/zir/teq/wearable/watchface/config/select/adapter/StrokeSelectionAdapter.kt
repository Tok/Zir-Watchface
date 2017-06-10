package zir.teq.wearable.watchface.config.select.adapter

import android.support.v7.widget.RecyclerView

class StrokeSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<zir.teq.wearable.watchface.model.data.Stroke>) : android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int) =
            StrokeViewHolder(android.view.LayoutInflater.from(parent.context).inflate(zir.teq.wearable.watchface.R.layout.list_item_stroke, parent, false))

    override fun onBindViewHolder(vh: android.support.v7.widget.RecyclerView.ViewHolder, position: Int) {
        val stroke = mOptions[position]
        val strokeViewHolder = vh as zir.teq.wearable.watchface.config.select.adapter.StrokeSelectionAdapter.StrokeViewHolder
        zir.teq.wearable.watchface.util.ViewHelper.bindCircleColor(strokeViewHolder.mView)
        zir.teq.wearable.watchface.util.ViewHelper.bindCircleBorderWidth(strokeViewHolder.mView)
        strokeViewHolder.bindStroke(stroke)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class StrokeViewHolder(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener {
        val mView: android.support.wearable.view.CircledImageView
        init {
            mView = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_stroke) as android.support.wearable.view.CircledImageView
            view.setOnClickListener(this)
        }

        fun bindStroke(stroke: zir.teq.wearable.watchface.model.data.Stroke) {
            val ctx = mView.context
            val themeName = zir.teq.wearable.watchface.model.ConfigData.prefs(ctx).getString(ctx.getString(zir.teq.wearable.watchface.R.string.saved_theme), zir.teq.wearable.watchface.model.data.Theme.Companion.default.name)
            val theme = zir.teq.wearable.watchface.model.data.Theme.Companion.getByName(themeName)
            val outline = zir.teq.wearable.watchface.model.data.Outline.Companion.create(ctx, theme.outlineName)
            mView.circleRadius = stroke.dim + outline.dim
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val stroke = mOptions[position]
            val activity = view.context as android.app.Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = zir.teq.wearable.watchface.model.ConfigData.prefs(view.context).edit()
                editor.putString(mPrefString, stroke.name)
                editor.commit()
                activity.setResult(android.app.Activity.RESULT_OK)
            }
            activity.finish()
        }
    }

    companion object {
        val DISPLAY_ITEM_FACTOR = 0.5F //=radius to circumfence?
    }
}