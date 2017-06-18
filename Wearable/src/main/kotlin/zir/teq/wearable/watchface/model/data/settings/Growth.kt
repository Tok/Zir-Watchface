package zir.teq.wearable.watchface.model.data.settings

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

data class Growth(val name: String, val dim: Float) {
    companion object {
        data class Type(val name: String, val dimId: Int)
        val OFF = zir.teq.wearable.watchface.model.data.settings.Growth.Companion.Type("Off", R.dimen.dim_off)
        val HAIR = zir.teq.wearable.watchface.model.data.settings.Growth.Companion.Type("Hair", R.dimen.dim_hair)
        val THIN = zir.teq.wearable.watchface.model.data.settings.Growth.Companion.Type("Thin", R.dimen.dim_thin)
        val NORMAL = zir.teq.wearable.watchface.model.data.settings.Growth.Companion.Type("Normal", R.dimen.dim_normal)
        val BOLD = zir.teq.wearable.watchface.model.data.settings.Growth.Companion.Type("Bold", R.dimen.dim_bold)
        val BOLDER = zir.teq.wearable.watchface.model.data.settings.Growth.Companion.Type("Bolder", R.dimen.dim_bolder)
        val MEGA = zir.teq.wearable.watchface.model.data.settings.Growth.Companion.Type("Mega", R.dimen.dim_mega)
        val all = listOf(zir.teq.wearable.watchface.model.data.settings.Growth.Companion.OFF, zir.teq.wearable.watchface.model.data.settings.Growth.Companion.HAIR, zir.teq.wearable.watchface.model.data.settings.Growth.Companion.THIN, zir.teq.wearable.watchface.model.data.settings.Growth.Companion.NORMAL, zir.teq.wearable.watchface.model.data.settings.Growth.Companion.BOLD, zir.teq.wearable.watchface.model.data.settings.Growth.Companion.BOLDER, zir.teq.wearable.watchface.model.data.settings.Growth.Companion.MEGA)
        private val defaultType = zir.teq.wearable.watchface.model.data.settings.Growth.Companion.BOLDER
        fun default() = zir.teq.wearable.watchface.model.data.settings.Growth.Companion.create(defaultType.name)
        fun options() = zir.teq.wearable.watchface.model.data.settings.Growth.Companion.all.map { zir.teq.wearable.watchface.model.data.settings.Growth.Companion.inst(it) }.toCollection(ArrayList<zir.teq.wearable.watchface.model.data.settings.Growth>())
        fun create(typeName: String) = zir.teq.wearable.watchface.model.data.settings.Growth.Companion.inst(all.find { it.name.equals(typeName) } ?: defaultType)
        private fun inst(type: zir.teq.wearable.watchface.model.data.settings.Growth.Companion.Type) = zir.teq.wearable.watchface.model.data.settings.Growth(type.name, ConfigData.res.getDimension(type.dimId))
    }
}
