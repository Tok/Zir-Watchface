package zir.teq.wearable.watchface.model.data.settings.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

data class Stroke(val name: String, val dim: Float, val iconId: Int) {
    companion object {
        data class Type(val name: String, val dimId: Int, val iconId: Int)

        val HAIR = Type("Hair", R.dimen.dim_hair, R.drawable.icon_stroke_hair)
        val THIN = Type("Thin", R.dimen.dim_thin, R.drawable.icon_stroke_thin)
        val NORMAL = Type("Normal", R.dimen.dim_normal, R.drawable.icon_stroke_normal)
        val BOLD = Type("Bold", R.dimen.dim_bold, R.drawable.icon_stroke_bold)
        val MEGA = Type("Mega", R.dimen.dim_mega, R.drawable.icon_stroke_mega)
        val GIGA = Type("Giga", R.dimen.dim_giga, R.drawable.icon_stroke_giga)
        val ULTRA = Type("Ultra", R.dimen.dim_ultra, R.drawable.icon_stroke_ultra)
        val all = listOf(HAIR, THIN, NORMAL, BOLD, MEGA, GIGA, ULTRA)
        private val defaultType = NORMAL
        fun default() = create(defaultType.name)
        fun options() = all.map { inst(it) }.toCollection(ArrayList())
        fun create(typeName: String) = inst(all.find { it.name.equals(typeName) } ?: defaultType)
        private fun inst(type: Type) = Stroke(type.name, ConfigData.res.getDimension(type.dimId), type.iconId)
    }
}
