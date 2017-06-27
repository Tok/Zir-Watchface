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
        val configItem = Type.valueOf(viewType)
        if (configItem.layoutId != null) {
            val view = createView(group, configItem.layoutId)
            return when (configItem) {
                Type.THEME -> ThemeViewHolder(view)
                Type.PALETTE -> PaletteViewHolder(view)
                Type.BACKGROUND -> BackgroundViewHolder(view)
                Type.WAVE -> WaveViewHolder(view)
                Type.STROKE -> StrokeViewHolder(view)
                Type.OUTLINE -> OutlineViewHolder(view)
                Type.GROWTH -> GrowthViewHolder(view)
                Type.STACK -> StackViewHolder(view)
                Type.ALPHA -> AlphaViewHolder(view)
                Type.DIM -> DimViewHolder(view)
                else -> throw IllegalArgumentException("Missing layout: $configItem.")
            }
        } else {
            return if (configItem.isPair()) {
                createDoubleCheckViewHolder(group, configItem)
            } else {
                createCheckboxViewHolder(group, configItem)
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
