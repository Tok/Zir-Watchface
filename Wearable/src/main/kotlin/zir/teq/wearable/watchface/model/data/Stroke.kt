package zir.teq.wearable.watchface.model.data

import android.content.Context
import zir.teq.wearable.watchface.model.data.types.StrokeType

data class Stroke(val name: String, val dim: Float) {
    companion object {
        fun createStrokeOptions(ctx: Context): ArrayList<Stroke> {
            return StrokeType.ALL_TYPES.map { create(ctx, it) }.toCollection(ArrayList())
        }

        fun createStroke(ctx: Context, typeName: String): Stroke {
            val strokeType = StrokeType.ALL_TYPES.find { it.name.equals(typeName) }
            return create(ctx, strokeType ?: StrokeType.default)
        }

        private fun create(ctx: Context, type: StrokeType): Stroke {
            return Stroke(type.name, ctx.getResources().getDimension(type.dimId))
        }
    }
}
