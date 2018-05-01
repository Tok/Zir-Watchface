package zir.teq.wearable.watchface.config.general

interface Config {
    val configId: Int
    val name: String
    val all: List<ConfigItem>
    val default: ConfigItem
    fun getByName(name: String): ConfigItem
}
