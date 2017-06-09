package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Outline
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.util.ViewHelper

class StrokeSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Stroke>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            StrokeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_stroke, parent, false))

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val stroke = mOptions[position]
        val strokeViewHolder = viewHolder as StrokeViewHolder
        ViewHelper.bindCircleColor(strokeViewHolder.mView)
        ViewHelper.bindCircleBorderWidth(strokeViewHolder.mView)
        strokeViewHolder.bindStroke(stroke)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class StrokeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mView: CircledImageView
        init {
            mView = view.findViewById(R.id.list_item_stroke) as CircledImageView
            view.setOnClickListener(this)
        }

        fun bindStroke(stroke: Stroke) {
            val ctx = mView.context
            val themeName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_theme), Theme.default.name)
            val theme = Theme.getByName(themeName)
            val outline = Outline.create(ctx, theme.outlineName)
            mView.circleRadius = stroke.dim + outline.dim
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val stroke = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = ConfigData.prefs(view.context).edit()
                editor.putString(mPrefString, stroke.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }

    companion object {
        val DISPLAY_ITEM_FACTOR = 0.5F //=radius to circumfence?
    }
}