package zir.teq.wearable.watchface.util

import android.support.v7.widget.RecyclerView
import android.support.wearable.view.CircledImageView
import android.support.wearable.view.WearableRecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.adapter.StrokeSelectionAdapter
import zir.teq.wearable.watchface.config.select.holder.*
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.data.Outline
import zir.teq.wearable.watchface.model.data.Palette
import zir.teq.wearable.watchface.model.data.Stroke
import zir.teq.wearable.watchface.model.data.Theme
import zir.teq.wearable.watchface.model.item.ConfigItem

object ViewHelper {
    fun initMainConfigView(view: WearableRecyclerView?,
                           ada: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
                           manager: RecyclerView.LayoutManager?): Unit {
        view!!.setBackgroundColor(R.color.background)
        init(view, ada, manager)
    }

    fun initView(view: WearableRecyclerView?,
                 ada: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
                 manager: RecyclerView.LayoutManager?): Unit {
        view!!.setBackgroundColor(R.color.background)
        init(view, ada, manager)
    }

    private fun init(view: WearableRecyclerView?,
                     ada: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
                     manager: RecyclerView.LayoutManager?) {
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

    fun createViewHolder(group: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ConfigItem.THEME.code -> ThemePickerViewHolder(createView(group, R.layout.config_list_item_theme))
            ConfigItem.PALETTE.code -> PalettePickerViewHolder(createView(group, R.layout.config_list_item_palette))
            ConfigItem.BACKGROUND.code -> BackgroundPickerViewHolder(createView(group, R.layout.config_list_item_background))
            ConfigItem.STROKE.code -> StrokePickerViewHolder(createView(group, R.layout.config_list_item_stroke))
            ConfigItem.OUTLINE.code -> OutlinePickerViewHolder(createView(group, R.layout.config_list_item_outline))
            ConfigItem.GROWTH.code -> GrowthPickerViewHolder(createView(group, R.layout.config_list_item_growth))
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

    fun bindCircleColor(view: CircledImageView) {
        val ctx = view.context
        val palName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_palette), Palette.default.name)
        val pal = Palette.getByName(palName)
        val color = pal.half(ctx)
        view.setCircleColor(color)
    }

    fun bindCircleRadius(view: CircledImageView) {
        val ctx = view.context
        val strokeName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_stroke), Stroke.default.name)
        val stroke = Stroke.create(ctx, strokeName)

        //outline is added to the radius
        val themeName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_theme), Theme.default.name)
        val theme = Theme.getByName(themeName)
        val outline = Outline.create(ctx, theme.outlineName)
        view.circleRadius = (stroke.dim * StrokeSelectionAdapter.DISPLAY_ITEM_FACTOR) + outline.dim
    }

    fun bindCircleBorderWidth(view: CircledImageView) {
        val ctx = view.context
        val themeName = ConfigData.prefs(ctx).getString(ctx.getString(R.string.saved_theme), Theme.default.name)
        val theme = Theme.getByName(themeName)
        val outline = Outline.create(ctx, theme.outlineName)
        view.setCircleBorderWidth(outline.dim)
    }
}