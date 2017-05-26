package zir.teq.wearable.watchface

import android.content.Context
import android.util.Log
import zir.watchface.Config

data class Stroke(val name: String, val dim: Float) {
    companion object {
        enum class StrokeType {
            THINNER, THIN, NORMAL, THICK, THICKER, FAT, FATTER, FATTEST, ULTRA
        }
        val defaultName = Stroke.Companion.StrokeType.NORMAL.name

        fun createStrokeOptions(ctx: Context): ArrayList<Stroke> {
            return StrokeType.values().map { st -> Stroke.create(ctx, st) }.toCollection(ArrayList())
        }

        fun createStroke(ctx: Context, name: String): Stroke = create(ctx, StrokeType.valueOf(name))
        private fun create(ctx: Context, type: StrokeType): Stroke {
            val res = ctx.getResources()
            return when (type) {
                StrokeType.THINNER -> Stroke(type.name, res.getDimension(R.dimen.dim_thinner))
                StrokeType.THIN -> Stroke(type.name, res.getDimension(R.dimen.dim_thin))
                StrokeType.NORMAL -> Stroke(type.name, res.getDimension(R.dimen.dim_normal))
                StrokeType.THICK -> Stroke(type.name, res.getDimension(R.dimen.dim_thick))
                StrokeType.THICKER -> Stroke(type.name, res.getDimension(R.dimen.dim_thicker))
                StrokeType.FAT -> Stroke(type.name, res.getDimension(R.dimen.dim_fat))
                StrokeType.FATTER -> Stroke(type.name, res.getDimension(R.dimen.dim_fatter))
                StrokeType.FATTEST -> Stroke(type.name, res.getDimension(R.dimen.dim_fattest))
                StrokeType.ULTRA -> Stroke(type.name, res.getDimension(R.dimen.dim_ultra))
                else -> {
                    val msg = "Ignoring unknown strokeType: " + type
                    Log.e(Config.TAG, msg)
                    throw IllegalArgumentException(msg)
                }
            }
        }
    }
}
