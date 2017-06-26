package zir.teq.wearable.watchface.util

import android.support.v7.widget.RecyclerView
import android.support.wearable.view.WearableRecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.holder.*
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder
import zir.teq.wearable.watchface.model.item.ConfigItem

object ViewHelper {
    fun initView(view: WearableRecyclerView,
                 ada: RecAdapter,
                 manager: RecyclerView.LayoutManager?): Unit {
        view.setBackgroundColor(R.color.background)
        init(view, ada, manager)
    }

    private fun init(view: WearableRecyclerView,
                     ada: RecAdapter,
                     manager: RecyclerView.LayoutManager?) {
        view.centerEdgeItems = true
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

    fun createViewHolder(group: ViewGroup, viewType: Int): RecHolder {
        return when (viewType) {
            ConfigItem.THEME.code -> ThemePickerViewHolder(createView(group, R.layout.config_list_item_theme))
            ConfigItem.PALETTE.code -> PalettePickerViewHolder(createView(group, R.layout.config_list_item_palette))
            ConfigItem.BACKGROUND.code -> BackgroundPickerViewHolder(createView(group, R.layout.config_list_item_background))
            ConfigItem.WAVE.code -> WavePickerViewHolder(createView(group, R.layout.config_list_item_wave))
            ConfigItem.STROKE.code -> StrokePickerViewHolder(createView(group, R.layout.config_list_item_stroke))
            ConfigItem.OUTLINE.code -> OutlinePickerViewHolder(createView(group, R.layout.config_list_item_outline))
            ConfigItem.GROWTH.code -> GrowthPickerViewHolder(createView(group, R.layout.config_list_item_growth))
            ConfigItem.STACK.code -> StackPickerViewHolder(createView(group, R.layout.config_list_item_stack))
            ConfigItem.ALPHA.code -> AlphaPickerViewHolder(createView(group, R.layout.config_list_item_alpha))
            ConfigItem.DIM.code -> DimPickerViewHolder(createView(group, R.layout.config_list_item_dim))
            else -> {
                val ci = ConfigItem.valueOf(viewType) ?: throw IllegalArgumentException("Unknown type $viewType for group: $group")
                if (ci.isPair()) {
                    createDoubleCheckViewHolder(group, ci)
                } else {
                    createCheckboxViewHolder(group, ci)
                }
            }
        }
    }

    private fun createView(viewGroup: ViewGroup, resource: Int): View {
        return LayoutInflater.from(viewGroup.context).inflate(resource, viewGroup, false)
    }

    private fun createDoubleCheckViewHolder(viewGroup: ViewGroup, type: ConfigItem.Companion.Type): BooleanPairViewHolder {
        val ctx = viewGroup.context
        val view = LayoutInflater.from(ctx).inflate(R.layout.list_item_double_check, viewGroup, false)
        val holder = BooleanPairViewHolder(view)
        val activePref = ctx.resources.getString(type.prefId)
        val ambientPref = ctx.resources.getString(type.secondaryPrefId ?: type.prefId)
        val name = ctx.resources.getString(type.nameId)
        holder.updateBoxes(activePref, ambientPref, name)
        return holder
    }

    private fun createCheckboxViewHolder(viewGroup: ViewGroup, type: ConfigItem.Companion.Type): ZirPickerViewHolder {
        val ctx = viewGroup.context
        with(ctx.resources) {
            val view = LayoutInflater.from(ctx).inflate(R.layout.list_item_checkbox, viewGroup, false)
            val holder = BooleanPickerViewHolder(view)
            holder.setSharedPrefString(getString(type.prefId))
            holder.setName(getString(type.nameId))
            return holder
        }
    }
}