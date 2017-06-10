package zir.teq.wearable.watchface.config.select.holder

import android.view.View
import android.widget.Button
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.select.*


class AlphaPickerViewHolder(view: View) : ZirPickerViewHolder(view) {
    init {
        mButton = view.findViewById(R.id.config_list_item_alpha) as Button
        view.setOnClickListener { super.handleClick(view, AlphaSelectionActivity.EXTRA) }
    }
}

class BackgroundPickerViewHolder(view: View) : ZirPickerViewHolder(view) {
    init {
        mButton = view.findViewById(R.id.config_list_item_background) as Button
        view.setOnClickListener { super.handleClick(view, BackgroundSelectionActivity.EXTRA) }
    }
}

class ColorPickerViewHolder(view: View) : ZirPickerViewHolder(view) {
    init {
        mButton = view.findViewById(R.id.config_list_item_color) as Button
        view.setOnClickListener { super.handleClick(view, PaletteSelectionActivity.EXTRA) }
    }
}

class DimPickerViewHolder(view: View) : ZirPickerViewHolder(view) {
    init {
        mButton = view.findViewById(R.id.config_list_item_dim) as Button
        view.setOnClickListener { super.handleClick(view, DimSelectionActivity.EXTRA) }
    }
}

class GrowthPickerViewHolder(view: View) : ZirPickerViewHolder(view) {
    init {
        mButton = view.findViewById(R.id.config_list_item_growth) as Button
        view.setOnClickListener { super.handleClick(view, GrowthSelectionActivity.EXTRA) }
    }
}

class OutlinePickerViewHolder(view: View) : ZirPickerViewHolder(view) {
    init {
        mButton = view.findViewById(R.id.config_list_item_outline) as Button
        view.setOnClickListener { super.handleClick(view, OutlineSelectionActivity.EXTRA) }
    }
}

class StrokePickerViewHolder(view: View) : ZirPickerViewHolder(view) {
    init {
        mButton = view.findViewById(R.id.config_list_item_stroke) as Button
        view.setOnClickListener { super.handleClick(view, StrokeSelectionActivity.EXTRA) }
    }
}

class ThemePickerViewHolder(view: View) : ZirPickerViewHolder(view) {
    init {
        mButton = view.findViewById(R.id.config_list_item_theme) as Button
        view.setOnClickListener { super.handleClick(view, ThemeSelectionActivity.EXTRA) }
    }
}