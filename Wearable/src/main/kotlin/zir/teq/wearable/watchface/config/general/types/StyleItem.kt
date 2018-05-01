package zir.teq.wearable.watchface.config.general.types

import android.app.Activity
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.config.general.Item
import zir.teq.wearable.watchface.config.select.activity.style.*


class StyleItem(prefId: Int, nameId: Int, iconId: Int, activity: Class<out Activity>) :
        Item(prefId, nameId, iconId, activity) {
    companion object {
        val STYLE_ALPHA = StyleItem(R.string.saved_style_alpha, R.string.label_alpha, R.drawable.style_icon_alpha, StyleAlphaActivity::class.java)
        val STYLE_DIM = StyleItem(R.string.saved_style_dim, R.string.label_dim, R.drawable.style_icon_dim, StyleDimActivity::class.java)
        val STYLE_STACK = StyleItem(R.string.saved_style_stack, R.string.label_stack, R.drawable.style_icon_stack, StyleStackActivity::class.java)
        val STYLE_GROWTH = StyleItem(R.string.saved_style_growth, R.string.label_growth, R.drawable.style_icon_growth, StyleGrowthActivity::class.java)
        val STYLE_STROKE = StyleItem(R.string.saved_style_stroke, R.string.label_stroke, R.drawable.style_icon_stroke, StyleStrokeActivity::class.java)
        val STYLE_OUTLINE = StyleItem(R.string.saved_style_outline, R.string.label_outline, R.drawable.style_icon_outline, StyleOutlineActivity::class.java)
        val all = listOf(STYLE_ALPHA, STYLE_DIM, STYLE_STACK, STYLE_GROWTH, STYLE_STROKE, STYLE_OUTLINE)
    }
}
