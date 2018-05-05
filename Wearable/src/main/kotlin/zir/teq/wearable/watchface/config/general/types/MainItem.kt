package zir.teq.wearable.watchface.config.general.types

import android.app.Activity
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.select.activity.main.*


class MainItem(prefId: Int, nameId: Int, iconId: Int, activity: Class<out Activity>) :
        Item(prefId, nameId, iconId, activity) {
    companion object {
        val MAIN_SETUP = MainItem(R.string.saved_setup, R.string.label_setup, R.drawable.main_icon_setup, MainSetupActivity::class.java)
        val MAIN_COMPONENTS = MainItem(R.string.saved_theme, R.string.label_components, R.drawable.main_icon_components, MainComponentActivity::class.java)
        val MAIN_COLORS = MainItem(R.string.saved_palette, R.string.label_colors, R.drawable.main_icon_color, MainColorActivity::class.java)
        val MAIN_WAVE = MainItem(R.string.saved_wave_props, R.string.label_wave_props, R.drawable.main_icon_wave, MainWaveActivity::class.java)
        val MAIN_STYLE = MainItem(R.string.saved_style, R.string.label_style, R.drawable.main_icon_style, MainStyleActivity::class.java)
        val MAIN_FAST_UPDATE = CheckboxItem(R.string.saved_fast_update, R.string.label_fast_update)
        val MAIN_IS_ELASTIC = CheckboxItem(R.string.saved_is_elastic, R.string.label_is_elastic)
        val all = listOf(MAIN_SETUP, MAIN_COMPONENTS, MAIN_COLORS, MAIN_WAVE, MAIN_STYLE,
                MAIN_FAST_UPDATE, MAIN_IS_ELASTIC)
    }
}
