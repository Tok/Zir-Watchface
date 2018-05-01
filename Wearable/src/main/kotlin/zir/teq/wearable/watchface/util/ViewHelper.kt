package zir.teq.wearable.watchface.util

import android.support.v7.widget.RecyclerView
import android.support.wear.widget.WearableRecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.general.holder.BooleanViewHolder
import zir.teq.wearable.watchface.config.general.holder.RecSelectionViewHolder
import zir.teq.wearable.watchface.model.RecAdapter

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

    fun createView(viewGroup: ViewGroup, resource: Int) =
            LayoutInflater.from(viewGroup.context).inflate(resource, viewGroup, false)

    fun createCheckboxViewHolder(viewGroup: ViewGroup, type: Item): RecSelectionViewHolder {
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
