package zir.teq.wearable.watchface.model

import zir.teq.wearable.watchface.R

data class Stroke(val name: String, val dim: Float) {
    companion object {
        enum class StrokeType {
            THINNER, THIN, NORMAL, THICK, THICKER, FAT, FATTER, FATTEST, ULTRA
        }
        val defaultName = zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType.NORMAL.name

        fun createStrokeOptions(ctx: android.content.Context): ArrayList<zir.teq.wearable.watchface.model.Stroke> {
            return zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType.values().map { st -> zir.teq.wearable.watchface.model.Stroke.Companion.create(ctx, st) }.toCollection(ArrayList())
        }

        fun createStroke(ctx: android.content.Context, name: String): zir.teq.wearable.watchface.model.Stroke = zir.teq.wearable.watchface.model.Stroke.Companion.create(ctx, StrokeType.valueOf(name))
        private fun create(ctx: android.content.Context, type: zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType): zir.teq.wearable.watchface.model.Stroke {
            val res = ctx.getResources()
            return when (type) {
                zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType.THINNER -> zir.teq.wearable.watchface.model.Stroke(type.name, res.getDimension(R.dimen.dim_thinner))
                zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType.THIN -> zir.teq.wearable.watchface.model.Stroke(type.name, res.getDimension(R.dimen.dim_thin))
                zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType.NORMAL -> zir.teq.wearable.watchface.model.Stroke(type.name, res.getDimension(R.dimen.dim_normal))
                zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType.THICK -> zir.teq.wearable.watchface.model.Stroke(type.name, res.getDimension(R.dimen.dim_thick))
                zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType.THICKER -> zir.teq.wearable.watchface.model.Stroke(type.name, res.getDimension(R.dimen.dim_thicker))
                zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType.FAT -> zir.teq.wearable.watchface.model.Stroke(type.name, res.getDimension(R.dimen.dim_fat))
                zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType.FATTER -> zir.teq.wearable.watchface.model.Stroke(type.name, res.getDimension(R.dimen.dim_fatter))
                zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType.FATTEST -> zir.teq.wearable.watchface.model.Stroke(type.name, res.getDimension(R.dimen.dim_fattest))
                zir.teq.wearable.watchface.model.Stroke.Companion.StrokeType.ULTRA -> zir.teq.wearable.watchface.model.Stroke(type.name, res.getDimension(R.dimen.dim_ultra))
                else -> {
                    val msg = "Ignoring unknown strokeType: " + type
                    android.util.Log.e(zir.watchface.Config.Companion.TAG, msg)
                    throw IllegalArgumentException(msg)
                }
            }
        }
    }
}
