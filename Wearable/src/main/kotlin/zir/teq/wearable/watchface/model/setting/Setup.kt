package zir.teq.wearable.watchface.model.setting

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.ConfigData
import zir.teq.wearable.watchface.model.setting.color.Background
import zir.teq.wearable.watchface.model.setting.color.Palette
import zir.teq.wearable.watchface.model.setting.component.Components
import zir.teq.wearable.watchface.model.setting.style.*
import zir.teq.wearable.watchface.model.setting.wave.*
import zir.teq.wearable.watchface.model.types.Operator


data class Setup(val code: Int, val name: String, val iconId: Int) {
    var components: Components = Components.default
    var isElastic: Boolean = false
    var isFastUpdate: Boolean = true
    var isWaveOff: Boolean = true
    var palette: Palette = Palette.default()
    var background: Background = Background.default
    var waveSpectrum: WaveSpectrum = WaveSpectrum.default
    var isWavePixelated: Boolean = false
    var isWaveInward: Boolean = false
    var isWaveStanding: Boolean = false
    var waveResolution: WaveResolution = WaveResolution.default
    var waveAmbientResolution: WaveAmbientResolution = WaveAmbientResolution.default
    var waveDarkness: WaveDarkness = WaveDarkness.default
    var waveFrequency: WaveFrequency = WaveFrequency.default
    var waveIntensity: WaveIntensity = WaveIntensity.default
    var waveVelocity: WaveVelocity = WaveVelocity.default
    var waveOperator: Operator = Operator.ADD
    var styleAlpha: StyleAlpha = StyleAlpha.default
    var styleDim: StyleDim = StyleDim.default
    var styleGrowth: StyleGrowth = StyleGrowth.default
    var styleOutline: StyleOutline = StyleOutline.default
    var styleStack: StyleStack = StyleStack.default
    var styleStroke: StyleStroke = StyleStroke.default

    fun applySetup() {
        Components.saveComponentStates(components)
        ConfigData.saveElastic(isElastic)
        ConfigData.saveFastUpdate(isFastUpdate)
        ConfigData.saveWaveIsOff(isWaveOff)
        Palette.save(palette)
        Background.save(background)
        WaveSpectrum.save(waveSpectrum)
        ConfigData.saveWaveIsPixelated(isWavePixelated)
        ConfigData.saveWaveIsInward(isWaveInward)
        ConfigData.saveWaveIsStanding(isWaveStanding)
        WaveResolution.save(waveResolution)
        WaveAmbientResolution.save(waveAmbientResolution)
        WaveDarkness.save(waveDarkness)
        WaveFrequency.save(waveFrequency)
        WaveIntensity.save(waveIntensity)
        WaveVelocity.save(waveVelocity)
        ConfigData.saveWaveOperator(waveOperator)
        StyleAlpha.save(styleAlpha)
        StyleDim.save(styleDim)
        StyleGrowth.save(styleGrowth)
        StyleOutline.save(styleOutline)
        StyleStack.save(styleStack)
        StyleStroke.save(styleStroke)
    }

    companion object {
        val ALL_DEFAULT = Setup(3010, "Default", R.drawable.setup_default)
        val PLAIN = Setup(3020, "Plain", R.drawable.setup_plain).apply {
            components = Components.FIELDS
            palette = Palette.default()
            isFastUpdate = true
            isWaveOff = false
            waveSpectrum = WaveSpectrum.BW
            waveVelocity = WaveVelocity.SLOWER
        }
        val META = Setup(3025, "Meta", R.drawable.setup_meta).apply {
            components = Components.META
            palette = Palette.BLUE_ORANGE
            styleAlpha = StyleAlpha._12
            styleStroke = StyleStroke._13
            styleGrowth = StyleGrowth._21
            isFastUpdate = true
        }
        val DIAMOND = Setup(3030, "Diamond", R.drawable.setup_diamond).apply {
            components = Components.FIELDS
            palette = Palette.BLUE
            styleAlpha = StyleAlpha._12
            styleStroke = StyleStroke._13
            isFastUpdate = true
        }
        val FIELD = Setup(3040, "Field", R.drawable.setup_field).apply {
            components = Components.SHAPE_FIELDS
            palette = Palette.REVERSE_GRASS
            styleStroke = StyleStroke._5
            styleAlpha = StyleAlpha._9
            styleOutline = StyleOutline.OFF
        }
        val TETRAHEDRON = Setup(3050, "Tetrahedron", R.drawable.setup_tetrahedron).apply {
            components = Components.SHAPES
            palette = Palette.RED
        }
        val CLOCKWORK = Setup(3060, "Clockwork", R.drawable.setup_pixel_clockwork).apply {
            components = Components.GEOMETRY
            palette = Palette.RED_YELLOW
            isElastic = true
            isWaveOff = false
            isWavePixelated = true
            waveSpectrum = WaveSpectrum.PALETTE
        }
        val INTERFERENCE = Setup(3070, "Interference", R.drawable.setup_interference).apply {
            components = Components.PLAIN
            palette = Palette.DARK
            isWaveOff = false
            waveSpectrum = WaveSpectrum.FULL
        }
        val CIRCLES = Setup(3080, "Circles", R.drawable.setup_circles).apply {
            components = Components.CIRCLES
            palette = Palette.GREEN_PURPLE
            styleStroke = StyleStroke._13
            isWaveOff = false
            waveSpectrum = WaveSpectrum.BW
            waveVelocity = WaveVelocity.SLOWER
        }
        val LINE_WAVES = Setup(3090, "Line Wave", R.drawable.setup_line_waves).apply {
            components = Components.PLAIN
            palette = Palette.PURPLE_GREEN
            isWaveOff = false
            waveSpectrum = WaveSpectrum.LINES
            isWavePixelated = true
            waveVelocity = WaveVelocity.SLOW
        }

        val default = ALL_DEFAULT
        val all = listOf(ALL_DEFAULT, PLAIN, META, DIAMOND, FIELD, TETRAHEDRON, CLOCKWORK,
                INTERFERENCE, CIRCLES, LINE_WAVES)

        fun valueOf(name: String): Setup {
            return all.find { it.name == name }
                    ?: throw IllegalArgumentException("Setup unknown: $name.")
        }
    }
}
