package zir.teq.wearable.watchface.model.data

import android.content.Context
import zir.teq.wearable.watchface.R

data class Stroke(val name: String, val dim: Float) {
    data class StrokeType(val name: String, val dimId: Int)
    companion object {
        val THINNER = StrokeType("Thinner", R.dimen.dim_thinner)
        val THIN = StrokeType("Thin", R.dimen.dim_thin)
        val NORMAL = StrokeType("Normal", R.dimen.dim_normal)
        val THICK = StrokeType("Thick", R.dimen.dim_thick)
        val THICKER = StrokeType("Thicker", R.dimen.dim_thicker)
        val FAT = StrokeType("Fat", R.dimen.dim_fat)
        val FATTER = StrokeType("Fatter", R.dimen.dim_fatter)
        val FATTEST = StrokeType("Fattest", R.dimen.dim_fattest)
        val ULTRA = StrokeType("Ultra", R.dimen.dim_ultra)
        val ALL_TYPES = listOf(THINNER, THIN, NORMAL, THICK, THICKER, FAT, FATTER, FATTEST, ULTRA)
        val default = NORMAL

        fun createStrokeOptions(ctx: Context): ArrayList<Stroke> {
            return ALL_TYPES.map { create(ctx, it) }.toCollection(ArrayList())
        }

        fun createStroke(ctx: Context, typeName: String): Stroke {
            val strokeType = ALL_TYPES.find { it.name.equals(typeName) }
            return create(ctx, strokeType ?: default)
        }

        private fun create(ctx: Context, type: StrokeType): Stroke {
            return Stroke(type.name, ctx.getResources().getDimension(type.dimId))
        }
    }
}
