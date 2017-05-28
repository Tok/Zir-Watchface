package zir.teq.wearable.watchface.util

import android.support.v7.widget.RecyclerView
import android.support.wearable.view.WearableRecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.holder.*
import zir.teq.wearable.watchface.model.item.ConfigItem

object ViewHelper {
    fun initView(view: WearableRecyclerView?,
                 ada: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
                 manager: RecyclerView.LayoutManager?): Unit {
        view!!.centerEdgeItems = true
        view.layoutManager = manager
        view.adapter = ada
        addCircularGestureToView(view)
    }

    private fun addCircularGestureToView(view: WearableRecyclerView) {
        val isGestureActive = false //TODO reactivate?
        if (isGestureActive) {
            view.setCircularScrollingGestureEnabled(true)
            view.setBezelWidth(0.5F)
            view.setScrollDegreesPerScreen(90F)
        }
    }

    fun createViewHolder(group: ViewGroup, viewType: Int): ZirPickerViewHolder? {
        return when (viewType) {
            ConfigItem.THEME.code -> ThemePickerViewHolder(createView(group, R.layout.config_list_theme_item))
            ConfigItem.COLORS.code -> ColorPickerViewHolder(createView(group, R.layout.config_list_color_item))
            ConfigItem.STROKE.code -> StrokePickerViewHolder(createView(group, R.layout.config_list_stroke_item))
            else -> {
                val ci = ConfigItem.valueOf(viewType) ?: throw IllegalArgumentException("Unknown type $viewType for group: $group")
                createCheckboxViewHolder(group, ci)
            }
        }
    }

    private fun createView(viewGroup: ViewGroup, resource: Int): View {
        return LayoutInflater.from(viewGroup.context).inflate(resource, viewGroup, false)
    }

    private fun createCheckboxViewHolder(viewGroup: ViewGroup, type: ConfigItem.Companion.Type): ZirPickerViewHolder {
        val resources = viewGroup.context.resources
        val pref = resources.getString(type.prefId)
        val name = resources.getString(type.nameId)
        return createViewHolder(viewGroup, pref, name, R.layout.list_item_checkbox)
    }

    private fun createViewHolder(viewGroup: ViewGroup, pref: String, name: String, layoutId: Int): ZirPickerViewHolder {
        val ctx = viewGroup.context
        val view = LayoutInflater.from(ctx).inflate(layoutId, viewGroup, false)
        val holder = BooleanPickerViewHolder(view)
        holder.setSharedPrefString(pref)
        holder.setName(name)
        return holder
    }
}