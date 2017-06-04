package zir.teq.wearable.watchface.model.data

import android.content.Context
import zir.teq.wearable.watchface.R

data class Growth(val name: String, val dim: Float) {
    companion object {
        data class Type(val name: String, val dimId: Int)
        val OFF = Type("Off", R.dimen.dim_off)
        val HAIR = Type("Hair", R.dimen.dim_hair)
        val THIN = Type("Thin", R.dimen.dim_thin)
        val NORMAL = Type("Normal", R.dimen.dim_normal)
        val BOLD = Type("Bold", R.dimen.dim_bold)
        val BOLDER = Type("Bolder", R.dimen.dim_bolder)
        val MEGA = Type("Mega", R.dimen.dim_mega)
        val all = listOf(OFF, HAIR, THIN, NORMAL, BOLD, BOLDER, MEGA)
        val default = NORMAL
        fun options(ctx: Context) = all.map { inst(ctx, it) }.toCollection(ArrayList<Growth>())
        fun create(ctx: Context, typeName: String) = inst(ctx, all.find { it.name.equals(typeName) } ?: default)
        private fun inst(ctx: Context, type: Type) = Growth(type.name, ctx.getResources().getDimension(type.dimId))
    }
}
