package zir.teq.wearable.watchface.model.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.holder.*

open class ConfigItem internal constructor(val name: String,
                                           val iconId: Int,
                                           val pref: String) {
    companion object {
        data class Type(val code: Int)
        val COLOR = Type(1)
        val STROKE = Type(2)
        val THEME = Type(3)
        val DRAW_TRIANGLES = Type(4)
        val DRAW_CIRCLES = Type(5)
        val DRAW_ACTIVE_HANDS = Type(6)
        val DRAW_POINTS = Type(7)
        val DRAW_TEXT = Type(8)

        fun createViewHolder(viewGroup: ViewGroup, viewType: Int): ZirPickerViewHolder? {
            return when (viewType) {
                COLOR.code -> ColorPickerViewHolder(createView(viewGroup, R.layout.config_list_color_item))
                STROKE.code -> StrokePickerViewHolder(createView(viewGroup, R.layout.config_list_stroke_item))
                THEME.code -> ThemePickerViewHolder(createView(viewGroup, R.layout.config_list_theme_item))
                DRAW_TRIANGLES.code -> createBooleanViewHolder(viewGroup, R.string.config_marker_draw_triangles_label)
                DRAW_CIRCLES.code -> createBooleanViewHolder(viewGroup, R.string.config_marker_draw_circles_label)
                DRAW_ACTIVE_HANDS.code -> createBooleanViewHolder(viewGroup, R.string.config_marker_draw_active_hands_label)
                DRAW_POINTS.code -> createBooleanViewHolder(viewGroup, R.string.config_marker_draw_points_label)
                DRAW_TEXT.code -> createBooleanViewHolder(viewGroup, R.string.config_marker_draw_text_label)
                else -> throw IllegalArgumentException("Unknown type $viewType for viewGroup: $viewGroup")
            }
        }

        private fun createView(viewGroup: ViewGroup, resource: Int): View {
            return LayoutInflater.from(viewGroup.context).inflate(resource, viewGroup, false)
        }

        private fun createBooleanViewHolder(viewGroup: ViewGroup, labelId: Int): ZirPickerViewHolder {
            val ctx = viewGroup.context
            val view = LayoutInflater.from(ctx).inflate(R.layout.checkbox_list_item, viewGroup, false)
            val holder = BooleanPickerViewHolder(view)
            val name = ctx.resources.getString(labelId)
            holder.setName(name)
            return holder
        }
    }
}