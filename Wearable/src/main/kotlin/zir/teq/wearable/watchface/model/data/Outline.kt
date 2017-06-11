package zir.teq.wearable.watchface.model.data

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

data class Outline(val name: String, val dim: Float) {
    companion object {
        data class Type(val name: String, val dimId: Int)
        val OFF = Type("Off", R.dimen.dim_off)
        val HAIR = Type("Hair", R.dimen.dim_hair)
        val THIN = Type("Thin", R.dimen.dim_thin)
        val NORMAL = Type("Normal", R.dimen.dim_normal)
        val BOLD = Type("Bold", R.dimen.dim_bold)
        val BOLDER = Type("Bolder", R.dimen.dim_bolder)
        val MEGA = Type("Mega", R.dimen.dim_mega)
        val all = listOf(OFF, HAIR, THIN, NORMAL, BOLD, BOLDER, MEGA)
        val defaultType = BOLD
        fun default() = create(defaultType.name)
        fun options() = all.map { inst(it) }.toCollection(ArrayList<Outline>())
        fun create(typeName: String) = inst(all.find { it.name.equals(typeName) } ?: defaultType)
        private fun inst(type: Type) = Outline(type.name, ConfigData.res.getDimension(type.dimId))
    }
}
