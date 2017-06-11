package config.select.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Palette

class PaletteSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: java.util.ArrayList<Palette>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ColorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_palette, parent, false))

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val pal = mOptions[position]
        val colorViewHolder = vh as ColorViewHolder
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
            val oDim = Math.max(1F, ConfigData.outline.dim)
            with (mFirst) {
                setCircleColor(pal.dark())
                setCircleBorderWidth(oDim)
            }
            with (mSecond) {
                setCircleColor(pal.half())
                setCircleBorderWidth(oDim)
            }
            with (mThird) {
                setCircleColor(pal.light())
                setCircleBorderWidth(oDim)
            }
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val palette = mOptions[position]
            val activity = view.context as Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                ConfigData.palette = palette
                val editor = ConfigData.prefs.edit()
                editor.putString(mPrefString, palette.name)
                editor.commit()
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}