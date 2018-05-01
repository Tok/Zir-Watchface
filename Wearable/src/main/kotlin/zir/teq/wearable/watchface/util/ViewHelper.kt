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
import zir.teq.wearable.watchface.config.select.holder.color.BackgroundViewHolder
import zir.teq.wearable.watchface.config.select.holder.color.PaletteViewHolder
import zir.teq.wearable.watchface.config.select.holder.component.ComponentViewHolder
import zir.teq.wearable.watchface.config.select.activity.main.MainStyleActivity
import zir.teq.wearable.watchface.config.select.holder.PropsViewHolder
import zir.teq.wearable.watchface.config.select.holder.WavePropsViewHolder
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
        val configItem = Item.valueOf(viewType)
        val view = createView(group, configItem.layoutId())
        return when (configItem) { //TODO move view holder instantiation to type?
            Item.COMPONENT -> ComponentViewHolder(view)
            Item.PALETTE -> PaletteViewHolder(view)
            Item.BACKGROUND -> BackgroundViewHolder(view)
            Item.WAVE_PROPS -> WavePropsViewHolder(view)
            Item.WAVE_SPECTRUM -> RecSelectionViewHolder(view)
            Item.WAVE_VELOCITY -> RecSelectionViewHolder(view)
            Item.WAVE_FREQUENCY -> RecSelectionViewHolder(view)
            Item.WAVE_INTENSITY -> RecSelectionViewHolder(view)
            Item.WAVE_RESO -> RecSelectionViewHolder(view)
            Item.WAVE_AMB_RESO -> RecSelectionViewHolder(view)
            Item.STYLE -> PropsViewHolder(view, MainStyleActivity.EXTRA, configItem.code)
        //Item.STROKE -> PropsViewHolder(view, StrokeActivity.EXTRA, configItem.code)
        //Item.OUTLINE -> PropsViewHolder(view, OutlineActivity.EXTRA, configItem.code)
        //Item.GROWTH -> PropsViewHolder(view, GrowthActivity.EXTRA, configItem.code)
        //Item.STACK -> PropsViewHolder(view, StackActivity.EXTRA, configItem.code)
        //Item.ALPHA -> PropsViewHolder(view, StyleAlphaActivity.EXTRA, configItem.code)
        //Item.DIM -> PropsViewHolder(view, StyleDimActivity.EXTRA, configItem.code)
            else -> createCheckboxViewHolder(group, configItem)
        }
    }

    private fun createView(viewGroup: ViewGroup, resource: Int) =
            LayoutInflater.from(viewGroup.context).inflate(resource, viewGroup, false)

    private fun createCheckboxViewHolder(viewGroup: ViewGroup, type: Item): RecSelectionViewHolder {
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
