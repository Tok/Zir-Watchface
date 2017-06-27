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
            view.isCircularScrollingGestureEnabled = true
            view.bezelWidth = 0.5F
            view.scrollDegreesPerScreen = 90F
        }
    }

    fun createViewHolder(group: ViewGroup, viewType: Int): RecHolder {
        val configItem = Type.valueOf(viewType)
        val maybeLayoutId = configItem.getLayoutId()
        if (maybeLayoutId != null) {
            val view = createView(group, maybeLayoutId)
            return when (configItem) { //TODO move view holder instantiation to type?
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
        } else return selectViewHolder(group, configItem)
    }

    private fun createView(viewGroup: ViewGroup, resource: Int): View {
        return LayoutInflater.from(viewGroup.context).inflate(resource, viewGroup, false)
    }

    private fun selectViewHolder(group: ViewGroup, type: Type): RecHolder =
            if (type.isPair()) createDoubleCheckViewHolder(group, type)
            else createCheckboxViewHolder(group, type)

    private fun createDoubleCheckViewHolder(viewGroup: ViewGroup, type: Type): BooleanPairViewHolder {
        val ctx = viewGroup.context
        val activePref = ctx.resources.getString(type.prefId)
        val ambientPref = ctx.resources.getString(type.secondaryPrefId ?: type.prefId)
        val name = ctx.resources.getString(type.nameId)
        val view = LayoutInflater.from(ctx).inflate(R.layout.list_item_double_check, viewGroup, false)
        return BooleanPairViewHolder(view).apply { updateBoxes(activePref, ambientPref, name) }
    }

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
