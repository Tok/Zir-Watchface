package zir.teq.wearable.watchface.model.data.settings.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.model.data.types.Operator
import zir.teq.wearable.watchface.util.DrawUtil.Companion.PHI

data class Wave(val name: String,
                val frequency: Float,
                val velocity: Float,
                val intensity: Float,
                val spectrum: Spectrum,
                val isBlur: Boolean = true,
                val isPixel: Boolean = false,
                val op: Operator = Operator.ADD) {
    val iconId: Int = R.drawable.icon_dummy //TODO replace
    val isOff = name.equals("Off")
    val isOn = !isOff
    val hasCenter = false //TODO tune (performance)
    val hasHours = true
    val hasMinutes = true
    val hasSeconds = true

    companion object {
        private val DEFAULT_VELOCITY = -1F / PHI //cycles per second (negative out, positive in)
        private val DEFAULT_FREQUENCY = 1F / PHI //waves per units
        private val DEFAULT_INTENSITY = 7F //~bightness

        val OFF = Wave("Off", 0F, 0F, 0F, Spectrum.default)

        //TODO implement separate selections
        private val DEF = Wave("Default", DEFAULT_FREQUENCY, DEFAULT_VELOCITY, DEFAULT_INTENSITY, Spectrum.default)
        private val DEFSPECS: List<Wave> = Spectrum.values().map {
            DEF.copy(name = "Def " + it.getName(), spectrum = it)
        }

        private val PIXEL = DEF.copy(name = "Pixel", isPixel = true, isBlur = false)
        private val PIXELSPECS: List<Wave> = Spectrum.values().map {
            PIXEL.copy(name = "Pixel " + it.getName(), spectrum = it)
        }

        private val SLOW = DEF.copy(name = "Slow", velocity = DEFAULT_VELOCITY / PHI)
        private val SLOWSPECS: List<Wave> = Spectrum.values().map {
            SLOW.copy(name = "Slow " + it.getName(), spectrum = it)
        }

        private val STANDING = DEF.copy(name = "Standing", velocity = 0F)
        private val STANDSPECS: List<Wave> = Spectrum.values().map {
            STANDING.copy(name = "Stand " + it.getName(), spectrum = it)
        }

        private val INTENSE = DEF.copy(name = "Intense", intensity = DEFAULT_INTENSITY * PHI)
        private val INTENSESPECS: List<Wave> = Spectrum.values().map {
            INTENSE.copy(name = "Intense " + it.getName(), spectrum = it)
        }

        private val LONG = DEF.copy(name = "Long", frequency = DEFAULT_FREQUENCY / PHI)
        private val LONGSPECS: List<Wave> = Spectrum.values().map {
            LONG.copy(name = "Long " + it.getName(), spectrum = it)
        }

        private val MULTIPLY = DEF.copy(name = "Multiply", op = Operator.MULTIPLY)
        private val MULTISPECS: List<Wave> = Spectrum.values().map {
            MULTIPLY.copy(name = "Multi " + it.getName(), spectrum = it)
        }

        val default = DEF
        val all = listOf(OFF) + listOf(DEF) +
                DEFSPECS + PIXELSPECS + SLOWSPECS + STANDSPECS + INTENSESPECS + LONGSPECS + MULTISPECS

        fun options() = all.toCollection(ArrayList())
        fun getByName(name: String): Wave = all.find { it.name.equals(name) } ?: default
    }
}
