package zir.teq.wearable.watchface.model.data

import android.content.Context
import zir.teq.wearable.watchface.R

data class Stroke(val name: String, val dim: Float) {
    companion object {
        data class Type(val name: String, val dimId: Int)
        val HAIR = Type("Hair", R.dimen.dim_hair)
        val THIN = Type("Thin", R.dimen.dim_thin)
        val NORMAL = Type("Normal", R.dimen.dim_normal)
        val BOLD = Type("Bold", R.dimen.dim_bold)
        val MEGA = Type("Mega", R.dimen.dim_mega)
        val GIGA = Type("Giga", R.dimen.dim_giga)
        val ULTRA = Type("Ultra", R.dimen.dim_ultra)
        val all = listOf(HAIR, THIN, NORMAL, BOLD, MEGA, GIGA, ULTRA)
        val default = NORMAL
        fun options(ctx: Context) = all.map { inst(ctx, it) }.toCollection(ArrayList<Stroke>())
        fun create(ctx: Context, typeName: String) = inst(ctx, all.find { it.name.equals(typeName) } ?: default)
        private fun inst(ctx: Context, type: Type) = Stroke(type.name, ctx.getResources().getDimension(type.dimId))
    }
}
