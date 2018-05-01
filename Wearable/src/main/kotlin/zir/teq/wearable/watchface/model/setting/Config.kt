package zir.teq.wearable.watchface.model.setting

import zir.teq.wearable.watchface.model.setting.wave.WaveVelocity

interface Config {
    val code: Int
    val label: String
    val pref: String
    val iconId: Int
    val all: List<Setting>
    val default: Setting
    fun getByName(name: String): Setting = WaveVelocity.values().find { it.name.equals(name) } ?: WaveVelocity.default
    fun save(setting: Setting)
    fun load(): Setting
}
