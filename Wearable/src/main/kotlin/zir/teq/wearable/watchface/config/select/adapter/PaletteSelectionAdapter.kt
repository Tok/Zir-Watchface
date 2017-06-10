package zir.teq.wearable.watchface.config.select.adapter

import android.support.v7.widget.RecyclerView
import zir.teq.wearable.watchface.model.data.Palette

class PaletteSelectionAdapter(
        private val mPrefString: String?,
        private val mOptions: java.util.ArrayList<Palette>) : android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int)
            = ColorViewHolder(android.view.LayoutInflater.from(parent.context).inflate(zir.teq.wearable.watchface.R.layout.list_item_palette, parent, false))

    override fun onBindViewHolder(vh: android.support.v7.widget.RecyclerView.ViewHolder, position: Int) {
        val pal = mOptions[position]
        val colorViewHolder = vh as zir.teq.wearable.watchface.config.select.adapter.PaletteSelectionAdapter.ColorViewHolder
        colorViewHolder.bindPalette(pal)
    }

    override fun getItemCount(): Int {
        return mOptions.size
    }

    inner class ColorViewHolder(view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener {
        val mView = view as android.widget.LinearLayout
        val mFirst = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_palette_first_cirlce) as android.support.wearable.view.CircledImageView
        val mSecond = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_palette_second_circle) as android.support.wearable.view.CircledImageView
        val mThird = view.findViewById(zir.teq.wearable.watchface.R.id.list_item_palette_third_circle) as android.support.wearable.view.CircledImageView
        init {
            mView.setOnClickListener(this)
        }

        fun bindPalette(pal: zir.teq.wearable.watchface.model.data.Palette) {
            val ctx = mFirst.context
            val prefs = zir.teq.wearable.watchface.model.ConfigData.prefs(ctx)

            val themeName = prefs.getString(ctx.getString(zir.teq.wearable.watchface.R.string.saved_theme), zir.teq.wearable.watchface.model.data.Theme.Companion.default.name)
            val theme = zir.teq.wearable.watchface.model.data.Theme.Companion.getByName(themeName)
            val outline = zir.teq.wearable.watchface.model.data.Outline.Companion.create(ctx, theme.outlineName)

            with (mFirst) {
                setCircleColor(pal.dark(ctx))
                setCircleBorderWidth(outline.dim)
            }

            with (mSecond) {
                setCircleColor(pal.half(ctx))
                setCircleBorderWidth(outline.dim)
            }

            with (mThird) {
                setCircleColor(pal.light(ctx))
                setCircleBorderWidth(outline.dim)
            }
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            val color = mOptions[position]
            val activity = view.context as android.app.Activity
            if (mPrefString != null && !mPrefString.isEmpty()) {
                val editor = zir.teq.wearable.watchface.model.ConfigData.prefs(view.context).edit()
                editor.putString(mPrefString, color.name)
                editor.commit()
                activity.setResult(android.app.Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}