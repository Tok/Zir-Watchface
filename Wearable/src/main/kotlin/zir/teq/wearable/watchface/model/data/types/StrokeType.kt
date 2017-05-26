package zir.teq.wearable.watchface.model.data.types

import zir.teq.wearable.watchface.R

data class StrokeType(val name: String, val dimId: Int) {
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
    }
}