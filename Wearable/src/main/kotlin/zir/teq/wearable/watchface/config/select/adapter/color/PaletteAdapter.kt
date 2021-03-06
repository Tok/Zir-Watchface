package zir.teq.wearable.watchface.config.select.adapter.color

import android.app.Activity
import android.support.wear.widget.CircularProgressLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.general.types.ColorItem
import zir.teq.wearable.watchface.config.select.holder.color.BackgroundViewHolder
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.setting.color.BackgroundConfigItem
import zir.teq.wearable.watchface.model.setting.color.ColorConfigItem
import zir.teq.wearable.watchface.model.setting.color.Palette
import zir.teq.wearable.watchface.model.setting.color.PaletteConfigItem
import zir.teq.wearable.watchface.util.ViewHelper


class PaletteAdapter(private val pref: String, private val options: List<ColorConfigItem>) : RecAdapter() {
    override fun getItemViewType(position: Int) = options[position].viewType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        if (viewType == ColorItem.COLOR_BACKGROUND.viewType) {
            return BackgroundViewHolder(ViewHelper.createView(parent, R.layout.list_item_main))
        }
        return ColorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_palette, parent, false))
    }

    override fun onBindViewHolder(vh: RecHolder, position: Int) {
        val item = options[position]
        when (item) {
            is Palette -> (vh as ColorViewHolder).bindPalette(item)
            is BackgroundConfigItem -> {
                with(vh as RecSelectionViewHolder) {
                    setName(Zir.string(R.string.label_background))
                    setSharedPrefString(Zir.string(R.string.saved_background))
                    val bgTintId = null //TODO implement
                    bindIcon(R.drawable.icon_background, bgTintId)
                    setActivity(ColorItem.COLOR_BACKGROUND.activity)
                }
            }
        }
    }

    override fun getItemCount() = options.size
    inner class ColorViewHolder(view: View) : RecHolder(view), View.OnClickListener {
        val mView = view as LinearLayout
        val mFirst: CircularProgressLayout = view.findViewById(R.id.list_item_palette_first_cirlce)
        val mSecond: CircularProgressLayout = view.findViewById(R.id.list_item_palette_second_circle)
        val mThird: CircularProgressLayout = view.findViewById(R.id.list_item_palette_third_circle)
        val mLayouts = listOf(mFirst, mSecond, mThird)

        init {
            mView.setOnClickListener(this)
        }

        fun bindPalette(pal: PaletteConfigItem) {
            mLayouts.forEach { layout ->
                layout.foreground = mView.context.getDrawable(R.drawable.icon_dummy)
                layout.strokeWidth = 1F
            }
            val palette = pal as Palette
            mFirst.setBackgroundColor(palette.dark())
            mSecond.setBackgroundColor(palette.half())
            mThird.setBackgroundColor(palette.light())
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val activity = view.context as Activity
            val item = options[position]
            if (item is Palette) {
                Palette.save(item)
                activity.setResult(Activity.RESULT_OK)
            }
            activity.finish()
        }
    }
}
