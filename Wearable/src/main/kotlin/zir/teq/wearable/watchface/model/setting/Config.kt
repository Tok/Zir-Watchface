package zir.teq.wearable.watchface.model.setting

interface Config {
    val code: Int
    val label: String
    val pref: String
    val iconId: Int
    val all: List<Setting>
    val default: Setting
    fun save(setting: Setting)
    fun load(): Setting
}
