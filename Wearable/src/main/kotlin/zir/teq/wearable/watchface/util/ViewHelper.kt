package zir.teq.wearable.watchface.util

import android.support.v7.widget.RecyclerView
import android.support.wear.widget.WearableRecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.holder.BooleanViewHolder
import zir.teq.wearable.watchface.config.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.config.select.config.Type
import zir.teq.wearable.watchface.config.select.main.color.BackgroundViewHolder
import zir.teq.wearable.watchface.config.select.main.color.PaletteViewHolder
import zir.teq.wearable.watchface.config.select.main.component.ComponentViewHolder
import zir.teq.wearable.watchface.config.select.main.style.*
import zir.teq.wearable.watchface.config.select.main.wave.WaveViewHolder
import zir.teq.wearable.watchface.model.RecAdapter
import zir.teq.wearable.watchface.model.RecHolder

object ViewHelper {
    fun initView(view: WearableRecyclerView,
                 ada: RecAdapter,
                 manager: RecyclerView.LayoutManager): Unit {
        view.setBackgroundColor(Zir.color(R.color.background))
        view.setEdgeItemsCenteringEnabled(true)
        view.layoutManager = manager
        view.adapter = ada
        addCircularGestureToView(view)
    }

    private fun addCircularGestureToView(view: WearableRecyclerView) {
        val isGestureActive = false
        if (isGestureActive) {
            view.setCircularScrollingGestureEnabled(true);
            view.setBezelFraction(0.5F)
            view.setScrollDegreesPerScreen(90F)
        }
    }

    fun createViewHolder(group: ViewGroup, viewType: Int): RecHolder {
        val configItem = Type.valueOf(viewType)
        val view = createView(group, configItem.layoutId())
        return when (configItem) { //TODO move view holder instantiation to type?
            Type.COMPONENT -> ComponentViewHolder(view)
            Type.PALETTE -> PaletteViewHolder(view)
            Type.STYLE -> StyleViewHolder(view)
            Type.BACKGROUND -> BackgroundViewHolder(view)
            Type.WAVE -> WaveViewHolder(view)
            Type.STROKE -> StrokeViewHolder(view)
            Type.OUTLINE -> OutlineViewHolder(view)
            Type.GROWTH -> GrowthViewHolder(view)
            Type.STACK -> StackViewHolder(view)
            Type.ALPHA -> AlphaViewHolder(view)
            Type.DIM -> DimViewHolder(view)
            else -> createCheckboxViewHolder(group, configItem)
        }
    }

    private fun createView(viewGroup: ViewGroup, resource: Int) =
            LayoutInflater.from(viewGroup.context).inflate(resource, viewGroup, false)

    private fun createCheckboxViewHolder(viewGroup: ViewGroup, type: Type): RecSelectionViewHolder {
        val ctx = viewGroup.context
        with(ctx.resources) {
            val view = LayoutInflater.from(ctx).inflate(R.layout.list_item_checkbox, viewGroup, false)
            return BooleanViewHolder(view).apply {
                setSharedPrefString(getString(type.prefId))
                setName(getString(type.nameId))
            }
        }
    }
}
