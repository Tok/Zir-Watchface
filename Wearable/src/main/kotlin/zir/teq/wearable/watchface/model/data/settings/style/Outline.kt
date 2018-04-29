package zir.teq.wearable.watchface.model.data.settings.style

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData

data class Outline(val name: String, val dim: Float, val iconId: Int) {
    val isOn = OFF.name != this.name

    companion object {
        data class Type(val name: String, val dimId: Int, val iconId: Int)

        val OFF = Type("Off", R.dimen.dim_off, R.drawable.theme_outline_0)
        val _1 = Type("1 Hair", R.dimen.dim_1, R.drawable.theme_outline_1)
        val _2 = Type("2 Thinner", R.dimen.dim_2, R.drawable.theme_outline_2)
        val _3 = Type("3 Thin", R.dimen.dim_3, R.drawable.theme_outline_3)
        val _5 = Type("5 Normal", R.dimen.dim_5, R.drawable.theme_outline_5)
        val _8 = Type("8 Thick", R.dimen.dim_8, R.drawable.theme_outline_8)
        val _10 = Type("10", R.dimen.dim_10, R.drawable.theme_outline_10)
        val _13 = Type("13 Bold", R.dimen.dim_13, R.drawable.theme_outline_13)
        val _16 = Type("16", R.dimen.dim_16, R.drawable.theme_outline_16)
        val _21 = Type("21 Mega", R.dimen.dim_21, R.drawable.theme_outline_21)

        val ALL = listOf(OFF, _1, _2, _3, _5, _8, _10, _13, _16, _21)

        val defaultType = _5
        fun default() = create(defaultType.name)
        fun options() = ALL.map { inst(it) }.toCollection(ArrayList())
        fun create(typeName: String): Outline = inst(ALL.find { it.name.equals(typeName) } ?: defaultType)
        private fun inst(type: Type) = Outline(type.name, ConfigData.res.getDimension(type.dimId), type.iconId)
    }
}
