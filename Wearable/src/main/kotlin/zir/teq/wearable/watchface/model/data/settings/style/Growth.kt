package zir.teq.wearable.watchface.model.data.settings.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

data class Growth(val name: String, val dim: Float, val iconId: Int) {
    companion object {
        data class Type(val name: String, val dimId: Int, val iconId: Int)
        val OFF = Type("Off", R.dimen.dim_off, R.drawable.icon_growth_off)
        val HAIR = Type("Hair", R.dimen.dim_hair, R.drawable.icon_growth_hair)
        val THIN = Type("Thin", R.dimen.dim_thin, R.drawable.icon_growth_thin)
        val NORMAL = Type("Normal", R.dimen.dim_normal, R.drawable.icon_growth_normal)
        val BOLD = Type("Bold", R.dimen.dim_bold, R.drawable.icon_growth_bold)
        val BOLDER = Type("Bolder", R.dimen.dim_bolder, R.drawable.icon_growth_bolder)
        val MEGA = Type("Mega", R.dimen.dim_mega, R.drawable.icon_growth_mega)
        val all = listOf(OFF, HAIR, THIN, NORMAL, BOLD, BOLDER, MEGA)
        private val defaultType = BOLDER
        fun default() = create(defaultType.name)
        fun options() = all.map { inst(it) }.toCollection(ArrayList())
        fun create(typeName: String) = inst(all.find { it.name.equals(typeName) } ?: defaultType)
        private fun inst(type: Type) = Growth(type.name, ConfigData.res.getDimension(type.dimId), type.iconId)
    }
}
