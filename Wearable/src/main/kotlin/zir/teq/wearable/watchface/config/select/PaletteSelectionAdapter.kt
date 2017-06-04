package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Outline
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.model.data.Theme
import java.util.*

class PaletteSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Palette>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder(): viewType: " + viewType)
        val viewHolder = ColorViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_palette, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() Element $position set.")
        val pal = mOptions[position]
        val colorViewHolder = viewHolder as ColorViewHolder
        colorViewHolder.bindPalette(pal)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mFirst = view.findViewById(R.id.list_item_palette_first_cirlce) as CircledImageView
        val mSecond = view.findViewById(R.id.list_item_palette_second_circle) as CircledImageView
        val mThird = view.findViewById(R.id.list_item_palette_third_circle) as CircledImageView
        init {
            mView.setOnClickListener(this)
        }

        fun bindPalette(pal: Palette) {
            val ctx = mFirst.context
            val prefs = ConfigData.prefs(ctx)
            val strokeName = prefs.getString(ctx.getString(R.string.saved_stroke), Stroke.default.name)
            val stroke = Stroke.create(ctx, strokeName)

            val themeName = prefs.getString(ctx.getString(R.string.saved_theme), Theme.default.name)
            val theme = Theme.getByName(themeName)
            val outline = Outline.create(ctx, theme.outlineName)

            val dim: Float = (DISPLAY_ITEM_FACTOR * (stroke.dim + outline.dim))

            with (mFirst) {
                circleRadius = dim
                setCircleColor(ctx.getColor(pal.darkId))
                setCircleBorderWidth(outline.dim)
            }

            with (mSecond) {
                circleRadius = dim
                setCircleColor(ctx.getColor(pal.id))
                setCircleBorderWidth(outline.dim)
            }

            with (mThird) {
                circleRadius = dim
                setCircleColor(ctx.getColor(pal.lightId))
                setCircleBorderWidth(outline.dim)
            }
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val color = mOptions[position]
            Log.d(TAG, "Color: $color onClick() position: $position sharedPrefString: $mPrefString")
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = ConfigData.prefs(view.context).edit()
                editor.putString(mPrefString, color.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK) //triggers config activity
            }
            activity.finish()
        }
    }

    companion object {
        val DISPLAY_ITEM_FACTOR = 1F
        private val TAG = this::class.java.simpleName
    }
}