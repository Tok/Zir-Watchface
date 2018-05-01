package zir.teq.wearable.watchface.model.data.types.wave

import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.config.general.Config
import zir.teq.wearable.watchface.config.general.Item

enum class WaveDarkness(override val label: String, override val value: Float) : Setting {
    OFF("Off", 0F),
    _1("10%", 0.1F),
    _2("20%", 0.2F),
    _3("30%", 0.3F),
    _4("40%", 0.4F),
    _5("50%", 0.5F),
    _6("60%", 0.6F),
    _7("70%", 0.7F),
    _8("80%", 0.8F),
    _9("90%", 0.9F);

    companion object : Config {
        override val code = Item.WAVE_DARKNESS.code
        override val label = Zir.string(R.string.label_wave_darkness)
        override val pref = Zir.string(R.string.saved_wave_darkness)
        override val iconId = R.drawable.icon_wave_darkness
        override val default = OFF
        override val all = values().toList()
        override fun getByName(name: String) = values().find { it.name.equals(name) } ?: default
    }
}
