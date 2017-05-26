package zir.teq.wearable.watchface.model

import android.content.Context
import zir.teq.wearable.watchface.R

data class Stroke(val name: String, val dim: Float) {
    companion object {
        enum class StrokeType {
            THINNER, THIN, NORMAL, THICK, THICKER, FAT, FATTER, FATTEST, ULTRA
        }
        val defaultName = StrokeType.NORMAL.name

        fun createStrokeOptions(ctx: Context): ArrayList<Stroke> {
            return StrokeType.values().map { create(ctx, it) }.toCollection(ArrayList())
        }

        fun createStroke(ctx: Context, name: String): Stroke = create(ctx, StrokeType.valueOf(name))
        private fun create(ctx: Context, type: StrokeType): Stroke {
            val res = ctx.getResources()
            return Stroke(type.name, when (type) {
                StrokeType.THINNER -> res.getDimension(R.dimen.dim_thinner)
                StrokeType.THIN -> res.getDimension(R.dimen.dim_thin)
                StrokeType.NORMAL -> res.getDimension(R.dimen.dim_normal)
                StrokeType.THICK -> res.getDimension(R.dimen.dim_thick)
                StrokeType.THICKER -> res.getDimension(R.dimen.dim_thicker)
                StrokeType.FAT -> res.getDimension(R.dimen.dim_fat)
                StrokeType.FATTER -> res.getDimension(R.dimen.dim_fatter)
                StrokeType.FATTEST -> res.getDimension(R.dimen.dim_fattest)
                StrokeType.ULTRA -> res.getDimension(R.dimen.dim_ultra)
                else -> throw IllegalArgumentException("Ignoring unknown strokeType: " + type)
            })
        }
    }
}
