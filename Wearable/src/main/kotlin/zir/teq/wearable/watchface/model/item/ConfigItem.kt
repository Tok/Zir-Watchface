package zir.teq.wearable.watchface.model.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.holder.ColorPickerViewHolder
import zir.teq.wearable.watchface.config.select.holder.StrokePickerViewHolder
import zir.teq.wearable.watchface.config.select.holder.ThemePickerViewHolder
import zir.teq.wearable.watchface.config.select.holder.ZirPickerViewHolder

open class ConfigItem internal constructor(val name: String,
                                           val iconId: Int,
                                           val pref: String) {
    companion object {
        fun createViewHolder(viewGroup: ViewGroup, viewType: Int): ZirPickerViewHolder? {
            return when (viewType) {
                TYPE_COLOR_CONFIG -> ColorPickerViewHolder(createView(viewGroup, R.layout.config_list_color_item))
                TYPE_STROKE_CONFIG -> StrokePickerViewHolder(createView(viewGroup, R.layout.config_list_stroke_item))
                TYPE_THEME_CONFIG -> ThemePickerViewHolder(createView(viewGroup, R.layout.config_list_theme_item))
                else -> throw IllegalArgumentException("Unknown type $viewType for viewGroup: $viewGroup")
            }
        }
        private fun createView(viewGroup: ViewGroup, resource: Int): View {
            return LayoutInflater.from(viewGroup.context).inflate(resource, viewGroup, false)
        }
        val TYPE_COLOR_CONFIG = 2
        val TYPE_STROKE_CONFIG = 3
        val TYPE_THEME_CONFIG = 4
    }
}