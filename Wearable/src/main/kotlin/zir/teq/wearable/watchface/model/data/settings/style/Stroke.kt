package zir.teq.wearable.watchface.model.data.settings.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

data class Stroke(val name: String, val dim: Float, val iconId: Int) {
    companion object {
        data class Type(val name: String, val dimId: Int, val iconId: Int)

        val OFF = Type("Off", R.dimen.dim_off, R.drawable.theme_stroke_0)
        val _1 = Type("1 Hair", R.dimen.dim_1, R.drawable.theme_stroke_1)
        val _2 = Type("2 Thin", R.dimen.dim_2, R.drawable.theme_stroke_2)
        val _3 = Type("3 Normal", R.dimen.dim_3, R.drawable.theme_stroke_3)
        val _5 = Type("5", R.dimen.dim_5, R.drawable.theme_stroke_5)
        val _8 = Type("8 Thick", R.dimen.dim_8, R.drawable.theme_stroke_8)
        val _10 = Type("10", R.dimen.dim_10, R.drawable.theme_stroke_10)
        val _13 = Type("13 Bold", R.dimen.dim_13, R.drawable.theme_stroke_13)
        val _16 = Type("16", R.dimen.dim_16, R.drawable.theme_stroke_16)
        val _21 = Type("21 Mega", R.dimen.dim_21, R.drawable.theme_stroke_21)
        val _26 = Type("26", R.dimen.dim_26, R.drawable.theme_stroke_26)
        val _34 = Type("34 Giga", R.dimen.dim_34, R.drawable.theme_stroke_34)

        val ALL = listOf(OFF, _1, _2, _3, _5, _8, _10, _13, _16, _21, _26, _34)

        private val defaultType = _3
        fun default() = create(defaultType.name)
        fun options() = ALL.map { inst(it) }.toCollection(ArrayList())
        fun create(typeName: String) = inst(ALL.find { it.name.equals(typeName) } ?: defaultType)
        private fun inst(type: Type) = Stroke(type.name, ConfigData.res.getDimension(type.dimId), type.iconId)
    }
}
