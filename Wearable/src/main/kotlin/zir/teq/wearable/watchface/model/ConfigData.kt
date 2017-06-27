package zir.teq.wearable.watchface.model

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import zir.teq.wearable.watchface.R
import zir.teq.wearable.watchface.Zir
import zir.teq.wearable.watchface.model.data.settings.*
import zir.teq.wearable.watchface.model.data.settings.wave.Wave

object ConfigData {
    val ctx = Zir.getAppContext()
    val res: Resources = ctx.resources
    val prefs: SharedPreferences = ctx.getSharedPreferences(
            ctx.getString(R.string.zir_watch_preference_file_key),
            Context.MODE_PRIVATE)

    fun updateFromSavedPreferences() {
        palette = savedPalette()
        stroke = savedStroke()
        background = savedBackground()
        wave = savedWave()
        alpha = savedAlpha()
        stack = savedStack()
        dim = savedDim()
        outline = savedOutline()
        growth = savedGrowth()
        isFastUpdate = savedFastUpdate()
        isElastic = savedIsElastic()
        val savedTheme = savedTheme()
        val isHand = savedHandSetting(savedTheme)
        val isTri = savedTriangleSetting(savedTheme)
        val isCirc = savedCircleSetting(savedTheme)
        val isPoints = savedPointsSetting(savedTheme)
        val isText = savedTextSetting(savedTheme)
        val isShapes = savedShapesSetting(savedTheme)
        theme = Theme(savedTheme.name, savedTheme.iconId, isHand, isTri, isCirc, isPoints, isText, isShapes)
    }

    private fun prefString(pref: Int, default: String): String {
        return prefs.getString(ctx.getString(pref), default)
    }

    private fun prefBoolean(pref: Int, default: Boolean): Boolean {
        return prefs.getBoolean(ctx.getString(pref), default)
    }

    private fun savedTheme(): Theme {
        return Theme.getByName(prefs.getString(ctx.getString(R.string.saved_theme), Theme.default.name))
    }

    private fun savedHandSetting(theme: Theme): Theme.Companion.Setting {
        return Theme.Companion.Setting(
                prefBoolean(R.string.saved_hands_act, theme.hands.active),
                prefBoolean(R.string.saved_hands_amb, theme.hands.ambient))
    }

    private fun savedTriangleSetting(theme: Theme): Theme.Companion.Setting {
        return Theme.Companion.Setting(
                prefBoolean(R.string.saved_triangles_act, theme.triangles.active),
                prefBoolean(R.string.saved_triangles_amb, theme.triangles.ambient))
    }

    private fun savedCircleSetting(theme: Theme): Theme.Companion.Setting {
        return Theme.Companion.Setting(
                prefBoolean(R.string.saved_circles_act, theme.circles.active),
                prefBoolean(R.string.saved_circles_amb, theme.circles.ambient))
    }

    private fun savedPointsSetting(theme: Theme): Theme.Companion.Setting {
        return Theme.Companion.Setting(
                prefBoolean(R.string.saved_points_act, theme.points.active),
                prefBoolean(R.string.saved_points_amb, theme.points.ambient))
    }

    private fun savedTextSetting(theme: Theme): Theme.Companion.Setting {
        return Theme.Companion.Setting(
                prefBoolean(R.string.saved_text_act, theme.text.active),
                prefBoolean(R.string.saved_text_amb, theme.text.ambient))
    }

    private fun savedShapesSetting(theme: Theme): Theme.Companion.Setting {
        return Theme.Companion.Setting(
                prefBoolean(R.string.saved_shapes_act, theme.shapes.active),
                prefBoolean(R.string.saved_shapes_amb, theme.shapes.ambient))
    }

    private fun savedFastUpdate() = prefs.getBoolean(ctx.getString(R.string.saved_fast_update), isFastUpdate)
    private fun savedIsElastic() = prefs.getBoolean(ctx.getString(R.string.saved_is_elastic), isElastic)
    private fun savedPalette() = Palette.create(prefString(R.string.saved_palette, Palette.default().name))
    private fun savedStroke() = Stroke.create(prefString(R.string.saved_stroke, Stroke.default().name))
    private fun savedWave() = Wave.getByName(prefString(R.string.saved_wave, Wave.default.name))
    private fun savedBackground() = Background.getByName(prefString(R.string.saved_background, Background.default.name))
    private fun savedAlpha() = Alpha.getByName(prefString(R.string.saved_alpha, Alpha.default.name))
    private fun savedStack() = Stack.getByName(prefString(R.string.saved_stack, Stack.default.name))
    private fun savedDim() = Dim.getByName(prefString(R.string.saved_dim, Dim.default.name))
    private fun savedOutline() = Outline.create(prefString(R.string.saved_outline, Outline.default().name))
    private fun savedGrowth() = Growth.create(prefString(R.string.saved_growth, Growth.default().name))
    var palette = savedPalette()
    var stroke = savedStroke()
    var theme = savedTheme()
    var outline = savedOutline()
    var growth = savedGrowth()
    var background = savedBackground()
    var wave = savedWave()
    var alpha = savedAlpha()
    var stack = savedStack()
    var dim = savedDim()
    var isFastUpdate: Boolean = savedFastUpdate()
    var isElastic: Boolean = savedIsElastic()
    var isElasticOutline: Boolean = true //TODO tune
    var isElasticColor: Boolean = true //TODO tune
    val isAntiAlias = true
    var isAmbient = false
    var isMute = false
}
