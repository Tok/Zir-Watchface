package zir.teq.wearable.watchface.config.select

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Outline
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.Theme
import java.util.*

class PaletteSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: ArrayList<Palette>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ColorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_palette, parent, false))

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
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

            val themeName = prefs.getString(ctx.getString(R.string.saved_theme), Theme.default.name)
            val theme = Theme.getByName(themeName)
            val outline = Outline.create(ctx, theme.outlineName)

            with (mFirst) {
                setCircleColor(ctx.getColor(pal.darkId))
                setCircleBorderWidth(outline.dim)
            }

            with (mSecond) {
                setCircleColor(ctx.getColor(pal.id))
                setCircleBorderWidth(outline.dim)
            }

            with (mThird) {
                setCircleColor(ctx.getColor(pal.lightId))
                setCircleBorderWidth(outline.dim)
            }
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val color = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = ConfigData.prefs(view.context).edit()
                editor.putString(mPrefString, color.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}