package zir.teq.wearable.watchface.model.data.settings

import zir.teq.wearable.watchface.config.select.config.Type

class StyleConfigItem() : ColorConfigItem {
    override val configId: Int = Type.STYLE.code
}

data class Style(val name: String, val alpha: Alpha, val dim: Dim, val stack: Stack,
                 val stroke: Stroke, val growth: Growth, val outline: Outline) {
    fun growthFactor() = stroke.dim + outline.dim
    companion object {
        val DEFAULT = Style("Default", Alpha.default, Dim.default, Stack.default,
                Stroke.default(), Growth.default(), Outline.default())

        val default = DEFAULT
        private val ALL = listOf(DEFAULT)

        fun options() = ALL.toCollection(ArrayList())
        fun getByName(name: String): Style = ALL.find { it.name.equals(name) } ?: default
    }
}
