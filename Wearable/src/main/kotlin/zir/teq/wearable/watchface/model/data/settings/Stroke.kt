package zir.teq.wearable.watchface.model.data.settings

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

data class Stroke(val name: String, val dim: Float) {
    companion object {
        data class Type(val name: String, val dimId: Int)
        val HAIR = zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.Type("Hair", R.dimen.dim_hair)
        val THIN = zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.Type("Thin", R.dimen.dim_thin)
        val NORMAL = zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.Type("Normal", R.dimen.dim_normal)
        val BOLD = zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.Type("Bold", R.dimen.dim_bold)
        val MEGA = zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.Type("Mega", R.dimen.dim_mega)
        val GIGA = zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.Type("Giga", R.dimen.dim_giga)
        val ULTRA = zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.Type("Ultra", R.dimen.dim_ultra)
        val all = listOf(zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.HAIR, zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.THIN, zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.NORMAL, zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.BOLD, zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.MEGA, zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.GIGA, zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.ULTRA)
        private val defaultType = zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.NORMAL
        fun default() = zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.create(defaultType.name)
        fun options() = zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.all.map { zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.inst(it) }.toCollection(ArrayList<zir.teq.wearable.watchface.model.data.settings.Stroke>())
        fun create(typeName: String) = zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.inst(all.find { it.name.equals(typeName) } ?: defaultType)
        private fun inst(type: zir.teq.wearable.watchface.model.data.settings.Stroke.Companion.Type) = zir.teq.wearable.watchface.model.data.settings.Stroke(type.name, ConfigData.res.getDimension(type.dimId))
    }
}
