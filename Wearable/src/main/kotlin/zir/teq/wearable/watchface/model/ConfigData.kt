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
        val savedTheme = savedTheme()
        theme = Theme.loadComponentStates(savedTheme.name, savedTheme.iconId)
        palette = savedPalette()
        background = savedBackground()
        wave = savedWave()
        style = updateStyle()
    }
    private fun prefString(pref: Int, default: String) = prefs.getString(ctx.getString(pref), default)

    var theme = savedTheme()
    private fun savedTheme() = Theme.getByName(prefs.getString(ctx.getString(R.string.saved_theme), Theme.default.name))
    fun savedFastUpdate() = prefs.getBoolean(ctx.getString(R.string.saved_fast_update), true)
    fun savedIsElastic() = prefs.getBoolean(ctx.getString(R.string.saved_is_elastic), false)
    var isElasticOutline: Boolean = true //TODO tune
    var isElasticColor: Boolean = true //TODO tune

    var palette = savedPalette()
    private fun savedPalette() = Palette.create(prefString(R.string.saved_palette, Palette.default().name))
    var background = savedBackground()
    private fun savedBackground() = Background.getByName(prefString(R.string.saved_background, Background.default.name))

    var wave = savedWave()
    private fun savedWave() = Wave.getByName(prefString(R.string.saved_wave, Wave.default.name))

    var style = updateStyle()
    private fun updateStyle() = Style("Current", savedAlpha(), savedDim(), savedStack(), savedStroke(), savedGrowth(), savedOutline())
    private fun savedAlpha() = Alpha.getByName(prefString(R.string.saved_alpha, Alpha.default.name))
    private fun savedDim() = Dim.getByName(prefString(R.string.saved_dim, Dim.default.name))
    private fun savedStack() = Stack.getByName(prefString(R.string.saved_stack, Stack.default.name))
    private fun savedStroke() = Stroke.create(prefString(R.string.saved_stroke, Stroke.default().name))
    private fun savedGrowth() = Growth.create(prefString(R.string.saved_growth, Growth.default().name))
    private fun savedOutline() = Outline.create(prefString(R.string.saved_outline, Outline.default().name))

    val isAntiAlias = true
    var isAmbient = false
    var isMute = false
}
