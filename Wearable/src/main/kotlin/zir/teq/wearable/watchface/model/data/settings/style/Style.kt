package zir.teq.wearable.watchface.model.data.settings.style

data class Style(val alpha: Alpha, val dim: Dim, val stack: Stack,
                 val stroke: Stroke, val growth: Growth, val outline: Outline) {
    fun growthFactor() = stroke.dim + outline.dim

    companion object {
        private val DEFAULT = Style(Alpha.default, Dim.default, Stack.default,
                Stroke.default(), Growth.default(), Outline.default())

        val default = DEFAULT
    }
}
