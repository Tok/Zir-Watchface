package zir.teq.wearable.watchface.model

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.data.*
import zir.teq.wearable.watchface.model.item.ConfigItem
import zir.teq.wearable.watchface.watchface.ZirWatchFaceService
import java.util.*

object ConfigData {
    val ctx = Zir.getAppContext()
    val res: Resources = ctx.resources
    val prefs: SharedPreferences = ctx.getSharedPreferences(
            ctx.getString(R.string.zir_watch_preference_file_key),
            Context.MODE_PRIVATE)
    fun prefString(pref: Int, default: String): String {
        return prefs.getString(ctx.getString(pref), default)
    }
    fun prefBoolean(pref: Int, default: Boolean): Boolean {
        return prefs.getBoolean(ctx.getString(pref), default)
    }
    fun savedTheme(): Theme {
        return Theme.getByName(prefs.getString(ctx.getString(R.string.saved_theme), Theme.default.name))
    }
    fun savedHandSetting(theme: Theme): Theme.Companion.Setting {
        return Theme.Companion.Setting(
                prefBoolean(R.string.saved_hands_act, theme.hands.active),
                prefBoolean(R.string.saved_hands_amb, theme.hands.ambient))
    }
    fun savedTriangleSetting(theme: Theme): Theme.Companion.Setting {
        return Theme.Companion.Setting(
                prefBoolean(R.string.saved_triangles_act, theme.triangles.active),
                prefBoolean(R.string.saved_triangles_amb, theme.triangles.ambient))
    }
    fun savedCircleSetting(theme: Theme): Theme.Companion.Setting {
        return Theme.Companion.Setting(
                prefBoolean(R.string.saved_circles_act, theme.circles.active),
                prefBoolean(R.string.saved_circles_amb, theme.circles.ambient))
    }
    fun savedPointsSetting(theme: Theme): Theme.Companion.Setting {
        return Theme.Companion.Setting(
                prefBoolean(R.string.saved_points_act, theme.points.active),
                prefBoolean(R.string.saved_points_amb, theme.points.ambient))
    }
    fun savedTextSetting(theme: Theme): Theme.Companion.Setting {
        return Theme.Companion.Setting(
                prefBoolean(R.string.saved_text_act, theme.text.active),
                prefBoolean(R.string.saved_text_amb, theme.text.ambient))
    }

    val isAntiAlias = true
    val isElastic = false
    var palette = Palette.default()
    var stroke: Stroke = Stroke.default()
    var theme = Theme.default
    fun hasOutline() = Outline.OFF.name != outline.name
    var outline: Outline = Outline.default()
    var growth: Growth = Growth.default()
    var isFastUpdate: Boolean = true
    var isAmbient = false
    var isMute = false
    var background = Background.default
    var alpha = Alpha.default
    var dim = Dim.default

    interface ConfigItemType {
        val configType: Int
    }

    val watchFaceServiceClass: Class<*> get() = ZirWatchFaceService::class.java

    fun getDataToPopulateAdapter(activityContext: Context): ArrayList<ConfigItemType> {
        val settingsConfigData = ArrayList<ConfigItemType>()
        ConfigItem.ALL_TYPES.forEach {
            settingsConfigData.add(ConfigItem.createInstance(activityContext, it))
        }
        return settingsConfigData
    }
}
