package zir.teq.wearable.watchface.util

import android.support.v7.widget.RecyclerView
import android.support.wearable.view.WearableRecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.holder.BooleanPairViewHolder
import zir.teq.wearable.watchface.config.holder.BooleanViewHolder
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.select.*
import zir.teq.wearable.watchface.config.select.config.Type
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder

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
            Type.THEME.code -> ThemeViewHolder(createView(group, R.layout.config_list_item_theme))
            Type.PALETTE.code -> PaletteViewHolder(createView(group, R.layout.config_list_item_palette))
            Type.BACKGROUND.code -> BackgroundViewHolder(createView(group, R.layout.config_list_item_background))
            Type.WAVE.code -> WaveViewHolder(createView(group, R.layout.config_list_item_wave))
            Type.STROKE.code -> StrokeViewHolder(createView(group, R.layout.config_list_item_stroke))
            Type.OUTLINE.code -> OutlineViewHolder(createView(group, R.layout.config_list_item_outline))
            Type.GROWTH.code -> GrowthViewHolder(createView(group, R.layout.config_list_item_growth))
            Type.STACK.code -> StackViewHolder(createView(group, R.layout.config_list_item_stack))
            Type.ALPHA.code -> AlphaViewHolder(createView(group, R.layout.config_list_item_alpha))
            Type.DIM.code -> DimViewHolder(createView(group, R.layout.config_list_item_dim))
            else -> {
                val ci = Type.valueOf(viewType) ?: throw IllegalArgumentException("Unknown type $viewType for group: $group")
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

    private fun createDoubleCheckViewHolder(viewGroup: ViewGroup, type: Type): BooleanPairViewHolder {
        val ctx = viewGroup.context
        val view = LayoutInflater.from(ctx).inflate(R.layout.list_item_double_check, viewGroup, false)
        val holder = BooleanPairViewHolder(view)
        val activePref = ctx.resources.getString(type.prefId)
        val ambientPref = ctx.resources.getString(type.secondaryPrefId ?: type.prefId)
        val name = ctx.resources.getString(type.nameId)
        holder.updateBoxes(activePref, ambientPref, name)
        return holder
    }

    private fun createCheckboxViewHolder(viewGroup: ViewGroup, type: Type): RecSelectionViewHolder {
        val ctx = viewGroup.context
        with(ctx.resources) {
            val view = LayoutInflater.from(ctx).inflate(R.layout.list_item_checkbox, viewGroup, false)
            val holder = BooleanViewHolder(view)
            holder.setSharedPrefString(getString(type.prefId))
            holder.setName(getString(type.nameId))
            return holder
        }
    }
}
