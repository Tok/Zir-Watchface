package zir.teq.wearable.watchface.model.data.settings

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

data class Outline(val name: String, val dim: Float) {
    companion object {
        data class Type(val name: String, val dimId: Int)
        val OFF = zir.teq.wearable.watchface.model.data.settings.Outline.Companion.Type("Off", R.dimen.dim_off)
        val HAIR = zir.teq.wearable.watchface.model.data.settings.Outline.Companion.Type("Hair", R.dimen.dim_hair)
        val THIN = zir.teq.wearable.watchface.model.data.settings.Outline.Companion.Type("Thin", R.dimen.dim_thin)
        val NORMAL = zir.teq.wearable.watchface.model.data.settings.Outline.Companion.Type("Normal", R.dimen.dim_normal)
        val BOLD = zir.teq.wearable.watchface.model.data.settings.Outline.Companion.Type("Bold", R.dimen.dim_bold)
        val BOLDER = zir.teq.wearable.watchface.model.data.settings.Outline.Companion.Type("Bolder", R.dimen.dim_bolder)
        val MEGA = zir.teq.wearable.watchface.model.data.settings.Outline.Companion.Type("Mega", R.dimen.dim_mega)
        val all = listOf(zir.teq.wearable.watchface.model.data.settings.Outline.Companion.OFF, zir.teq.wearable.watchface.model.data.settings.Outline.Companion.HAIR, zir.teq.wearable.watchface.model.data.settings.Outline.Companion.THIN, zir.teq.wearable.watchface.model.data.settings.Outline.Companion.NORMAL, zir.teq.wearable.watchface.model.data.settings.Outline.Companion.BOLD, zir.teq.wearable.watchface.model.data.settings.Outline.Companion.BOLDER, zir.teq.wearable.watchface.model.data.settings.Outline.Companion.MEGA)
        val defaultType = zir.teq.wearable.watchface.model.data.settings.Outline.Companion.BOLD
        fun default() = zir.teq.wearable.watchface.model.data.settings.Outline.Companion.create(defaultType.name)
        fun options() = zir.teq.wearable.watchface.model.data.settings.Outline.Companion.all.map { zir.teq.wearable.watchface.model.data.settings.Outline.Companion.inst(it) }.toCollection(ArrayList<zir.teq.wearable.watchface.model.data.settings.Outline>())
        fun create(typeName: String) = zir.teq.wearable.watchface.model.data.settings.Outline.Companion.inst(all.find { it.name.equals(typeName) } ?: defaultType)
        private fun inst(type: zir.teq.wearable.watchface.model.data.settings.Outline.Companion.Type) = zir.teq.wearable.watchface.model.data.settings.Outline(type.name, ConfigData.res.getDimension(type.dimId))
    }
}
