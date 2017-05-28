package zir.teq.wearable.watchface.model.data

import android.content.Context
import zir.teq.wearable.watchface.R

data class Stroke(val name: String, val dim: Float) {
    companion object {
        data class Type(val name: String, val dimId: Int)

        val THINNER = Type("Thinner", R.dimen.dim_thinner)
        val THIN = Type("Thin", R.dimen.dim_thin)
        val NORMAL = Type("Normal", R.dimen.dim_normal)
        val THICK = Type("Thick", R.dimen.dim_thick)
        val THICKER = Type("Thicker", R.dimen.dim_thicker)
        val FAT = Type("Fat", R.dimen.dim_fat)
        val FATTER = Type("Fatter", R.dimen.dim_fatter)
        val FATTEST = Type("Fattest", R.dimen.dim_fattest)
        val ULTRA = Type("Ultra", R.dimen.dim_ultra)
        val ALL_TYPES = listOf(THINNER, THIN, NORMAL, THICK, THICKER, FAT, FATTER, FATTEST, ULTRA)
        val default = NORMAL

        fun createStrokeOptions(ctx: Context): ArrayList<Stroke> {
            return ALL_TYPES.map { create(ctx, it) }.toCollection(ArrayList())
        }

        fun createStroke(ctx: Context, typeName: String): Stroke {
            val type = ALL_TYPES.find { it.name.equals(typeName) }
            return create(ctx, type ?: default)
        }

        private fun create(ctx: Context, type: Type): Stroke {
            return Stroke(type.name, ctx.getResources().getDimension(type.dimId))
        }
    }
}
