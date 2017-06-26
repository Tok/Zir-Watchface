package zir.teq.wearable.watchface.model.item

import zir.teq.wearable.watchface.config.select.*

class AlphaConfigItem(type: Type, pref: String, name: String) : ConfigItem(type, pref, name) {
    val activity = AlphaSelectionActivity::class.java
}

class BackgroundConfigItem(type: Type, pref: String, name: String) : ConfigItem(type, pref, name) {
    val activity = BackgroundSelectionActivity::class.java
}

class DimConfigItem(type: Type, pref: String, name: String) : ConfigItem(type, pref, name) {
    val activity = DimSelectionActivity::class.java
}

class GrowthConfigItem(type: Type, pref: String, name: String) : ConfigItem(type, pref, name) {
    val activity = GrowthSelectionActivity::class.java
}

class OutlineConfigItem(type: Type, pref: String, name: String) : ConfigItem(type, pref, name) {
    val activity = OutlineSelectionActivity::class.java
}

class PaletteConfigItem(type: Type, pref: String, name: String) : ConfigItem(type, pref, name) {
    val activity = PaletteSelectionActivity::class.java
}

class StackConfigItem(type: Type, pref: String, name: String) : ConfigItem(type, pref, name) {
    val activity = StackSelectionActivity::class.java
}

class StrokeConfigItem(type: Type, pref: String, name: String) : ConfigItem(type, pref, name) {
    val activity = StrokeSelectionActivity::class.java
}

class ThemeConfigItem(type: Type, pref: String, name: String) : ConfigItem(type, pref, name) {
    val activity = ThemeSelectionActivity::class.java
}

class WaveConfigItem(type: Type, pref: String, name: String) : ConfigItem(type, pref, name) {
    val activity = WaveSelectionActivity::class.java
}
